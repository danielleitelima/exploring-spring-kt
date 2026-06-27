package com.danielleitelima.exploring.spring.kt.feature.user.data.local

import org.springframework.data.jpa.repository.JpaRepository

interface UserDao : JpaRepository<UserEntity, String>
