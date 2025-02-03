package com.alenniboris.nba_app.presentation.test.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.random.Random

class TestManager() {

    suspend fun getMyDataPage(page: Int): Result<MyDataPage> = withContext(Dispatchers.IO) {
        delay(if (page == 1) 0 else 4_000)
        return@withContext runCatching {
            if (Random.nextBoolean()) {
                (0..10).map { "${page}-${it}" }.let {
                    MyDataPage(
                        data = it,
                        maxPage = 5
                    )
                }
            } else {
                throw Exception("server error")
            }
        }
    }

}