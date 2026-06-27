package com.danielleitelima.exploring.spring.kt.shared.storage

import io.minio.BucketExistsArgs
import io.minio.MakeBucketArgs
import io.minio.MinioClient
import io.minio.PutObjectArgs
import org.springframework.stereotype.Service
import java.io.InputStream

@Service
class MinioStorageService(
    private val client: MinioClient,
    private val properties: MinioProperties,
) : StorageService {

    override fun upload(bucket: String, key: String, inputStream: InputStream, contentType: String, size: Long): String {
        ensureBucketExists(bucket)
        client.putObject(
            PutObjectArgs.builder()
                .bucket(bucket)
                .`object`(key)
                .stream(inputStream, size, -1)
                .contentType(contentType)
                .build()
        )
        return "${properties.endpoint}/$bucket/$key"
    }

    private fun ensureBucketExists(bucket: String) {
        val exists = client.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())
        if (!exists) {
            client.makeBucket(MakeBucketArgs.builder().bucket(bucket).build())
        }
    }
}
