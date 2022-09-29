package com.lelyuh.githubinfo.domain.coroutines

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@ExperimentalCoroutinesApi
internal class CoroutineDispatchersTest : CoroutineDispatchers {
    override fun ioDispatcher() = UnconfinedTestDispatcher()
}