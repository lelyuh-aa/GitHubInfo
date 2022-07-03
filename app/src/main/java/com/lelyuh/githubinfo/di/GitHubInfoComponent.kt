package com.lelyuh.githubinfo.di

import com.lelyuh.githubinfo.presentation.activity.CommitsSummaryActivity
import com.lelyuh.githubinfo.presentation.activity.GitHubLoginActivity
import com.lelyuh.githubinfo.presentation.activity.RepositoriesListActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [GitHubInfoModule::class],
    dependencies = [ContextProviderDependencies::class]
)
interface GitHubInfoComponent {
    fun inject(activity: GitHubLoginActivity)
    fun inject(activity: RepositoriesListActivity)
    fun inject(activity: CommitsSummaryActivity)
}
