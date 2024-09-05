package com.test.omni_test_rick.domain.usecases

import com.test.omni_test_rick.domain.APIClient
import com.test.omni_test_rick.domain.dto.CharacterInfo

class GetCharacterInfoUseCase(
    private val apiClient: APIClient
) {
    suspend fun execute(): Result<CharacterInfo?> {
        return apiClient.getCharacterInfo()
    }
}