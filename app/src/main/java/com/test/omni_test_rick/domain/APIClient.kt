package com.test.omni_test_rick.domain

import com.test.omni_test_rick.domain.dto.Character
import com.test.omni_test_rick.domain.dto.CharacterInfo
import com.test.omni_test_rick.domain.dto.DetailedCharacter

interface APIClient {
    suspend fun getCharacters(page: Int): Result<List<Character?>>
    suspend fun getCharacterInfo(): Result<CharacterInfo?>
    suspend fun getCharacter(id: String): Result<DetailedCharacter?>
}