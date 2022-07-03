package com.lelyuh.githubinfo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lelyuh.githubinfo.domain.interactor.GitHubFavoritesInteractor

/**
 * View-model for screen with input information about user's GitHub login
 *
 * @constructor
 * @param   favoritesInteractor      interactor for getting information about favorites logins
 *
 * @author Leliukh Aleksandr
 */
internal class GitHubLoginViewModel(
    private val favoritesInteractor: GitHubFavoritesInteractor
) : ViewModel() {

    private val _favoritesLiveData = MutableLiveData<Set<String>?>()

    val favoritesLiveData: LiveData<Set<String>?>
        get() = _favoritesLiveData

    /**
     * Update information about current favorite logins for represent these to user
     */
    fun updateFavoritesLogins() {
        _favoritesLiveData.value = favoritesInteractor.favoritesLogins()
    }
}