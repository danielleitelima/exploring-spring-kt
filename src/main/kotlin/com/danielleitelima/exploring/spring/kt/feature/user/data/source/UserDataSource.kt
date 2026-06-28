package com.danielleitelima.exploring.spring.kt.feature.user.data.source

import com.danielleitelima.exploring.spring.kt.feature.user.data.entity.UserDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface UserDataSource : MongoRepository<UserDocument, String>