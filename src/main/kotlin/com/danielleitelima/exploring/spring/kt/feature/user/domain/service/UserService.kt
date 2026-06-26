package com.danielleitelima.exploring.spring.kt.feature.user.domain.service

import com.danielleitelima.exploring.spring.kt.feature.user.domain.model.User
import com.danielleitelima.exploring.spring.kt.feature.user.domain.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.UUID

interface UserService {
    fun getAll(): List<User>
    fun getById(id: String): User
    fun create(name: String, email: String): User
}

@Service
class UserServiceImpl(private val repository: UserRepository) : UserService {

    override fun getAll(): List<User> = repository.findAll()

    override fun getById(id: String): User =
        repository.findById(id) ?: throw UserNotFoundException(id)

    override fun create(name: String, email: String): User {
        val user = User(id = UUID.randomUUID().toString(), name = name, email = email)
        return repository.save(user)
    }
}

class UserNotFoundException(id: String) : RuntimeException("User not found: $id")
