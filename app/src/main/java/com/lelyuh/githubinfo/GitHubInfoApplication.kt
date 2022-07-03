package com.lelyuh.githubinfo

import android.app.Application
import com.lelyuh.githubinfo.di.DaggerGitHubInfoComponent
import com.lelyuh.githubinfo.di.DefaultContextProviderDependencies
import com.lelyuh.githubinfo.di.GitHubInfoComponent

/**
 * Custom application for create Dagger component to use DI framework on app screens
 *
 * @author Leliukh Aleksandr
 */
class GitHubInfoApplication : Application() {
    val appComponent: GitHubInfoComponent = DaggerGitHubInfoComponent
        .builder()
        .contextProviderDependencies(DefaultContextProviderDependencies(this))
        .build()
}