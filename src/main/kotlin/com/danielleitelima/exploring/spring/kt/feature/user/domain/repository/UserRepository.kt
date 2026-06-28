package com.danielleitelima.exploring.spring.kt.feature.user.domain.repository

import com.danielleitelima.exploring.spring.kt.feature.user.domain.model.User

interface UserRepository {
    fun findAll(): List<User>
    fun findById(id: String): User?
    fun save(user: User): User
    fun deleteById(id: String)
}