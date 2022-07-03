package com.lelyuh.githubinfo.di

import android.content.Context
import com.lelyuh.githubinfo.data.api.GitHubInfoApi
import com.lelyuh.githubinfo.data.preference.GitHubPreferenceImpl
import com.lelyuh.githubinfo.data.repository.GitHubFavoritesRepositoryImpl
import com.lelyuh.githubinfo.data.repository.GitHubInfoRepositoryImpl
import com.lelyuh.githubinfo.domain.interactor.GitHubFavoritesInteractor
import com.lelyuh.githubinfo.domain.interactor.GitHubFavoritesInteractorImpl
import com.lelyuh.githubinfo.domain.interactor.GitHubInfoInteractor
import com.lelyuh.githubinfo.domain.interactor.GitHubInfoInteractorImpl
import com.lelyuh.githubinfo.domain.rx.RxSchedulers
import com.lelyuh.githubinfo.domain.rx.RxSchedulersImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class GitHubInfoModule {

    @Singleton
    @Provides
    fun provideGitHubInfoInteractor(): GitHubInfoInteractor {
        val gitHubInfoApi = Retrofit
            .Builder()
            .baseUrl(GIT_HUB_API_BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubInfoApi::class.java)
        return GitHubInfoInteractorImpl(GitHubInfoRepositoryImpl(gitHubInfoApi))
    }

    @Singleton
    @Provides
    fun provideGitHubFavoritesInteractor(
        context: Context
    ): GitHubFavoritesInteractor {
        val sharedPreferences = context.getSharedPreferences(GIT_HUB_PREFERENCE, Context.MODE_PRIVATE)
        return GitHubFavoritesInteractorImpl(
            GitHubFavoritesRepositoryImpl(GitHubPreferenceImpl(sharedPreferences))
        )
    }

    @Singleton
    @Provides
    fun provideRx(): RxSchedulers = RxSchedulersImpl()

    private companion object {
        private const val GIT_HUB_API_BASE_URL = "https://api.github.com/"
        private const val GIT_HUB_PREFERENCE = "git_hub_prefs"
    }
}
