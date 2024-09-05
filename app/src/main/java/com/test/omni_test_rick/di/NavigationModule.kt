package com.test.omni_test_rick.di

import com.test.omni_test_rick.utils.navigation.AppNavigator
import com.test.omni_test_rick.utils.navigation.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {
    @Singleton
    @Binds
    fun bindAppNavigator(appNavigatorImpl: AppNavigatorImpl): AppNavigator
}