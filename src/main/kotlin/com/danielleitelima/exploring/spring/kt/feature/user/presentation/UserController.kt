package com.danielleitelima.exploring.spring.kt.feature.user.presentation

import com.danielleitelima.exploring.spring.kt.feature.user.domain.model.User
import com.danielleitelima.exploring.spring.kt.feature.user.domain.service.UserNotFoundException
import com.danielleitelima.exploring.spring.kt.feature.user.domain.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Users", description = "Operations for managing users")
@RestController
@RequestMapping("/api/users")
class UserController(private val service: UserService) {

    data class CreateUserRequest(val name: String, val email: String)

    data class UserResponse(val id: String, val name: String, val email: String)

    data class ErrorResponse(val error: String)

    @Operation(
        summary = "List all users",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Users retrieved successfully",
                content = [Content(array = ArraySchema(schema = Schema(implementation = UserResponse::class)))]
            )
        ]
    )
    @GetMapping
    fun getAll(): List<UserResponse> = service.getAll().map { it.toResponse() }

    @Operation(
        summary = "Get a user by ID",
        parameters = [Parameter(name = "id", description = "User ID", required = true)],
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "User found",
                content = [Content(schema = Schema(implementation = UserResponse::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "User not found",
                content = [Content(schema = Schema(implementation = ErrorResponse::class))]
            )
        ]
    )
    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<UserResponse> =
        ResponseEntity.ok(service.getById(id).toResponse())

    @Operation(
        summary = "Create a new user",
        responses = [
            ApiResponse(
                responseCode = "201",
                description = "User created successfully",
                content = [Content(schema = Schema(implementation = UserResponse::class))]
            )
        ]
    )
    @PostMapping
    fun create(@RequestBody request: CreateUserRequest): ResponseEntity<UserResponse> {
        val user = service.create(request.name, request.email)
        return ResponseEntity.status(HttpStatus.CREATED).body(user.toResponse())
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleNotFound(ex: UserNotFoundException): ResponseEntity<ErrorResponse> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(ex.message ?: "Not found"))

    private fun User.toResponse() = UserResponse(id = id, name = name, email = email)
}
