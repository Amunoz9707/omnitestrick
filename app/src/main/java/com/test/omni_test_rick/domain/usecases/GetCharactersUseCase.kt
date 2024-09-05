package com.test.omni_test_rick.domain.usecases

import com.test.omni_test_rick.domain.APIClient
import com.test.omni_test_rick.domain.dto.Character

class GetCharactersUseCase(
    private val apiClient: APIClient
) {
    suspend fun execute(page: Int): Result<List<Character?>> {
        return apiClient.getCharacters(page)
    }
}