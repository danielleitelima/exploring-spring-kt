package com.danielleitelima.exploring.spring.kt.feature.user.presentation

import com.danielleitelima.exploring.spring.kt.feature.user.domain.model.User
import com.danielleitelima.exploring.spring.kt.feature.user.domain.service.UserNotFoundException
import com.danielleitelima.exploring.spring.kt.feature.user.domain.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/users")
class UserController(private val service: UserService) {

    data class CreateUserRequest(val name: String, val email: String)

    data class UserResponse(val id: String, val name: String, val email: String, val profilePictureUrl: String?)

    data class ErrorResponse(val error: String)

    @GetMapping
    fun getAll(): List<UserResponse> = service.getAll().map { it.toResponse() }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<UserResponse> =
        ResponseEntity.ok(service.getById(id).toResponse())

    @PostMapping
    fun create(@RequestBody request: CreateUserRequest): ResponseEntity<UserResponse> {
        val user = service.create(request.name, request.email)
        return ResponseEntity.status(HttpStatus.CREATED).body(user.toResponse())
    }

    @PostMapping("/{id}/profile-picture", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadProfilePicture(
        @PathVariable id: String,
        @RequestParam("file") file: MultipartFile,
    ): ResponseEntity<UserResponse> {
        val user = service.uploadProfilePicture(
            id = id,
            inputStream = file.inputStream,
            contentType = file.contentType ?: "application/octet-stream",
            size = file.size,
        )
        return ResponseEntity.ok(user.toResponse())
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleNotFound(ex: UserNotFoundException): ResponseEntity<ErrorResponse> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(ex.message ?: "Not found"))

    private fun User.toResponse() = UserResponse(id = id, name = name, email = email, profilePictureUrl = profilePictureUrl)
}
