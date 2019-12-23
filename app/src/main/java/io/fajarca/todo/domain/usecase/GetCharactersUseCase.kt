package io.fajarca.todo.domain.usecase

import io.fajarca.todo.data.repository.HomeRepositoryImpl
import io.fajarca.todo.domain.model.Character
import io.fajarca.todo.domain.model.common.Result
import io.fajarca.todo.ui.CoroutinesDispatcherProvider
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val repository: HomeRepositoryImpl) : UseCase()  {

    suspend fun execute(dispatcherProvider: CoroutinesDispatcherProvider) : Result<List<Character>> {
        return safeApiCall(dispatcherProvider) { repository.getAllCharacters()}
    }

}