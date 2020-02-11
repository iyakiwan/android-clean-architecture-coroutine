package io.fajarca.news.domain.usecase

import io.fajarca.news.domain.repository.NewsRepository
import io.fajarca.core.network.HttpResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class GetCharactersDetailUseCaseTest {

    private lateinit var sut : GetCharactersDetailUseCase
    @Mock
    private lateinit var repository: NewsRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sut = GetCharactersDetailUseCase(repository)
    }

    @Test
    fun `when use case is executed, should get character detail from repository`() = runBlockingTest {
        //Given
        val characterId = 4

        //When
        sut.execute(characterId, {}, {cause: HttpResult, code: Int?, errorMessage: String? ->  })

        //Then
        verify(repository).getCharacterDetail(characterId)
    }
}