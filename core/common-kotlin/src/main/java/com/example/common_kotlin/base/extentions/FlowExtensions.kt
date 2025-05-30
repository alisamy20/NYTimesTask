package com.example.common_kotlin.base.extentions

import com.example.common_kotlin.base.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart


fun <T> Flow<T>.obtainOutcome(): Flow<Resource<T>> =
    this.map { value ->
        if (value is List<*> && value.isEmpty()) {
            Resource.Empty
        } else {
            Resource.success(value)
        }
    }
        .onStart {
            emit(Resource.Loading)
        }
        .catch { e ->
            emit(Resource.Failure(e))
        }
