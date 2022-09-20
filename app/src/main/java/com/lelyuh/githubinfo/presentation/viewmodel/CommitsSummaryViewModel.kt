package com.lelyuh.githubinfo.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lelyuh.githubinfo.domain.interactor.GitHubInfoInteractor
import com.lelyuh.githubinfo.models.presentation.CommitsMonthsModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * View-model for screen with information about commits in given repository
 *
 * @constructor
 * @param   interactor  interactor for getting information to represent it to user
 *
 * @author Leliukh Aleksandr
 */
internal class CommitsSummaryViewModel(
    private val interactor: GitHubInfoInteractor
) : ViewModel() {

    private val _authorsLiveData = MutableLiveData<String>()
    private val _commitsMonthLiveData = MutableLiveData<CommitsMonthsModel>()
    private val _finishSubmitCommitsLiveData = MutableLiveData<Unit>()
    private val _errorLiveData = MutableLiveData<Unit>()

    val authorsLiveData: LiveData<String>
        get() = _authorsLiveData
    val commitsMonthLiveData: LiveData<CommitsMonthsModel>
        get() = _commitsMonthLiveData
    val finishSubmitCommitsLiveData: LiveData<Unit>
        get() = _finishSubmitCommitsLiveData
    val errorLiveData: LiveData<Unit>
        get() = _errorLiveData

    /**
     * Load info about commits in given repository by [commitsUrl]
     */
    fun commitsInfo(commitsUrl: String) {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            Log.e("CommitsSummaryViewModel", throwable.message.orEmpty(), throwable)
            _errorLiveData.value = Unit
        }) {
            val commitModel = interactor.commitsInfo(commitsUrl)
            if (commitModel.authorsInfo.isNotBlank()) {
                _authorsLiveData.value = commitModel.authorsInfo
            }
            if (commitModel.commitsModelList.isEmpty()) {
                _errorLiveData.value = Unit
            } else {
                submitDelayedCommitsInfo(commitModel.commitsModelList)
            }
        }
    }

    private suspend fun submitDelayedCommitsInfo(commitsMonthList: List<CommitsMonthsModel>) =
        with(commitsMonthList.iterator()) {
            delay(INITIAL_DELAY_MILLIS)
            while (hasNext()) {
                _commitsMonthLiveData.value = next()
                if (hasNext()) {
                    delay(INTERMEDIATE_DELAY_MILLIS)
                }
            }
            _finishSubmitCommitsLiveData.value = Unit
        }

    private companion object {
        const val INITIAL_DELAY_MILLIS = 500L
        const val INTERMEDIATE_DELAY_MILLIS = 1500L
    }
}
