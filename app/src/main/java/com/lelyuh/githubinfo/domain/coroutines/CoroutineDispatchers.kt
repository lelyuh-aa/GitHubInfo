package com.lelyuh.githubinfo.domain.coroutines

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Interface for dispatchers to work with coroutines
 *
 * @author Leliukh Aleksandr
 */
interface CoroutineDispatchers {

    fun ioDispatcher(): CoroutineDispatcher
}