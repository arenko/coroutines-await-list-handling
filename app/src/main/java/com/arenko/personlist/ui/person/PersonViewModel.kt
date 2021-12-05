package com.arenko.personlist.ui.person

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.arenko.personlist.domain.usecase.PersonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(private val personListUseCase: PersonListUseCase) :
    ViewModel() {

    fun getPersonList(next: String?) = liveData {
        emit(personListUseCase.getPersonList(next))
    }
}