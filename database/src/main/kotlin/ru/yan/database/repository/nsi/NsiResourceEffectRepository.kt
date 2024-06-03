package ru.yan.database.repository.nsi

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.yan.database.model.nsi.NsiResourceEffect
import java.util.*

@Repository
interface NsiResourceEffectRepository: JpaRepository<NsiResourceEffect, UUID>