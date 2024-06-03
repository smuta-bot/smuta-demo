package ru.yan.database.service

import ru.yan.database.exceptions.AlreadyExistsException
import ru.yan.database.exceptions.NotFoundException
import ru.yan.database.exceptions.NotEnoughPeopleException
import ru.yan.database.exceptions.PermissionDeniedException
import ru.yan.database.model.operation.dto.DtoCountryFightingUnit
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.model.task.dto.DtoBattle
import ru.yan.database.model.task.dto.DtoBattleTask
import java.util.UUID

/**
 * Сервис для работы со сражениями
 */
interface BattleService {

    /**
     * Получить текущий бой
     *
     * @param user Пользователь, сделавший запрос
     *
     * @throws NotFoundException
     */
    fun getCurrentBattle(user: DtoUser): DtoBattle?

    /**
     * Получить боевое подразделение, выставленное для сражения
     *
     * @param user Пользователь, сделавший запрос
     *
     * @throws NotFoundException
     */
    fun getCountryFightingUnit(user: DtoUser): DtoCountryFightingUnit?

    /**
     * Запуск сражения
     *
     * @param user Пользователь, сделавший запрос
     *
     * @throws AlreadyExistsException
     * @throws PermissionDeniedException
     * @throws NotFoundException
     */
    fun startBattle(user: DtoUser): DtoBattleTask

    /**
     * Остановка сражения
     *
     * @param user Пользователь, сделавший запрос
     *
     * @throws AlreadyExistsException
     * @throws PermissionDeniedException
     * @throws NotFoundException
     */
    fun stopBattle(user: DtoUser)

    /**
     * Отправка боевых подразделений
     *
     * @param user Пользователь, сделавший запрос
     * @param combatUnitId Id подразделения
     * @param combatUnitSize Кол-во людей в подразделении
     *
     * @throws NotEnoughPeopleException
     * @throws PermissionDeniedException
     * @throws NotFoundException
     */
    fun sendCombatUnit(user: DtoUser, combatUnitId: UUID, combatUnitSize: Long?): DtoCountryFightingUnit

    /**
     * Отзыв боевых подразделений
     *
     * @param user Пользователь, сделавший запрос
     *
     * @throws PermissionDeniedException
     * @throws NotFoundException
     */
    fun revokeCombatUnit(user: DtoUser)

    /**
     * Остановка атаки
     *
     * @param user Пользователь, страна которого атакует
     *
     * @throws AlreadyExistsException
     * @throws PermissionDeniedException
     * @throws NotFoundException
     */
    fun stopAttack(user: DtoUser)

    /**
     * Капитуляция
     *
     * @param user Пользователь, страну которого атакуют
     *
     * @throws AlreadyExistsException
     * @throws PermissionDeniedException
     * @throws NotFoundException
     */
    fun surrender(user: DtoUser)
}