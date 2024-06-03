package ru.yan.core.processes.management.tasks.population

import org.springframework.stereotype.Component
import ru.yan.core.bot.BotTask
import ru.yan.core.bot.message.InBotMessage
import ru.yan.core.bot.message.BotTextMessage
import ru.yan.core.bot.message.OutBotMessage
import ru.yan.core.getMessengerId
import ru.yan.database.model.nsi.types.AgeGroup
import ru.yan.database.model.nsi.types.HealthStatus
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.service.PopulationService

@Component
class PopulationTask(
    private val populationService: PopulationService
): BotTask {
    override fun execute(message: InBotMessage, user: DtoUser, params: HashMap<String, Any>): OutBotMessage {
        return try {
            val population = populationService.getPopulation(user)

            val childs = population.filter { it.ageGroup == AgeGroup.Child }
            val adults = population.filter { it.ageGroup == AgeGroup.MiddleAge }
            val olds = population.filter { it.ageGroup == AgeGroup.Old }

            BotTextMessage(
                "Детей: ${childs.fold(0L) { a, b -> a + b.quantity}}(Здоровых: ${childs.filter{ it.healthStatus == HealthStatus.Healthy }.fold(0L) { a, b -> a + b.quantity}} / Больных: ${childs.filter{ it.healthStatus == HealthStatus.Sick }.fold(0L) { a, b -> a + b.quantity}})\n" +
                     "Взрослых: ${adults.fold(0L) { a, b -> a + b.quantity }}(Здоровых: ${adults.filter{ it.healthStatus == HealthStatus.Healthy }.fold(0L) { a, b -> a + b.quantity}} / Больных: ${adults.filter{ it.healthStatus == HealthStatus.Sick }.fold(0L) { a, b -> a + b.quantity}})\n" +
                     "Стариков: ${olds.fold(0L) { a, b -> a + b.quantity }}(Здоровых: ${olds.filter{ it.healthStatus == HealthStatus.Healthy }.fold(0L) { a, b -> a + b.quantity}} / Больных: ${olds.filter{ it.healthStatus == HealthStatus.Sick }.fold(0L) { a, b -> a + b.quantity}})\n",
                user.getMessengerId()
            )
        } catch (ex: Throwable) {
            BotTextMessage(
                "Что то пошло не так",
                user.getMessengerId()
            )
        }
    }

    companion object {
        const val ID = "91ab4471-1768-428f-ba8f-9677e1398ff6"
    }
}