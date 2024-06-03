package ru.yan.database.repository.bot

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.yan.database.model.bot.BotStorageResource
import java.util.*

@Repository
interface BotStorageResourceRepository: JpaRepository<BotStorageResource, UUID>