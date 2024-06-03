package ru.yan.telegram.bot.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "bot")
class BotConfig {
    var username: String = ""
    var token: String = ""
}