package ru.yan.core.bot

import ru.yan.core.bot.message.InBotMessage
import ru.yan.database.model.smuta.BreadCrumbs
import java.util.HashMap
import kotlin.reflect.KClass

/**
 * Описание шага
 *
 * @param id Id шага
 * @param executor Исполнитель(тип процесса/задачи которая будет выполняться)
 * @param parent Шаг-родитель(процесс/задача которая породила текущий шаг)
 */
open class BotTaskNode(
    val id: String,
    val executor: KClass<out BotTask>,
    val parent: BotTaskNode?
) {
    /**
     * Дочерние узлы
     */
    var next: List<BotTaskNode> = listOf()

    /**
     * Функция обработки входящего сообщения. Возвращает задачу для исполнения.
     * Суть метода в том что бы обработать сообщение и понять запускать ли текущую задачу или нужно перейти к следующей
     */
    var processing: (message: InBotMessage, params: HashMap<String, Any>) -> BotTaskNode = { _, _ -> this }

    /**
     * Поиск узла по хлебным крошкам
     */
    fun fromBreadCrumbs(breadCrumb: BreadCrumbs): BotTaskNode? {
        return breadCrumb.nextCrumb?.let { crumb ->
            return next.find { it.id == crumb.taskId }?.fromBreadCrumbs(crumb)
        } ?: this
    }

    /**
     * Создание хлебных крошек
     */
    fun toBreadCrumbs(): BreadCrumbs {
        var p = parent
        var state = BreadCrumbs(taskId = id, nextCrumb = null)

        while (p != null) {
            state = BreadCrumbs(taskId = p.id, nextCrumb = state)
            p = p.parent
        }

        return state
    }
}