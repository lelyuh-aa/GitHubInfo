package com.lelyuh.githubinfo.di

import android.content.Context

/**
 * Provider for context (global application context)
 *
 * @property context application context
 *
 * @author Leliukh Aleksandr
 */
interface ContextProviderDependencies {
    val context: Context
}