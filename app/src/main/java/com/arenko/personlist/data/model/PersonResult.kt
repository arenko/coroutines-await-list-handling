package com.arenko.personlist.data.model

import com.arenko.personlist.datasource.FetchError
import com.arenko.personlist.datasource.FetchResponse

data class PersonResult(
    val fetchResponse: FetchResponse?,
    val fetchError: FetchError?
)
