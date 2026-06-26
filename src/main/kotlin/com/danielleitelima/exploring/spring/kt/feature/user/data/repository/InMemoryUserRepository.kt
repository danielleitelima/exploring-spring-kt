package com.danielleitelima.exploring.spring.kt.feature.user.data.repository

import com.danielleitelima.exploring.spring.kt.feature.user.domain.model.User
import com.danielleitelima.exploring.spring.kt.feature.user.domain.repository.UserRepository
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class InMemoryUserRepository : UserRepository {

    private val store = ConcurrentHashMap<String, User>()

    override fun findAll(): List<User> = store.values.toList()

    override fun findById(id: String): User? = store[id]

    override fun save(user: User): User {
        store[user.id] = user
        return user
    }
}