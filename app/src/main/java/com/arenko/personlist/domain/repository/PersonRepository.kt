package com.arenko.personlist.domain.repository

import com.arenko.personlist.data.model.PersonResult
import com.arenko.personlist.datasource.*
import kotlinx.coroutines.*
import javax.inject.Inject

class PersonRepository @Inject constructor(
    private val dataSource: DataSource
) {

    suspend fun getPersonList(next: String?): PersonResult? {
        val value = CoroutineScope(Dispatchers.IO).async {
            var personResult: PersonResult? = null
            dataSource.fetch(next) { fetchResult, fetchError ->
                personResult = PersonResult(fetchResult, fetchError)
            }
            delay(2000)
            return@async personResult
        }
        return value.await()
    }
}