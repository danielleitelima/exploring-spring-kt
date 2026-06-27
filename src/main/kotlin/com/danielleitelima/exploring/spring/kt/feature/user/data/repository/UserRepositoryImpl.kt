package com.danielleitelima.exploring.spring.kt.feature.user.data.repository

import com.danielleitelima.exploring.spring.kt.feature.user.data.source.UserDataSource
import com.danielleitelima.exploring.spring.kt.feature.user.data.entity.UserDocument
import com.danielleitelima.exploring.spring.kt.feature.user.domain.model.User
import com.danielleitelima.exploring.spring.kt.feature.user.domain.repository.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userDataSource: UserDataSource,
) : UserRepository {

    override fun findAll(): List<User> = userDataSource.findAll().map { it.toDomain() }

    override fun findById(id: String): User? = userDataSource.findById(id).orElse(null)?.toDomain()

    override fun save(user: User): User {
        userDataSource.save(user.toDocument())
        return user
    }

    private fun UserDocument.toDomain() = User(id = id, name = name, email = email)

    private fun User.toDocument() = UserDocument(id = id, name = name, email = email)
}
