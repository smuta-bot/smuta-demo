package ru.yan.vk.bot.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "bot")
class BotConfig {
    var confirmationCode: String = ""
    var accessKey: String = ""
}