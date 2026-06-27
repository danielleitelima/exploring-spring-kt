package com.danielleitelima.exploring.spring.kt.shared.storage

import java.io.InputStream

interface StorageService {
    fun upload(bucket: String, key: String, inputStream: InputStream, contentType: String, size: Long): String
}
