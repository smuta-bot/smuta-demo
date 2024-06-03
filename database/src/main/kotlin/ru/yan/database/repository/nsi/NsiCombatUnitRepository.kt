package ru.yan.database.repository.nsi

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.yan.database.model.nsi.NsiCombatUnit
import ru.yan.database.repository.nsi.custom.CustomNsiCombatUnitRepository
import java.util.*

@Repository
interface NsiCombatUnitRepository: JpaRepository<NsiCombatUnit, UUID>, CustomNsiCombatUnitRepository