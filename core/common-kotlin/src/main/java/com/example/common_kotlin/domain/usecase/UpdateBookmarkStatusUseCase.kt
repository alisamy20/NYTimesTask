package com.example.common_kotlin.domain.usecase

import com.example.common_kotlin.data.source.repo.BaseRepository
import javax.inject.Inject

class UpdateBookmarkStatusUseCase @Inject constructor(
    private val bookmarkRepository: BaseRepository
) {
    suspend operator fun invoke(articleID: String, isBookmarked: Boolean) {
        bookmarkRepository.updateBookmarkStatus(articleID, isBookmarked)
    }
}