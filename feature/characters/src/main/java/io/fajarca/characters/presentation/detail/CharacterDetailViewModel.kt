package io.fajarca.characters.presentation.detail

import androidx.lifecycle.*
import io.fajarca.characters.domain.entities.MarvelCharacterDetail
import io.fajarca.characters.domain.entities.MarvelCharacterSeries
import io.fajarca.characters.domain.usecase.GetCharactersDetailUseCase
import io.fajarca.characters.domain.usecase.GetCharactersSeriesUseCase
import io.fajarca.characters.presentation.list.CharactersViewModel
import io.fajarca.core.vo.Result
import io.fajarca.core.network.HttpResult
import kotlinx.coroutines.launch

class CharacterDetailViewModel (private val useCase: GetCharactersDetailUseCase, private val getCharactersSeriesUseCase: GetCharactersSeriesUseCase) :
    ViewModel() {

    class Factory(private val useCase: GetCharactersDetailUseCase, private val getCharactersSeriesUseCase: GetCharactersSeriesUseCase) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CharacterDetailViewModel::class.java)) {
                return CharacterDetailViewModel(useCase, getCharactersSeriesUseCase) as T
            }
            throw IllegalArgumentException("ViewModel not found.")
        }
    }


    private val _detail = MutableLiveData<Result<MarvelCharacterDetail>>()
    val characterDetail: LiveData<Result<MarvelCharacterDetail>>
        get() = _detail

    private val _series = MutableLiveData<SeriesState<List<MarvelCharacterSeries>>>()
    val series: LiveData<SeriesState<List<MarvelCharacterSeries>>>
        get() = _series

    sealed class SeriesState<out T> {
        object Empty : SeriesState<Nothing>()
        object Loading : SeriesState<Nothing>()
        data class Success<out T>(val data: T) : SeriesState<T>()
        data class Error(val cause : HttpResult) : SeriesState<Nothing>()
    }

    fun getCharacterDetail(characterId: Int) {
        _detail.postValue(Result.Loading)
        viewModelScope.launch {
            useCase.execute(characterId,
                onSuccess = { _detail.postValue(Result.Success(it)) },
                onError = { cause: HttpResult, code: Int?, errorMessage: String? ->
                    _detail.postValue(Result.Error(cause, code, errorMessage))
                })
        }
    }

    fun getCharacterSeries(characterId : Int) {
        _series.postValue(SeriesState.Loading)
        viewModelScope.launch {
            getCharactersSeriesUseCase.execute(characterId,
                onSuccess = {
                    if (it.isEmpty()) _series.value = SeriesState.Empty else _series.value = SeriesState.Success(it)
                },
                onError = { cause, _, _ ->
                    _series.value = SeriesState.Error(cause)
                })
        }
    }


}