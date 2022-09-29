package com.lelyuh.githubinfo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lelyuh.githubinfo.domain.interactor.GitHubFavoritesInteractor
import com.lelyuh.githubinfo.domain.interactor.GitHubInfoInteractor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class RepositoryListViewModelFactory @Inject constructor(
    private val repoInteractor: GitHubInfoInteractor,
    private val favoritesInteractor: GitHubFavoritesInteractor
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        RepositoryListViewModel(repoInteractor, favoritesInteractor) as T
}
