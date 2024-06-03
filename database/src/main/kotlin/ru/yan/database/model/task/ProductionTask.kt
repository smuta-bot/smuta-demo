package ru.yan.database.model.task

import jakarta.persistence.*
import ru.yan.database.model.nsi.NsiBuilding
import ru.yan.database.model.operation.StorageResourceOperation

/**
 * Сущность "Задача на производство товара"
 */
@Entity
@Table(schema = "task", name = "production_task")
class ProductionTask(
    /**
     * Здание, которое производит
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_uuid")
    val nsiBuilding: NsiBuilding,

    task: Task
): AbstractTask(task) {

    /**
     * Производимая продукция
     */
    @OneToMany(mappedBy = "productionTask", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val productionProducts: List<ProductionProduct> = listOf()

    /**
     * Снятые ресурсы при производстве продукции
     */
    @OneToMany(mappedBy = "productionTask", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val storageResourceOperation: List<StorageResourceOperation> = listOf()
}