package com.lelyuh.githubinfo.di

import android.content.Context
import com.lelyuh.githubinfo.data.api.GitHubInfoApi
import com.lelyuh.githubinfo.data.preference.GitHubPreferenceImpl
import com.lelyuh.githubinfo.data.repository.GitHubFavoritesRepositoryImpl
import com.lelyuh.githubinfo.data.repository.GitHubInfoRepositoryImpl
import com.lelyuh.githubinfo.domain.coroutines.CoroutineDispatchers
import com.lelyuh.githubinfo.domain.coroutines.CoroutineDispatchersImpl
import com.lelyuh.githubinfo.domain.interactor.GitHubFavoritesInteractor
import com.lelyuh.githubinfo.domain.interactor.GitHubFavoritesInteractorImpl
import com.lelyuh.githubinfo.domain.interactor.GitHubInfoInteractor
import com.lelyuh.githubinfo.domain.interactor.GitHubInfoInteractorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GitHubInfoModule {

    @Singleton
    @Provides
    fun provideCoroutineDispatchers(): CoroutineDispatchers = CoroutineDispatchersImpl()

    @Singleton
    @Provides
    fun provideGitHubInfoInteractor(
        dispatchers: CoroutineDispatchers
    ): GitHubInfoInteractor {
        val gitHubInfoApi = Retrofit
            .Builder()
            .baseUrl(GIT_HUB_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubInfoApi::class.java)
        return GitHubInfoInteractorImpl(dispatchers, GitHubInfoRepositoryImpl(gitHubInfoApi))
    }

    @Singleton
    @Provides
    fun provideGitHubFavoritesInteractor(
        @ApplicationContext context: Context
    ): GitHubFavoritesInteractor {
        val sharedPreferences = context.getSharedPreferences(GIT_HUB_PREFERENCE, Context.MODE_PRIVATE)
        return GitHubFavoritesInteractorImpl(
            GitHubFavoritesRepositoryImpl(GitHubPreferenceImpl(sharedPreferences))
        )
    }

    private companion object {
        private const val GIT_HUB_API_BASE_URL = "https://api.github.com/"
        private const val GIT_HUB_PREFERENCE = "git_hub_prefs"
    }
}
