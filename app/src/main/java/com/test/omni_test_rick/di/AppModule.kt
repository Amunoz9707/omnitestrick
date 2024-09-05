package com.test.omni_test_rick.di

import com.apollographql.apollo.ApolloClient
import com.test.omni_test_rick.data.ApolloAPIClient
import com.test.omni_test_rick.domain.APIClient
import com.test.omni_test_rick.domain.usecases.GetCharacterInfoUseCase
import com.test.omni_test_rick.domain.usecases.GetCharactersUseCase
import com.test.omni_test_rick.domain.usecases.GetDetailedCharacterUseCase
import com.test.omni_test_rick.utils.network.Environment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder().serverUrl(Environment.currentEnvironment).build()
    }

    @Provides
    @Singleton
    fun provideAPIClient(apolloClient: ApolloClient): APIClient {
        return ApolloAPIClient(apolloClient)
    }

    @Provides
    @Singleton
    fun provideGetCharactersUseCase(apiClient: APIClient): GetCharactersUseCase {
        return GetCharactersUseCase(apiClient)
    }

    @Provides
    @Singleton
    fun provideGetCharacterInfoUseCase(apiClient: APIClient): GetCharacterInfoUseCase {
        return GetCharacterInfoUseCase(apiClient)
    }

    @Provides
    @Singleton
    fun provideGetDetailCharacterUseCase(apiClient: APIClient): GetDetailedCharacterUseCase {
        return GetDetailedCharacterUseCase(apiClient)
    }
}