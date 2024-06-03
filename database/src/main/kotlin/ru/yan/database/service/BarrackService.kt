package ru.yan.database.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import ru.yan.database.exceptions.AlreadyExistsException
import ru.yan.database.exceptions.NotFoundException
import ru.yan.database.model.nsi.dto.DtoNsiCombatUnit
import ru.yan.database.model.operation.dto.DtoCombatUnitOperation
import ru.yan.database.model.smuta.dto.DtoCombatUnit
import ru.yan.database.model.smuta.dto.DtoCountryBuilding
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.model.task.dto.DtoCombatUnitTask
import java.util.UUID

/**
 * Сервис для работы со зданием "Казарма"
 */
interface BarrackService {
    /**
     * Получить здание "Казарма"
     *
     * @param user Пользователь, запросивший данные
     */
    fun getCountryBarrack(user: DtoUser): DtoCountryBuilding?

    /**
     * Получить боевые подразделения страны
     *
     * @param user Пользователь, запросивший данные
     * @param pageRequest Страница
     */
    fun getCombatUnits(user: DtoUser, pageRequest: PageRequest): Page<DtoCombatUnit>

    /**
     * Получить боевое подразделение страны по id
     *
     * @param user Пользователь, запросивший данные
     * @param combatUnitId Id подразделения
     */
    fun getCombatUnit(user: DtoUser, combatUnitId: UUID): DtoCombatUnit?

    /**
     * Получить боевые подразделения из справочника
     *
     * @param pageRequest Страница
     */
    fun getNsiCombatUnits(pageRequest: PageRequest): Page<DtoNsiCombatUnit>

    /**
     * Получить боевое подразделение по id
     *
     * @param nsiCombatUnitId Id подразделения из справочника
     */
    fun getNsiCombatUnit(nsiCombatUnitId: UUID): DtoNsiCombatUnit?

    /**
     * Создать боевое подразделение
     *
     * @param user Пользователь, сделавший запрос
     * @param nsiCombatUnitId Id подразделения из справочника
     * @param numberPeople Кол-во людей в подразделении
     *
     * @throws NotFoundException
     * @throws AlreadyExistsException
     */
    fun createCombatUnit(user: DtoUser, nsiCombatUnitId: UUID, numberPeople: Long): DtoCombatUnitTask

    /**
     * Отправить подразделение на учения
     *
     * @param combatUnitId Id подразделения
     *
     * @throws NotFoundException
     * @throws AlreadyExistsException
     */
    fun exerciseCombatUnit(combatUnitId: UUID): DtoCombatUnitTask

    /**
     * Отмена изменений боевого подразделения
     *
     * @param combatUnitId Id подразделения
     *
     * @throws NotFoundException
     * @throws AlreadyExistsException
     */
    fun abortCombatUnit(combatUnitId: UUID): DtoCombatUnitOperation
}