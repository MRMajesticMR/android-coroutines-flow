package com.majestic_dev.coroutinesflow.model

import kotlinx.coroutines.delay

interface GetMessageUseCase {

    suspend operator fun invoke(): String

}

class GetMessageUseCaseImpl : GetMessageUseCase {

    override suspend fun invoke(): String {
        delay(1000L)

        return "Hello world"
    }

}