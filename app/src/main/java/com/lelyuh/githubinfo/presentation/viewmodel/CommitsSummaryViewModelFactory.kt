package com.lelyuh.githubinfo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lelyuh.githubinfo.domain.interactor.GitHubInfoInteractor
import com.lelyuh.githubinfo.domain.rx.RxSchedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class CommitsSummaryViewModelFactory @Inject constructor(
    private val interactor: GitHubInfoInteractor,
    private val rxSchedulers: RxSchedulers
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CommitsSummaryViewModel(interactor, rxSchedulers) as T
}
