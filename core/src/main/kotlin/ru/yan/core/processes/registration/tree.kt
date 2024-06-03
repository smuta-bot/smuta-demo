package ru.yan.core.processes.registration

import ru.yan.core.bot.BotProcessTree
import ru.yan.core.bot.BotTaskNode
import ru.yan.core.countryNameValidate
import ru.yan.core.processes.main.mainProcessTree
import ru.yan.core.processes.registration.tasks.CountryCreatedTask
import ru.yan.core.processes.registration.tasks.CreateCountryTask

val registrationProcessTree = BotProcessTree(
    "/start",
    listOf(),
    RegistrationProcess.ID,
    RegistrationProcess::class
).apply {
    next = listOf(
        BotTaskNode(
            CreateCountryTask.ID,
            CreateCountryTask::class,
            this
        ).apply {
            next = listOf(
                BotTaskNode(
                    CountryCreatedTask.ID,
                    CountryCreatedTask::class,
                    this
                ).apply {
                    next = listOf(mainProcessTree)
                    processing = { message, _ ->
                        if (!message.callbackData.isNullOrEmpty()) {
                            next.first()
                        } else {
                            this
                        }
                    }
                }
            )
            processing = { message, _ ->
                if (countryNameValidate(message.text)) {
                    next.first()
                } else {
                    this
                }
            }
        }
    )
    processing = { message, _ ->
        if (!message.callbackData.isNullOrEmpty()) {
             next.first()
        } else {
            this
        }
    }
}