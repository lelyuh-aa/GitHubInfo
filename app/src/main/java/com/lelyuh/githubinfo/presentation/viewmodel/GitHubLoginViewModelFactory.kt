package com.lelyuh.githubinfo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lelyuh.githubinfo.domain.interactor.GitHubFavoritesInteractor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitHubLoginViewModelFactory @Inject constructor(
    private val favoritesInteractor: GitHubFavoritesInteractor
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = GitHubLoginViewModel(favoritesInteractor) as T
}