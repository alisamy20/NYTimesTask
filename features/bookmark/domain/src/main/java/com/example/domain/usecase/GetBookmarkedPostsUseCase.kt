package com.example.domain.usecase

import com.example.domain.repository.BookmarkRepository
import javax.inject.Inject

class GetBookmarkedArticlesUseCase @Inject constructor(
    private val repository: BookmarkRepository
) {
    suspend operator fun invoke() = repository.getBookmarkedArticles()

}
