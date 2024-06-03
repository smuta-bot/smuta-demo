package ru.yan.database.repository.smuta

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.yan.database.model.smuta.CountryResourceEffect
import java.util.*

@Repository
interface CountryResourceEffectRepository: JpaRepository<CountryResourceEffect, UUID>