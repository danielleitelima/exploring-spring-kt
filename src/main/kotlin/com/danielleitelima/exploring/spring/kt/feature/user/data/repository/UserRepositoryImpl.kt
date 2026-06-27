package com.danielleitelima.exploring.spring.kt.feature.user.data.repository

import com.danielleitelima.exploring.spring.kt.feature.user.data.local.UserDao
import com.danielleitelima.exploring.spring.kt.feature.user.data.local.UserEntity
import com.danielleitelima.exploring.spring.kt.feature.user.domain.model.User
import com.danielleitelima.exploring.spring.kt.feature.user.domain.repository.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val dao: UserDao,
) : UserRepository {

    override fun findAll(): List<User> = dao.findAll().map { it.toDomain() }

    override fun findById(id: String): User? = dao.findById(id).orElse(null)?.toDomain()

    override fun save(user: User): User {
        val entity = UserEntity(id = user.id, name = user.name, email = user.email)
        return dao.save(entity).toDomain()
    }

    private fun UserEntity.toDomain() = User(id = id, name = name, email = email)
}
