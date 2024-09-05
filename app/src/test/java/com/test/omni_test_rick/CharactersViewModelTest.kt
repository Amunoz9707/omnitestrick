package com.test.omni_test_rick

import com.test.omni_test_rick.domain.dto.Character
import com.test.omni_test_rick.domain.dto.CharacterInfo
import com.test.omni_test_rick.domain.usecases.GetCharacterInfoUseCase
import com.test.omni_test_rick.domain.usecases.GetCharactersUseCase
import com.test.omni_test_rick.presentation.characters.CharactersViewModel
import com.test.omni_test_rick.utils.navigation.AppNavigator
import com.test.omni_test_rick.utils.navigation.Destination
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CharactersViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var mockGetCharactersUseCase: GetCharactersUseCase

    @Mock
    private lateinit var mockGetCharacterInfoUseCase: GetCharacterInfoUseCase

    @Mock
    private lateinit var mockAppNavigator: AppNavigator

    private lateinit var viewModel: CharactersViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = CharactersViewModel(
            getCharactersUseCase = mockGetCharactersUseCase,
            getCharacterInfoUseCase = mockGetCharacterInfoUseCase,
            appNavigator = mockAppNavigator
        )
    }

    @Test
    fun `initial state should be correct`() {
        val expectedState = CharactersViewModel.CharactersState(
            characters = emptyList(), count = 0, pages = 0, isLoading = false, charactersError = ""
        )
        val actualState = viewModel.state.value
        assertEquals(expectedState, actualState)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `updateCharacters should update state correctly on success`() = runBlockingTest {
        val characters = listOf(
            Character(
                id = "1",
                name = "Rick Sanchez",
                status = "Alive",
                species = "Human",
                image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
            )
        )
        val characterInfo = CharacterInfo(count = 1, pages = 1)
        `when`(mockGetCharactersUseCase.execute(1)).thenReturn(Result.success(characters))
        `when`(mockGetCharacterInfoUseCase.execute()).thenReturn(Result.success(characterInfo))

        viewModel.updateCharacters()

        val expectedState = CharactersViewModel.CharactersState(
            characters = characters,
            count = characterInfo.count,
            pages = characterInfo.pages,
            isLoading = false
        )
        assertEquals(expectedState, viewModel.state.value)
    }

    @Test
    fun `updateCharacters should update state with error`() = runBlockingTest {
        val errorMessage = "Network Error"
        `when`(mockGetCharactersUseCase.execute(1)).thenReturn(Result.failure(Exception(errorMessage)))

        viewModel.updateCharacters()
        val expectedState = CharactersViewModel.CharactersState(
            charactersError = errorMessage, isLoading = false
        )

        assertEquals(expectedState, viewModel.state.value)
    }

    @Test
    fun `onCharacterClicked should call appNavigator with correct route`() {
        val character = Character(
            id = "1",
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"

        )
        val expectedRoute = "${Destination.DetailedCharacterScreen.fullRoute}/1"
        viewModel.onCharacterClicked(character)
        verify(mockAppNavigator).tryNavigateTo(expectedRoute)
    }

    @Test
    fun `setPage should correctly increment page`() {
        viewModel.setPage(false)
        assertEquals(2, viewModel.page.value)
    }

    @Test
    fun `setPage should correctly decrement page`() {
        viewModel.setPage(true)
        assertEquals(0, viewModel.page.value)
    }

}