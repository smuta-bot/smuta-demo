package ru.yan.database.components.task

import ru.yan.database.model.smuta.Country
import ru.yan.database.model.task.AbstractTask

/**
 * Исполнитель задачи
 */
sealed interface TaskPerformer<in T: AbstractTask?> {

    /**
     * Запустить задачу
     *
     * @param task задача для запуска
     * @param country страна, которой принадлежит задача
     */
    fun execute(task: T, country: Country)
}