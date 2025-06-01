package com.example.domain.usecase

import com.example.domain.repository.BookmarkRepository
import javax.inject.Inject

class UpdateBookmarkStatusUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {
    suspend operator fun invoke(articleID: String, isBookmarked: Boolean) {
        bookmarkRepository.updateBookmarkStatus(articleID, isBookmarked)
    }
}
