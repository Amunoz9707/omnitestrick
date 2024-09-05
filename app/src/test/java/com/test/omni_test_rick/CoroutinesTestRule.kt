package com.test.omni_test_rick

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class CoroutinesTestRule(
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : TestRule {
    override fun apply(base: Statement, description: Description): Statement {
        return statement(base)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun statement(base: Statement): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                Dispatchers.setMain(testDispatcher)
                try {
                    base.evaluate()
                } finally {
                    Dispatchers.resetMain()
                    testDispatcher.cleanupTestCoroutines()
                }
            }
        }
    }
}