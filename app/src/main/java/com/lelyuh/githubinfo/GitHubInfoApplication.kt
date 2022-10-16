package com.lelyuh.githubinfo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Custom application for create Dagger component to use DI framework on app screens
 *
 * @author Leliukh Aleksandr
 */
@HiltAndroidApp
class GitHubInfoApplication : Application()