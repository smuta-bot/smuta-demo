package ru.yan.database.model.smuta

import jakarta.persistence.*
import ru.yan.database.model.operation.*
import ru.yan.database.model.task.Task
import java.util.*

/**
 * Сущность "Страна"
 *
 * Показатели страны([area], [morbidity]) только для чтения. Изменения производить через [CountryStateOperation]
 * В бд есть триггер, который при добавлении записи в [CountryStateOperation] изменит показатели
 */
@Entity
@Table(schema = "smuta", name = "country")
class Country(
    /**
     * Название
     */
    val name: String,

    /**
     * Общая площадь страны
     */
    val area: Double,

    /**
     * Уровень заболеваемости
     */
    val morbidity: Double,

    /**
     * Уровень смертности
     */
    val mortality: Double,

    /**
     * Статус мобилизации
     */
    val mobilization: Boolean,

    /**
     * Пользователь, который управляет страной
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uuid")
    val user: User
) {

    /**
     * Идентификатор
     */
    @Id
    @Column(name = "id", updatable = false)
    val id: UUID = UUID.randomUUID()

    /**
     * Группы населения
     */
    @OneToMany(mappedBy = "country", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val populations: List<Population> = listOf()

    /**
     * Здания, построенные страной
     */
    @OneToMany(mappedBy = "country", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val buildings: List<CountryBuilding> = listOf()

    /**
     * Боевые подразделения страны
     */
    @OneToMany(mappedBy = "country", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val combatUnits: List<CombatUnit> = listOf()

    /**
     * Ресурсы, которыми располагает страна
     */
    @OneToMany(mappedBy = "country", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    @MapKeyColumn(name="resource_uuid")
    val storageResources: Map<UUID, StorageResource> = mapOf()

    /**
     * Продукты, выставленные страной на продажу
     */
    @OneToMany(mappedBy = "country", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val marketProducts: List<MarketProduct> = listOf()

    /**
     * Операции над ресурсами в хранилище(по сути лог поступлений/изъятий ресурсов)
     */
    @OneToMany(mappedBy = "country", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val storageResourceOperations: List<StorageResourceOperation> = listOf()

    /**
     * Операции над структурой населения(по сути лог рождаемости/лечения/смерти)
     */
    @OneToMany(mappedBy = "country", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val populationOperations: List<PopulationOperation> = listOf()

    /**
     * Операции над показателями страны
     */
    @OneToMany(mappedBy = "country", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val stateOperations: List<CountryStateOperation> = listOf()

    /**
     * Операции над зданиями в стране
     */
    @OneToMany(mappedBy = "country", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val buildingOperations: List<BuildingOperation> = listOf()

    /**
     * Операции над производимыми продуктами в стране
     */
    @OneToMany(mappedBy = "country", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val productOperations: List<ProductOperation> = listOf()

    /**
     * Операции над счастьем в стране
     */
    @OneToMany(mappedBy = "country", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val happinessOperations: List<HappinessOperation> = listOf()

    /**
     * Операции над боевыми подразделениями в стране
     */
    @OneToMany(mappedBy = "country", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val combatUnitOperations: List<CombatUnitOperation> = listOf()

    /**
     * Уведомления
     */
    @OneToMany(mappedBy = "country", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val notifications: List<Notification> = listOf()

    /**
     * Задачи, которые выполняются в стране
     */
    @OneToMany(mappedBy = "country", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val tasks: List<Task> = listOf()

    /**
     * Эффекты, наложенные на страну
     */
    @OneToMany(mappedBy = "country", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val resourceEffects: List<CountryResourceEffect> = listOf()

    /**
     * Дата создания
     */
    @Column(name = "created_at")
    val createdAt: Date = Date()

    /**
     * Дата удаления
     */
    @Column(name = "deleted_at")
    var deletedAt: Date? = null
}