package ru.yan.core.general

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotButtonMessage
import ru.yan.core.bot.message.InlineButton
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.database.model.smuta.dto.DtoUser

@Component
class GetConfirmationTask: BotTask {

    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {

        val type = enumValueOf<ConfirmationType>(params[CONFIRMATION_TYPE] as String)
        val confirmationText = when(type) {
            ConfirmationType.StartProduction -> "Вы уверены что хотите запустить производство продукции этим зданием?"
            ConfirmationType.StartDestroy -> "Вы уверены что хотите уничтожить здание? Весь прогресс будет потерян"
            ConfirmationType.StartConservation -> "Вы уверены что хотите законсервировать здание?"
            ConfirmationType.StopProduction -> "Вы уверены что хотите прекратить производство продукции этим зданием?"
            ConfirmationType.StopConstruction -> "Вы уверены что хотите прекратить строительство?"
            ConfirmationType.StopDestroy -> "Вы уверены что хотите отменить снос здания?"
            ConfirmationType.StopConservation -> "Вы уверены что хотите отменить консервацию здание?"
            ConfirmationType.StopReactivation -> "Вы уверены что хотите отменить расконсервацию здание?"
            ConfirmationType.Reactivate -> "Вы уверены что хотите расконсервировать здание?"
            ConfirmationType.ExcludeProduct -> "Вы уверены что хотите исключить продукт из списка производимых товаров?"
            ConfirmationType.OpenHospital -> "Вы уверены что хотите начать лечение населения?"
            ConfirmationType.CloseHospital -> "Вы уверены что хотите прекратить лечение населения?"
            ConfirmationType.StopAttack -> "Вы уверены что хотите прекратить атаку?"
            ConfirmationType.Surrender -> "Вы уверены что хотите капитулировать?"
        }

        return BotButtonMessage(
            confirmationText,
            listOf(
                listOf(InlineButton("Да", "confirm")),
                listOf(InlineButton("Нет", "not_confirm"))
            ),
            null,
            user.getMessengerId()
        )
    }

    companion object {
        const val ID = "94ebede5-17f0-4522-8d2d-608b159bedf7"
    }
}