package ru.yan.database.model.task

import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import ru.yan.database.model.nsi.types.BattleStage
import ru.yan.database.model.operation.BattleOperation
import ru.yan.database.model.operation.StorageResourceOperation

/**
 * Задача на сражения
 */
@Entity
@Table(schema = "task", name = "battle_task")
class BattleTask(
    /**
     * Стадия сражения
     */
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "nsi.BattleStage")
    @Type(PostgreSQLEnumType::class)
    val stage: BattleStage,

    /**
     * Операция над сражением.
     * Наличие зависит от стадии сражения [stage].
     * Null только для [BattleStage.Search].
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "battle_operation_uuid")
    val battleOperation: BattleOperation?,

    task: Task
): AbstractTask(task) {

    /**
     * Снятые ресурсы для проведения сражения
     */
    @OneToMany(mappedBy = "battleTask", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val storageResourceOperation: List<StorageResourceOperation> = listOf()
}