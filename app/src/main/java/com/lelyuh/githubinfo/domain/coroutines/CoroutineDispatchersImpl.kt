package com.lelyuh.githubinfo.domain.coroutines

import kotlinx.coroutines.Dispatchers

internal class CoroutineDispatchersImpl : CoroutineDispatchers {
    override fun ioDispatcher() = Dispatchers.IO
}