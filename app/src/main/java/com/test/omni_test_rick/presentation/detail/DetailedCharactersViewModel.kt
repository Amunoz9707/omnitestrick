package com.test.omni_test_rick.presentation.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.omni_test_rick.domain.dto.DetailedCharacter
import com.test.omni_test_rick.domain.usecases.GetDetailedCharacterUseCase
import com.test.omni_test_rick.utils.navigation.AppNavigator
import com.test.omni_test_rick.utils.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailedCharacterViewModel @Inject constructor(
    private val getDetailedCharacterUseCase: GetDetailedCharacterUseCase,
    private val appNavigator: AppNavigator,
) : ViewModel() {

    private val _state = MutableStateFlow(DetailedCharacterState())
    val state = _state.asStateFlow()

    fun getCharacter(id: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            val result = getDetailedCharacterUseCase.execute(id)
            if (result.isSuccess) {
                _state.update {
                    it.copy(
                        detailedCharacter = result.getOrNull(),
                        isLoading = false,
                    )
                }
            } else if (result.isFailure) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        detailedCharacterError = result.exceptionOrNull()?.message.toString()
                    )
                }
            }
        }
    }

    fun goBack() {
        appNavigator.tryNavigateBack(Destination.CharactersScreen.fullRoute)
    }

    data class DetailedCharacterState(
        val detailedCharacter: DetailedCharacter? = null,
        val isLoading: Boolean = false,
        val detailedCharacterError: String = "",
    )
}