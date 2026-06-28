package com.danielleitelima.exploring.spring.kt.feature.user.data.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("users")
data class UserDocument(
    @Id val id: String,
    val name: String,
    val email: String,
)
