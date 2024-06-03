package ru.yan.core.processes.market

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotProcess
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.database.model.smuta.dto.DtoUser

/**
 * Процесс для работы с рынком(выставление товара на продажу, покупка товара)
 */
@Component
class MarketProcess: BotProcess() {
    override fun start(message: InBotMessage, user: DtoUser): OutBotMessage {
        return BotButtonMessage(
            "Здесь можно выставить на продажу и/или купить товар",
            null,
            listOf(listOf("Купить", "Продать"), listOf("Мои предложения"), listOf("Назад")),
            user.getMessengerId()
        )
    }

    companion object {
        const val ID = "05052c84-7154-412d-9bfe-2c3dbf510631"
    }
}