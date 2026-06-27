package com.danielleitelima.exploring.spring.kt.feature.user.domain.service

import com.danielleitelima.exploring.spring.kt.feature.user.domain.model.User
import com.danielleitelima.exploring.spring.kt.feature.user.domain.repository.UserRepository
import org.slf4j.Logger
import org.springframework.stereotype.Service
import java.util.UUID

interface UserService {
    fun getAll(): List<User>
    fun getById(id: String): User
    fun create(name: String, email: String): User
}

@Service
class UserServiceImpl(
    private val repository: UserRepository,
    private val logger: Logger,
) : UserService {

    override fun getAll(): List<User> {
        logger.debug("Fetching all users")
        return repository.findAll()
    }

    override fun getById(id: String): User {
        logger.debug("Fetching user id={}", id)
        return repository.findById(id) ?: throw UserNotFoundException(id)
    }

    override fun create(name: String, email: String): User {
        val user = User(id = UUID.randomUUID().toString(), name = name, email = email)
        repository.save(user)
        logger.info("User created id={}", user.id)
        return user
    }
}

class UserNotFoundException(id: String) : RuntimeException("User not found: $id")
