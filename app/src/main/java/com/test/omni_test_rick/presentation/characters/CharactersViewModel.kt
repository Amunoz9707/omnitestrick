package com.test.omni_test_rick.presentation.characters

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.omni_test_rick.domain.dto.Character
import com.test.omni_test_rick.domain.usecases.GetCharacterInfoUseCase
import com.test.omni_test_rick.domain.usecases.GetCharactersUseCase
import com.test.omni_test_rick.utils.navigation.AppNavigator
import com.test.omni_test_rick.utils.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getCharacterInfoUseCase: GetCharacterInfoUseCase,
    private val appNavigator: AppNavigator
) : ViewModel() {

    private val _state = MutableStateFlow(CharactersState())
    val state = _state.asStateFlow()
    private val _page = mutableIntStateOf(1)
    val page: State<Int> get() = _page

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)

            }
            val getCharacters = getCharactersUseCase.execute(page.value)
            val getCharacterInfo = getCharacterInfoUseCase.execute()
            if (getCharacters.isSuccess) {
                _state.update {
                    it.copy(characters = getCharacters.getOrNull()
                        ?.sortedBy { sorted -> sorted?.name } ?: emptyList(),
                        count = getCharacterInfo.getOrNull()?.count ?: 0,
                        pages = getCharacterInfo.getOrNull()?.pages ?: 0,
                        isLoading = false)
                }
            } else if (getCharacters.isFailure) {
                _state.update {
                    it.copy(
                        charactersError = getCharacters.exceptionOrNull()?.message.toString(),
                        isLoading = false
                    )
                }
            }
        }
    }

    fun updateCharacters() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)

            }
            val getCharacters = getCharactersUseCase.execute(page.value)
            val getCharacterInfo = getCharacterInfoUseCase.execute()
            if (getCharacters.isSuccess) {
                _state.update {
                    it.copy(characters = getCharacters.getOrNull()
                        ?.sortedBy { sorted -> sorted?.name } ?: emptyList(),
                        count = getCharacterInfo.getOrNull()?.count,
                        pages = getCharacterInfo.getOrNull()?.pages,
                        isLoading = false)
                }
            } else if (getCharacters.isFailure) {
                _state.update {
                    it.copy(
                        charactersError = getCharacters.exceptionOrNull()?.message.toString(),
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onCharacterClicked(character: Character) {
        appNavigator.tryNavigateTo(Destination.DetailedCharacterScreen.fullRoute + "/${character.id}")
    }

    fun setPage(isUp: Boolean) {
        if (isUp) {
            _page.intValue = (_page.intValue - 1)
        } else {
            _page.intValue = (_page.intValue + 1)
        }
    }

    data class CharactersState(
        val characters: List<Character?> = emptyList(),
        val count: Int? = 0,
        val pages: Int? = 0,
        val isLoading: Boolean = false,
        val charactersError: String = "",
    )
}