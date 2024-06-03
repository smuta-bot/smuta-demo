package ru.yan.database.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.yan.database.model.smuta.User
import ru.yan.database.exceptions.notFound
import ru.yan.database.model.nsi.types.MessengerType
import ru.yan.database.model.smuta.UserActivity
import ru.yan.database.model.smuta.dto.DtoUser
import ru.yan.database.model.smuta.dto.toDto
import ru.yan.database.repository.smuta.UserRepository
import ru.yan.database.service.UserService
import java.util.*

@Service
class UserServiceImpl(
    val userRepository: UserRepository
): UserService {
    @Transactional
    override fun getOrCreateTelegram(telegramId: Long): DtoUser {
        return userRepository.findByTelegramId(telegramId)?.toDto() ?: run {
            userRepository.save(
                User(
                    telegramId,
                    null,
                    null
                )
            )
        }.toDto()
    }

    @Transactional
    override fun getOrCreateVk(vkId: Long): DtoUser {
        return userRepository.findByVkId(vkId)?.toDto() ?: run {
            userRepository.save(
                User(
                    null,
                    vkId,
                    null
                )
            )
        }.toDto()
    }

    @Transactional
    override fun getOutOfMessengerLimit(ids: List<Long>, limit: Int, messengerType: MessengerType): List<DtoUser> {
        return if (messengerType == MessengerType.Telegram) {
            userRepository.findOutOfTelegramLimit(ids, limit)
        } else {
            userRepository.findOutOfVkLimit(ids, limit)
        }.map {
            it.toDto()
        }
    }

    @Transactional
    override fun getToNotificate() =
        userRepository.findToNotificate().map { it.toDto() }

    @Transactional
    override fun updateUserActivity(user: DtoUser, activity: UserActivity?): DtoUser {
        val dbUser = userRepository.findByUserId(user.id) ?: notFound(User::class, user.id)

        return userRepository.save(
            dbUser.apply { userActivity = activity }
        ).toDto()
    }

    @Transactional
    override fun updateActivityTime(ids: List<Long>, messengerType: MessengerType): List<DtoUser> {
        val users = if (messengerType == MessengerType.Telegram) {
            userRepository.findByTelegramIdIn(ids)
        } else {
            userRepository.findByVkIdIn(ids)
        }

        users.forEach {
            it.activityAt = Date()
        }

        return userRepository.saveAll(users).map { it.toDto() }
    }
}