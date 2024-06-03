package ru.yan.database.model.smuta

import jakarta.persistence.*
import ru.yan.database.model.nsi.Resource
import java.util.*
import ru.yan.database.model.operation.StorageResourceOperation

/**
 * Сущность "Ресурс, находящийся на хранении"
 *
 * Это общее кол-во ресурса
 * Только для чтения. Изменения производить через [StorageResourceOperation]
 * В бд есть триггер, который при добавлении записи в [StorageResourceOperation] изменит кол-во ресурса в [StorageResource]
 */
@Entity
@Table(schema = "smuta", name = "storage_resource")
class StorageResource(
    /**
     * Кол-во ресурса на хранении
     */
    @Column(updatable = false, insertable = false)
    val quantity: Double,

    /**
     * Страна
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_uuid")
    val country: Country,

    /**
     * Ресурс
     */
    @OneToOne
    @JoinColumn(name = "resource_uuid")
    val resource: Resource
) {

    /**
     * Идентификатор
     */
    @Id
    @Column(name = "id", updatable = false)
    val id: UUID = UUID.randomUUID()
}