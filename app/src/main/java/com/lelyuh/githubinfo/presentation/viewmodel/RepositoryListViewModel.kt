package com.lelyuh.githubinfo.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lelyuh.githubinfo.R
import com.lelyuh.githubinfo.domain.interactor.GitHubFavoritesInteractor
import com.lelyuh.githubinfo.domain.interactor.GitHubInfoInteractor
import com.lelyuh.githubinfo.models.domain.RepositoryListModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

/**
 * View-model for screens with list of repositories of given user
 *
 * @constructor
 * @param   repoInteractor      interactor for getting information about public repositories to represent it to user
 * @param   favoritesInteractor interactor for manage with Favorites
 *
 * @author Leliukh Aleksandr
 */
internal class RepositoryListViewModel(
    private val repoInteractor: GitHubInfoInteractor,
    private val favoritesInteractor: GitHubFavoritesInteractor
) : ViewModel() {

    private val _repoLiveData = MutableLiveData<List<RepositoryListModel>>()
    private val _initFavoriteLiveData = MutableLiveData<Boolean>()
    private val _updateFavoriteLiveData = MutableLiveData<Boolean>()
    private val _errorLiveData = MutableLiveData<Int>()

    val repoLiveData: LiveData<List<RepositoryListModel>>
        get() = _repoLiveData
    val initFavoriteLiveData: LiveData<Boolean>
        get() = _initFavoriteLiveData
    val updateFavoriteLiveData: LiveData<Boolean>
        get() = _updateFavoriteLiveData
    val errorLiveData: LiveData<Int>
        get() = _errorLiveData

    private lateinit var currentLogin: String

    /**
     * Load info about all public repository by [reposUrl] of given user
     */
    fun loadRepos(reposUrl: String, login: String) {
        currentLogin = login
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            Log.e("RepositoryListViewModel", throwable.message.orEmpty(), throwable)
            _errorLiveData.postValue(R.string.error_message)
        }) {
            val reposList = repoInteractor.repos(reposUrl)
            _initFavoriteLiveData.postValue(
                favoritesInteractor.favoritesLogins()?.contains(currentLogin) ?: false
            )
            if (reposList.isEmpty()) {
                _errorLiveData.postValue(R.string.empty_list_message)
            } else {
                _repoLiveData.postValue(reposList)
            }
        }
    }

    /**
     * Update favorite state (add or remove) by user action at toolbar
     */
    fun updateFavoriteState() {
        _updateFavoriteLiveData.value = favoritesInteractor.updateFavoriteState(currentLogin)
    }
}
