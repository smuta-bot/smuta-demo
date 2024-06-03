package ru.yan.database.model.smuta

import java.io.Serializable

/**
 * Хлебные крошки
 * Необходимы для того что бы сохранить пользовательское состояние(воспроизвести последнюю задачу)
 */
data class BreadCrumbs(
    /**
     * Id задачи
     */
    val taskId: String,

    /**
     * Следующая "крошка", указывающая на следующую задачу
     */
    val nextCrumb: BreadCrumbs?
)

/**
 * Пользовательская активность
 */
class UserActivity(
    /**
     * Сохраненные параметры задачи
     */
    val params: HashMap<String, Any>,

    /**
     * Хлебные крошки
     */
    val breadCrumbs: BreadCrumbs
): Serializable