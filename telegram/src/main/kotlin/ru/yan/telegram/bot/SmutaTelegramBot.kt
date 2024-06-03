package ru.yan.telegram.bot

import jakarta.annotation.PostConstruct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.service.MessageProcessingService
import ru.yan.telegram.bot.config.BotConfig

@Component
class SmutaTelegramBot(
    private val botConfig: BotConfig,
    private val messageQueue: TelegramBotMessageQueue,
    private val messageProcessingService: MessageProcessingService
) : TelegramLongPollingCommandBot(botConfig.token) {

    init {
        CoroutineScope(Dispatchers.Default).launch {
            messageQueue.queueFlow.collect { messages ->
                messages.forEach { message ->
                    execute(message)
                }
            }
        }
    }

    override fun getBotUsername() = botConfig.username

    override fun onUpdatesReceived(updates: MutableList<Update>?) {
        super.onUpdatesReceived(updates)
    }

    override fun processNonCommandUpdate(update: Update?) {
        when {
            update?.hasMessage() == true -> {
                messageProcessingService.processing(
                    InBotMessage(
                        update.message.chatId,
                        update.message.messageId.toLong(),
                        update.message.text,
                        null
                    )
                )
            }

            update?.hasCallbackQuery() == true -> {
                messageProcessingService.processing(
                    InBotMessage(
                        update.callbackQuery!!.from.id,
                        update.callbackQuery!!.message.messageId.toLong(),
                        null,
                        update.callbackQuery!!.data
                    )
                )
            }

            update?.hasPreCheckoutQuery() == true -> {
                // TODO: Обработать
            }

            else -> {
                // TODO: Произошло что то неожиданное
            }
        }
    }

    @PostConstruct
    @Throws(TelegramApiRequestException::class)
    fun addBot() {
        val botsApi = TelegramBotsApi(DefaultBotSession::class.java)
        botsApi.registerBot(this)
    }
}