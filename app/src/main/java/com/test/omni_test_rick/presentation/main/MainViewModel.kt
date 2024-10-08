package com.test.omni_test_rick.presentation.main

import androidx.lifecycle.ViewModel
import com.test.omni_test_rick.utils.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    appNavigator: AppNavigator
) : ViewModel() {

    val navigationChannel = appNavigator.navigationChannel
}