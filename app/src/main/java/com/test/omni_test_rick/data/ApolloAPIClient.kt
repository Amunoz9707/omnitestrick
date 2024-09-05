package com.test.omni_test_rick.data

import com.apollographql.apollo.ApolloClient
import com.test.CharacterInfoQuery
import com.test.CharacterQuery
import com.test.CharactersQuery
import com.test.omni_test_rick.data.mappers.mapAPIError
import com.test.omni_test_rick.data.mappers.toCharacter
import com.test.omni_test_rick.data.mappers.toCharacterInfo
import com.test.omni_test_rick.data.mappers.toDetailedCharacter
import com.test.omni_test_rick.domain.APIClient
import com.test.omni_test_rick.domain.dto.Character
import com.test.omni_test_rick.domain.dto.CharacterInfo
import com.test.omni_test_rick.domain.dto.DetailedCharacter
import com.test.omni_test_rick.utils.network.RequestException

class ApolloAPIClient(
    private val apolloClient: ApolloClient
) : APIClient {
    override suspend fun getCharacters(page: Int): Result<List<Character?>> {

        val response = apolloClient.query(CharactersQuery(page)).execute()

        if (response.data != null) {
            if (response.data!!.characters != null) {
                return Result.success(response.data?.characters?.results?.map { it?.toCharacter() }
                    ?: emptyList())
            } else {
                val errorResponse =
                    mapAPIError(response.errors!!.first().extensions?.getValue("response") as Map<String, Any>)

                return Result.failure(
                    RequestException(
                        code = errorResponse.status, message = errorResponse.body.error
                    )
                )
            }
        }
        return Result.failure(
            RequestException(
                code = "", message = ""
            )
        )
    }

    override suspend fun getCharacterInfo(): Result<CharacterInfo?> {

        val response = apolloClient.query(CharacterInfoQuery()).execute()

        if (response.data != null) {
            if (response.data!!.characters != null) {
                return Result.success(response.data?.characters?.info?.toCharacterInfo())
            } else {
                val errorResponse =
                    mapAPIError(response.errors!!.first().extensions?.getValue("response") as Map<String, Any>)

                return Result.failure(
                    RequestException(
                        code = errorResponse.status, message = errorResponse.body.error
                    )
                )
            }
        }
        return Result.failure(
            RequestException(
                code = "", message = ""
            )
        )
    }

    override suspend fun getCharacter(id: String): Result<DetailedCharacter?> {
        val response = apolloClient.query(CharacterQuery(id)).execute()

        if (response.data != null) {
            if (response.data!!.character != null) {
                return Result.success(response.data?.character?.toDetailedCharacter())
            } else {
                val errorResponse =
                    mapAPIError(response.errors!!.first().extensions?.getValue("response") as Map<String, Any>)

                return Result.failure(
                    RequestException(
                        code = errorResponse.status, message = errorResponse.body.error
                    )
                )
            }
        }
        return Result.failure(
            RequestException(
                code = "", message = ""
            )
        )
    }
}