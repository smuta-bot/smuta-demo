package ru.yan.vk

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.boot.runApplication
import ru.yan.vk.bot.config.BotConfig

@SpringBootApplication
@EnableConfigurationProperties(BotConfig::class)
@EnableScheduling
class VkSmutaBot

fun main(args: Array<String>) {
    runApplication<VkSmutaBot>(*args)
}