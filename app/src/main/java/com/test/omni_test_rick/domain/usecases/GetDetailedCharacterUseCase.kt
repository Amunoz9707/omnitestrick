package com.test.omni_test_rick.domain.usecases

import com.test.omni_test_rick.domain.APIClient
import com.test.omni_test_rick.domain.dto.DetailedCharacter

class GetDetailedCharacterUseCase(
    private val apiClient: APIClient
) {
    suspend fun execute(id: String): Result<DetailedCharacter?> {
        return apiClient.getCharacter(id)
    }
}