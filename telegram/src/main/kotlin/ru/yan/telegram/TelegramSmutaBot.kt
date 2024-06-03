package ru.yan.telegram

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.boot.runApplication
import ru.yan.telegram.bot.config.BotConfig

@SpringBootApplication
@EnableConfigurationProperties(BotConfig::class)
@EnableScheduling
class TelegramSmutaBot

fun main(args: Array<String>) {
    runApplication<TelegramSmutaBot>(*args)
}