package com.danielleitelima.exploring.spring.kt.feature.user.domain.service

import com.danielleitelima.exploring.spring.kt.feature.user.domain.model.User
import com.danielleitelima.exploring.spring.kt.feature.user.domain.repository.UserRepository
import com.danielleitelima.exploring.spring.kt.shared.storage.StorageService
import org.springframework.stereotype.Service
import java.io.InputStream
import java.util.UUID

interface UserService {
    fun getAll(): List<User>
    fun getById(id: String): User
    fun create(name: String, email: String): User
    fun uploadProfilePicture(id: String, inputStream: InputStream, contentType: String, size: Long): User
}

@Service
class UserServiceImpl(
    private val repository: UserRepository,
    private val storageService: StorageService,
) : UserService {

    companion object {
        private const val BUCKET = "profile-pictures"
    }

    override fun getAll(): List<User> = repository.findAll()

    override fun getById(id: String): User =
        repository.findById(id) ?: throw UserNotFoundException(id)

    override fun create(name: String, email: String): User {
        val user = User(id = UUID.randomUUID().toString(), name = name, email = email)
        return repository.save(user)
    }

    override fun uploadProfilePicture(id: String, inputStream: InputStream, contentType: String, size: Long): User {
        val user = getById(id)
        val extension = contentType.substringAfter("/").substringBefore(";")
        val key = "$id-profile.$extension"
        val url = storageService.upload(BUCKET, key, inputStream, contentType, size)
        return repository.save(user.copy(profilePictureUrl = url))
    }
}

class UserNotFoundException(id: String) : RuntimeException("User not found: $id")
