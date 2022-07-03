package com.lelyuh.githubinfo.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lelyuh.githubinfo.domain.interactor.GitHubInfoInteractor
import com.lelyuh.githubinfo.domain.rx.RxSchedulers
import com.lelyuh.githubinfo.models.presentation.CommitsMonthsModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

/**
 * View-model for screen with information about commits in given repository
 *
 * @constructor
 * @param   interactor      interactor for getting information to represent it to user
 * @param   rxSchedulers    schedulers for async work
 *
 * @author Leliukh Aleksandr
 */
internal class CommitsSummaryViewModel(
    private val interactor: GitHubInfoInteractor,
    private val rxSchedulers: RxSchedulers
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

    private val disposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    /**
     * Load info about commits in given repository by [commitsUrl]
     */
    fun commitsInfo(commitsUrl: String) {
        disposable.add(
            interactor
                .commitsInfo(commitsUrl)
                .subscribeOn(rxSchedulers.io())
                .observeOn(rxSchedulers.ui())
                .subscribe(
                    { commitModel ->
                        if (commitModel.authorsInfo.isNotBlank()) {
                            _authorsLiveData.postValue(commitModel.authorsInfo)
                        }
                        if (commitModel.commitsModelList.isEmpty()) {
                            _errorLiveData.postValue(Unit)
                        } else {
                            submitDelayedCommitsInfo(commitModel.commitsModelList)
                        }
                    },
                    { throwable ->
                        Log.e("CommitsSummaryViewModel", throwable.message.orEmpty(), throwable)
                        _errorLiveData.postValue(Unit)
                    }
                )
        )
    }

    private fun submitDelayedCommitsInfo(commitsMonthList: List<CommitsMonthsModel>) {
        val intervalObservable = Observable
            .interval(
                TIMER_INITIAL_DELAY,
                TIMER_PERIOD_MILLIS,
                TimeUnit.MILLISECONDS,
                rxSchedulers.io()
            )
            .take(commitsMonthList.size.toLong())
        disposable.add(
            intervalObservable
                .subscribeOn(rxSchedulers.io())
                .observeOn(rxSchedulers.ui())
                .subscribe(
                    { currentIndex ->
                        _commitsMonthLiveData.postValue(commitsMonthList[currentIndex.toInt()])
                    },
                    { throwable ->
                        Log.e("CommitsSummaryViewModel", throwable.message.orEmpty(), throwable)
                        _errorLiveData.postValue(Unit)
                    },
                    {
                        _finishSubmitCommitsLiveData.postValue(Unit)
                        disposable.clear()
                    }
                )
        )
    }

    private companion object {
        private const val TIMER_INITIAL_DELAY = 500L
        private const val TIMER_PERIOD_MILLIS = 1500L
    }
}
