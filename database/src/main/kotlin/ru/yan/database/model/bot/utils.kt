package ru.yan.database.model.bot

import ru.yan.database.model.bot.dto.DtoBotCombatUnit
import ru.yan.database.model.nsi.dto.toDto

fun BotCombatUnit.toDto() = DtoBotCombatUnit(
    this.id,
    this.quantity,
    this.experience,
    this.nsiCombatUnit.toDto()
)