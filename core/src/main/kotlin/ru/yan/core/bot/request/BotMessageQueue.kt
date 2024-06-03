package ru.yan.core.bot.request

import jakarta.annotation.PostConstruct
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.bot.message.BotMessageConverter
import ru.yan.database.model.smuta.dto.DtoUser
import java.util.Date

/**
 * Класс, реализующий очередь сообщений бота и работу с ней
 */
abstract class BotMessageQueue<TMessage> {

    /**
     * Преобразователь сообщения
     */
    protected abstract val converter: BotMessageConverter<TMessage>

    /**
     * Кол-во сообщений в секунду
     */
    protected abstract val messagesPerSecond: Int

    /**
     * Очередь сообщений
     */
    private val messageQueue: HashMap<Long, MutableList<OutBotMessage>> = hashMapOf()


    val queueFlow = MutableSharedFlow<List<TMessage>>()

    private var messageCount = 0
    private var firstMessageTime = Date()

    /**
     * Добавить сообщение в очередь
     */
    fun add(message: OutBotMessage) {
        messageQueue[message.userId]?.add(message) ?: run {
            messageQueue.put(message.userId, mutableListOf(message))
        }
    }

    /**
     * Получить id пользователей, которым МОЖНО отправить сообщение
     *
     * @param ids Идентификаторы пользователей, которым НУЖНО отправить сообщения
     * @param limit Лимит сообщений
     */
    abstract fun findOutOfMessengerLimit(ids: List<Long>, limit: Int): List<Long>

    /**
     * Обновление времени последней пользовательской активности
     *
     * @param ids Идентификаторы пользователей, которым НУЖНО обновить время
     */
    abstract fun updateActivityTime(ids: List<Long>): List<DtoUser>

    /**
     * Обработка очереди сообщений
     */
    private fun process() {
        CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                // Если с момента отправки первого сообщений не прошла секунда
                if (Date().time - firstMessageTime.time < 1000) {
                    if (messageCount == messagesPerSecond) {
                        // А мы уже достигли потолка(отправили максимум сообщений), то сбрасываем счетчик и засыпаем
                        messageCount = 0
                        delay(1000)
                    }
                } else {
                    // Иначе просто сбрасываем счетчик сообщений
                    messageCount = 0
                }

                if (messageQueue.isNotEmpty()) {
                    val messengerIds = findOutOfMessengerLimit(
                        messageQueue.keys.toList(),
                        messagesPerSecond - messageCount
                    )

                    val messagesForSend = messengerIds.map {
                        val messages = messageQueue[it]!!

                        converter.convert(
                            if (messages.size > 1) {
                                messages.removeAt(0)
                            } else {
                                messageQueue.remove(it)!!.first()
                            }
                        )
                    }

                    if (messagesForSend.isNotEmpty()) {
                        // Если это наше первое сообщение, то фиксируем время
                        if (messageCount == 0) {
                            firstMessageTime = Date()
                        }

                        // Отправляем сообщения
                        queueFlow.emit(messagesForSend)

                        // Суммируем кол-во отправленных сообщений
                        messageCount += messagesForSend.size

                        // Обновляем время последней активности пользователей
                        updateActivityTime(messengerIds)
                    } else {
                        // Если все пользователи исчерпали лимит, то просто ненадолго засыпаем
                        delay(100)
                    }
                } else {
                    // Если сообщений для отправки нет, то просто ненадолго засыпаем
                    delay(100)
                }
            }
        }
    }

    @PostConstruct
    fun after() {
        process()
    }
}