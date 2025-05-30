package com.example.domain.usecase


import com.example.domain.repository.NewsRepository
import com.example.common_kotlin.base.coroutine_dispatcher.IoDispatcher
import com.example.common_kotlin.base.usecase.FlowUseCase
import com.example.common_kotlin.domain.model.ArticleModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetNewsUseCase @Inject constructor(
    private val repository: NewsRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : FlowUseCase<Unit, List<ArticleModel>>(ioDispatcher) {

    override fun execute(parameters: Unit) = flow {
        emit(repository.fetchNews())
    }


}