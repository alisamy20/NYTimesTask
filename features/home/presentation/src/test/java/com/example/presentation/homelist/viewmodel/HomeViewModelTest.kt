package com.example.presentation.homelist.viewmodel


import com.example.common_kotlin.utils.Resource
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import app.cash.turbine.test
import com.example.common_kotlin.domain.model.ArticleModel
import com.example.common_kotlin.domain.model.MediaMetadataModel
import com.example.common_kotlin.domain.model.MediaModel
import com.example.common_kotlin.domain.usecase.UpdateBookmarkStatusUseCase
import com.example.domain.usecase.FilterNewsUseCase
import com.example.domain.usecase.GetNewsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertNull


@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private val getNewsUseCase: GetNewsUseCase = mockk()
    private val updateBookmarkStatusUseCase: UpdateBookmarkStatusUseCase = mockk(relaxed = true)
    private val filterArticlesUseCase: FilterNewsUseCase = mockk(relaxed = true)
    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: HomeViewModel

    val article = ArticleModel(
        id = "100000010146090",
        url = "https://www.nytimes.com/2025/05/30/us/elon-musk-drugs-children-trump.html",
        title = "On the Campaign Trail, Elon Musk Juggled Drugs and Family Drama",
        description = "As Mr. Musk entered President Trump’s orbit, his private life grew increasingly tumultuous and his drug use was more intense than previously known.",
        type = "Article",
        publishedDate = "2025-05-30",
        updatedDate = "2025-06-01 11:01:59",
        section = "U.S.",
        source = "New York Times",
        media = listOf(
            MediaModel(
                caption = "Elon Musk boarding Air Force One in March.",
                subtype = "photo",
                type = "image",
                mediaMetadata = listOf(
                    MediaMetadataModel(
                        format = "Standard Thumbnail",
                        height = 75,
                        width = 75,
                        url = "https://static01.nyt.com/images/2025/05/26/multimedia/00musk-wflt/00musk-wflt-thumbStandard.jpg"
                    ), MediaMetadataModel(
                        format = "mediumThreeByTwo210",
                        height = 140,
                        width = 210,
                        url = "https://static01.nyt.com/images/2025/05/26/multimedia/00musk-wflt/00musk-wflt-mediumThreeByTwo210.jpg"
                    ), MediaMetadataModel(
                        format = "mediumThreeByTwo440",
                        height = 293,
                        width = 440,
                        url = "https://static01.nyt.com/images/2025/05/26/multimedia/00musk-wflt/00musk-wflt-mediumThreeByTwo440.jpg"
                    )
                )
            )
        ),
        isBookmarked = false
    )


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(
            getNewsUseCase, updateBookmarkStatusUseCase, filterArticlesUseCase, testDispatcher
        )
    }

    @Test
    fun `LoadArticles emits loading then success`() = runTest {
        val articles = listOf(article)
        coEvery { getNewsUseCase(Unit) } returns flowOf(
            Resource.Loading, Resource.Success(articles)
        )

        viewModel.handleAction(HomeAction.LoadArticles)
        advanceUntilIdle()

        assertEquals(false, viewModel.uiState.value.isLoading)
        assertEquals(articles, viewModel.uiState.value.originalArticles)
    }

    @Test
    fun `LoadArticles success updates state with articles`() = runTest {
        coEvery { getNewsUseCase(Unit) } returns flow {
            emit(Resource.Loading)
            emit(Resource.Success(listOf(article)))
        }

        viewModel.handleAction(HomeAction.LoadArticles)

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(false, state.isLoading)
        assertEquals(listOf(article), state.originalArticles)
        assertNull(state.error)
    }


    @Test
    fun `LoadArticles emits failure throwable `() = runTest {
        val error = Throwable("Network error")
        coEvery { getNewsUseCase(Unit) } returns flowOf(Resource.Loading, Resource.Failure(error))

        viewModel.handleAction(HomeAction.LoadArticles)
        advanceUntilIdle()

        assertEquals("Network error", viewModel.uiState.value.error)
        assertEquals(false, viewModel.uiState.value.isLoading)
    }

    @Test
    fun `LoadArticles failure updates error state with runtimeException`() = runTest {
        val exception = RuntimeException("Network Error")

        coEvery { getNewsUseCase(Unit) } returns flow {
            emit(Resource.Loading)
            emit(Resource.Failure(exception))
        }

        viewModel.handleAction(HomeAction.LoadArticles)

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals("Network Error", state.error)
        assertFalse(state.isLoading)
    }

    @Test
    fun `LoadArticles emits only loading keeps isLoading true`() = runTest {
        coEvery { getNewsUseCase(Unit) } returns flow {
            emit(Resource.Loading)
        }

        viewModel.handleAction(HomeAction.LoadArticles)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state.isLoading)
        assertTrue(state.originalArticles.isEmpty())
    }


    @Test
    fun `LoadArticles emits empty when no articles available`() = runTest {
        coEvery { getNewsUseCase(Unit) } returns flow {
            emit(Resource.Empty)
        }

        viewModel.handleAction(HomeAction.LoadArticles)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state.originalArticles.isEmpty())
        assertTrue(state.filteredArticles.isEmpty())
        assertFalse(state.isLoading)
    }

    @Test
    fun `Multiple rapid LoadArticles calls do not cause state issues`() = runTest {
        val articles = listOf(article)
        coEvery { getNewsUseCase(Unit) } returns flow {
            emit(Resource.Loading)
            emit(Resource.Success(articles))
        }

        repeat(5) {
            viewModel.handleAction(HomeAction.LoadArticles)
        }

        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(articles, state.originalArticles)
    }


    @Test
    fun `ToggleBookmark updates article and calls use case`() = runTest {
        coEvery { getNewsUseCase(Unit) } returns flow {
            emit(Resource.Success(listOf(article)))
        }

        viewModel.handleAction(HomeAction.LoadArticles)
        advanceUntilIdle()

        viewModel.handleAction(HomeAction.ToggleBookmark(article))
        advanceUntilIdle()

        val updatedArticle = viewModel.uiState.value.originalArticles.find { it.id == article.id }
        assertTrue(updatedArticle?.isBookmarked == true)

        coVerify { updateBookmarkStatusUseCase(article.id, true) }
    }


    @Test
    fun `Refresh triggers refresh state`() = runTest {
        coEvery { getNewsUseCase(Unit) } returns flow {
            emit(Resource.Loading)
            emit(Resource.Success(listOf(article)))
        }

        viewModel.handleAction(HomeAction.Refresh)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isRefreshing)
        assertEquals(listOf(article), state.originalArticles)
    }

    @Test
    fun `Refresh handles failure response`() = runTest {
        val exception = RuntimeException("Refresh failed")
        coEvery { getNewsUseCase(Unit) } returns flow {
            emit(Resource.Loading)
            emit(Resource.Failure(exception))
        }

        viewModel.handleAction(HomeAction.Refresh)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isRefreshing)
        assertEquals("Refresh failed", state.error)
    }


    @Test
    fun `NavigateToArticleDetails sends correct event`() = runTest {
        viewModel.uiEvent.test {
            viewModel.handleAction(HomeAction.NavigateToArticleDetails(article))
            assertEquals(HomeViewEvent.NavigateToArticleDetails(article), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}