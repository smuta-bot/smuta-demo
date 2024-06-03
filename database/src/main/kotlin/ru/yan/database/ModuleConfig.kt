package ru.yan.database

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan("ru.yan.database")
@ComponentScan("ru.yan.database")
@EnableJpaRepositories
class ModuleConfig
