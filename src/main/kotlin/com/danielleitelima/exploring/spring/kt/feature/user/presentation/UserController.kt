package com.danielleitelima.exploring.spring.kt.feature.user.presentation

import com.danielleitelima.exploring.spring.kt.feature.user.domain.model.User
import com.danielleitelima.exploring.spring.kt.feature.user.domain.service.UserNotFoundException
import com.danielleitelima.exploring.spring.kt.feature.user.domain.service.UserService
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(private val service: UserService) {

    data class CreateUserRequest(
        @field:NotBlank(message = "Name must not be blank")
        val name: String,

        @field:Email(message = "Email must be a valid address")
        @field:NotBlank(message = "Email must not be blank")
        val email: String,

        @field:NotBlank(message = "Username must not be blank")
        @field:Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
        @field:Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username may only contain letters, digits, and underscores")
        val username: String,

        @field:Min(value = 18, message = "Age must be at least 18")
        @field:Max(value = 120, message = "Age must be at most 120")
        val age: Int,
    )

    data class UserResponse(val id: String, val name: String, val email: String, val username: String, val age: Int)

    data class ErrorResponse(val error: String)

    data class ValidationErrorResponse(val errors: Map<String, String>)

    @GetMapping
    fun getAll(): List<UserResponse> = service.getAll().map { it.toResponse() }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<UserResponse> =
        ResponseEntity.ok(service.getById(id).toResponse())

    @PostMapping
    fun create(@Valid @RequestBody request: CreateUserRequest): ResponseEntity<UserResponse> {
        val user = service.create(request.name, request.email, request.username, request.age)
        return ResponseEntity.status(HttpStatus.CREATED).body(user.toResponse())
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidation(ex: MethodArgumentNotValidException): ResponseEntity<ValidationErrorResponse> {
        val errors = ex.bindingResult.fieldErrors.associate { it.field to (it.defaultMessage ?: "Invalid value") }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ValidationErrorResponse(errors))
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleNotFound(ex: UserNotFoundException): ResponseEntity<ErrorResponse> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(ex.message ?: "Not found"))

    private fun User.toResponse() = UserResponse(id = id, name = name, email = email, username = username, age = age)
}
