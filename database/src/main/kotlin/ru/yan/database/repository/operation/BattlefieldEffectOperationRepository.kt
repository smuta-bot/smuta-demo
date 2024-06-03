package ru.yan.database.repository.operation

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.yan.database.model.operation.BattlefieldEffectOperation
import java.util.*

@Repository
interface BattlefieldEffectOperationRepository: JpaRepository<BattlefieldEffectOperation, UUID>