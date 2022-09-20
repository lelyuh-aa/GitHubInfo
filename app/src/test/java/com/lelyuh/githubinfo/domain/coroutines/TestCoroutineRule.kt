package com.lelyuh.githubinfo.domain.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Set [TestDispatcher] for [Dispatchers.Main] when use unit-tests
 */
@ExperimentalCoroutinesApi
class TestCoroutineRule : TestWatcher() {
    internal val testDispatcher = CoroutineDispatchersTest().ioDispatcher()

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}
