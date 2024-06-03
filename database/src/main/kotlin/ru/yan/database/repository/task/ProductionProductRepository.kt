package ru.yan.database.repository.task

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.yan.database.model.task.ProductionProduct
import java.util.*

@Repository
interface ProductionProductRepository: JpaRepository<ProductionProduct, UUID>