package com.arenko.personlist.domain.usecase

import com.arenko.personlist.domain.repository.PersonRepository
import javax.inject.Inject

class PersonListUseCase @Inject constructor(private val personRepository: PersonRepository) {

    suspend fun getPersonList(next: String?) =
        personRepository.getPersonList(next)
}