package com.example.domain.usecase

import com.example.domain.repository.NewsRepository
import javax.inject.Inject

class UpdateBookmarkStatusUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(articleID: String, isBookmarked: Boolean) {
        newsRepository.updateBookmarkStatus(articleID, isBookmarked)
    }
}
