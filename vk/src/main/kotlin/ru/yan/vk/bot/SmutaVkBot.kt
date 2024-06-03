package ru.yan.vk.bot

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.service.MessageProcessingService
import ru.yan.vk.bot.callback.CallbackDto
import ru.yan.vk.bot.callback.CallbackType
import ru.yan.vk.bot.callback.message.MessageEventBody
import ru.yan.vk.bot.callback.message.MessageNewBody
import ru.yan.vk.bot.callback.message.MessageSendResponse
import ru.yan.vk.bot.config.BotConfig
import kotlin.random.Random

@Controller
@RequestMapping("/callback")
class SmutaVkBot(
    private val botConfig: BotConfig,
    private val messageQueue: VkBotMessageQueue,
    private val messageProcessingService: MessageProcessingService
) {
    init {
        CoroutineScope(Dispatchers.Default).launch {
            messageQueue.queueFlow.collect { messages ->
                messages.forEach { message ->

                    val uri = UriComponentsBuilder.fromHttpUrl(message.url)
                        .queryParam("access_token", botConfig.accessKey)
                        .queryParam("v", "5.199")
                        .queryParam("random_id", Random.nextInt())
                        .queryParams(message.toParams())
                        .build()
                        .toUri()

                    val restTemplate = RestTemplate()
                    restTemplate.postForEntity(uri, null, MessageSendResponse::class.java)
                }
            }
        }
    }

    @PostMapping
    fun processing(@RequestBody callbackDto: CallbackDto?): ResponseEntity<String> {
        return callbackDto?.let { callback ->
            when(callback.type) {
                CallbackType.Confirmation -> ResponseEntity(botConfig.confirmationCode, HttpStatus.OK)
                CallbackType.MessageNew -> {
                    val body = callback.body as MessageNewBody

                    messageProcessingService.processing(
                        InBotMessage(
                            body.message.fromId,
                            body.message.id,
                            if (body.message.payload?.command == "start") {
                                "/start"
                            } else {
                                body.message.text
                            },
                            null
                        )
                    )

                    ResponseEntity("ok", HttpStatus.OK)
                }
                CallbackType.MessageEvent -> {
                    val body = callback.body as MessageEventBody

                    messageProcessingService.processing(
                        InBotMessage(
                            body.userId,
                            body.conversationMessageId ?: 0L,
                            null,
                            body.payload
                        )
                    )

                    ResponseEntity("ok", HttpStatus.OK)
                }
            }
        } ?: ResponseEntity("ok", HttpStatus.OK)
    }
}