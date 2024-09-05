package com.test.omni_test_rick.data.mappers


import com.test.CharacterInfoQuery
import com.test.CharacterQuery
import com.test.CharactersQuery
import com.test.omni_test_rick.domain.dto.Character
import com.test.omni_test_rick.domain.dto.CharacterInfo
import com.test.omni_test_rick.domain.dto.DetailedCharacter
import com.test.omni_test_rick.domain.dto.Error
import com.test.omni_test_rick.domain.dto.ErrorBody

fun CharactersQuery.Result.toCharacter(): Character {
    return Character(
        id = id ?: "Missing ID",
        name = name ?: "Missing name",
        status = status ?: "Missing status",
        species = species ?: "Missing species",
        image = image ?: "Missing image",
    )
}


fun CharacterInfoQuery.Info.toCharacterInfo(): CharacterInfo {
    return CharacterInfo(
        count = count ?: 0,
        pages = pages ?: 0,
    )
}

fun CharacterQuery.Character.toDetailedCharacter(): DetailedCharacter {
    return DetailedCharacter(
        id = id ?: "Missing ID",
        name = name ?: "Missing name",
        status = status ?: "Missing status",
        species = species ?: "Missing species",
        origin = origin?.name ?: "Missing origin",
        location = location?.name ?: "Missing location",
        image = image ?: "Missing image",
    )
}

fun mapAPIError(errorMap: Map<String, Any>): Error {
    val url = errorMap["url"] as String
    val status = (errorMap["status"] as Number).toInt()
    val statusText = errorMap["statusText"] as String
    val bodyMap = errorMap["body"] as Map<*, *>
    val errorBody = ErrorBody(error = bodyMap["error"] as String)

    return Error(
        url = url, status = status, statusText = statusText, body = errorBody
    )
}
