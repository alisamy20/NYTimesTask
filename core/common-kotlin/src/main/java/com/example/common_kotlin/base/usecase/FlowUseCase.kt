package com.example.common_kotlin.base.usecase

import com.example.common_kotlin.base.extentions.obtainOutcome
import com.example.common_kotlin.base.network.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn


abstract class FlowUseCase<in Params, Result>(private val coroutineDispatcher: CoroutineDispatcher) {
  @ExperimentalCoroutinesApi
  operator fun invoke(parameters: Params): Flow<Resource<Result>> =
    execute(parameters)
      .obtainOutcome()
      .flowOn(coroutineDispatcher)

  protected abstract fun execute(parameters: Params): Flow<Result>
}