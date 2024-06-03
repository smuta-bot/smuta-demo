package ru.yan.vk.bot.callback

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import kotlinx.serialization.json.Json
import org.springframework.boot.jackson.JsonComponent
import ru.yan.vk.bot.callback.message.MessageEventBody
import ru.yan.vk.bot.callback.message.MessageNewBody
import ru.yan.vk.bot.callback.message.VkCallbackMessage
import ru.yan.vk.bot.callback.message.VkCallbackMessagePayload

@JsonComponent
class CustomCallbackDeserializer: JsonDeserializer<CallbackDto?>() {

    override fun deserialize(parser: JsonParser, context: DeserializationContext): CallbackDto? {
        val root = parser.codec.readTree<JsonNode>(parser)
        val type = root.get("type").textValue()

        return when (type) {
            "message_new" -> {
                CallbackDto(
                    CallbackType.MessageNew,
                    root.get("v").textValue(),
                    root.get("group_id").longValue(),
                    root.get("event_id").textValue(),
                    MessageNewBody(
                        VkCallbackMessage(
                            root.get("object").get("message").get("id").longValue(),
                            root.get("object").get("message").get("from_id").longValue(),
                            root.get("object").get("message").get("text").textValue(),
                            root.get("object").get("message").get("payload")?.let {
                                Json.decodeFromString<VkCallbackMessagePayload>(it.textValue())
                            }
                        )
                    ),
                    root.get("secret").textValue()
                )
            }
            "message_event" -> {
                CallbackDto(
                    CallbackType.MessageEvent,
                    root.get("v").textValue(),
                    root.get("group_id").longValue(),
                    root.get("event_id").textValue(),
                    MessageEventBody(
                        root.get("object").get("user_id").longValue(),
                        root.get("object").get("payload").get("command").textValue(),
                        root.get("object").get("conversation_message_id")?.longValue()
                    ),
                    root.get("secret").textValue()
                )
            }
            "confirmation" -> {
                CallbackDto(
                    CallbackType.Confirmation,
                    root.get("v").textValue(),
                    root.get("group_id").longValue(),
                    root.get("event_id").textValue(),
                    null,
                    root.get("secret").textValue()
                )
            }
            else -> null
        }
    }
}