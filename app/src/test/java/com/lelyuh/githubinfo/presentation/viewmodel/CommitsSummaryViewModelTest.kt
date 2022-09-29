package com.lelyuh.githubinfo.presentation.viewmodel

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.lelyuh.githubinfo.GitHubInfoTestHelper.COMMIT_PRESENTATION_AUTHORS_INFO
import com.lelyuh.githubinfo.GitHubInfoTestHelper.URL
import com.lelyuh.githubinfo.GitHubInfoTestHelper.createCommitPresentationModel
import com.lelyuh.githubinfo.GitHubInfoTestHelper.createFirstCommitMonthModel
import com.lelyuh.githubinfo.GitHubInfoTestHelper.createSecondCommitMonthModel
import com.lelyuh.githubinfo.domain.coroutines.TestCoroutineRule
import com.lelyuh.githubinfo.domain.interactor.GitHubInfoInteractor
import com.lelyuh.githubinfo.models.presentation.CommitsMonthsModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CommitsSummaryViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    private val gitHubInfoInteractor = mockk<GitHubInfoInteractor>()
    private val authorsObserver = mockk<Observer<String>>(relaxUnitFun = true)
    private val commitsMonthObserver = mockk<Observer<CommitsMonthsModel>>(relaxUnitFun = true)
    private val finishSubmitCommitsObserver = mockk<Observer<Unit>>(relaxUnitFun = true)
    private val errorObserver = mockk<Observer<Unit>>(relaxUnitFun = true)

    private val viewModel = CommitsSummaryViewModel(gitHubInfoInteractor)

    @Before
    fun `set up`() {
        viewModel.authorsLiveData.observeForever(authorsObserver)
        viewModel.commitsMonthLiveData.observeForever(commitsMonthObserver)
        viewModel.finishSubmitCommitsLiveData.observeForever(finishSubmitCommitsObserver)
        viewModel.errorLiveData.observeForever(errorObserver)
    }

    @Test
    fun `test success commits info`() {
        val commitPresentationModel = createCommitPresentationModel()
        val firstMonthCommitModel = createFirstCommitMonthModel()
        val secondMonthCommitModel = createSecondCommitMonthModel()
        coEvery { gitHubInfoInteractor.commitsInfo(URL) } returns commitPresentationModel

        viewModel.commitsInfo(URL)
        coroutineRule.testDispatcher.scheduler.advanceUntilIdle()

        verify {
            authorsObserver.onChanged(COMMIT_PRESENTATION_AUTHORS_INFO)
            commitsMonthObserver.onChanged(firstMonthCommitModel)
            commitsMonthObserver.onChanged(secondMonthCommitModel)
            finishSubmitCommitsObserver.onChanged(Unit)
        }
        verify(exactly = 0) { errorObserver.onChanged(Unit) }
    }

    @Test
    fun `test success commits info empty authors data`() {
        val commitPresentationModel = createCommitPresentationModel(isAuthorsExist = false)
        val firstMonthCommitModel = createFirstCommitMonthModel()
        val secondMonthCommitModel = createSecondCommitMonthModel()
        coEvery { gitHubInfoInteractor.commitsInfo(URL) } returns commitPresentationModel

        viewModel.commitsInfo(URL)
        coroutineRule.testDispatcher.scheduler.advanceUntilIdle()

        verify {
            commitsMonthObserver.onChanged(firstMonthCommitModel)
            commitsMonthObserver.onChanged(secondMonthCommitModel)
            finishSubmitCommitsObserver.onChanged(Unit)
        }
        verify(exactly = 0) {
            authorsObserver.onChanged(any())
            errorObserver.onChanged(Unit)
        }
    }

    @Test
    fun `test error empty commits info`() {
        val commitPresentationModel = createCommitPresentationModel(isCommitsExist = false, isAuthorsExist = false)
        coEvery { gitHubInfoInteractor.commitsInfo(URL) } returns commitPresentationModel

        viewModel.commitsInfo(URL)

        verify {
            errorObserver.onChanged(Unit)
        }
        verify(exactly = 0) {
            commitsMonthObserver.onChanged(any())
            authorsObserver.onChanged(any())
            finishSubmitCommitsObserver.onChanged(Unit)
        }
    }

    @Test
    fun `test error commits info`() {
        mockkStatic(Log::class)
        coEvery { gitHubInfoInteractor.commitsInfo(URL) } throws Throwable()
        every { Log.e(any(), any(), any()) } returns 0
        viewModel.errorLiveData.observeForever(errorObserver)

        viewModel.commitsInfo(URL)

        verify { errorObserver.onChanged(Unit) }
        verify(exactly = 0) {
            authorsObserver.onChanged(any())
            commitsMonthObserver.onChanged(any())
            commitsMonthObserver.onChanged(any())
            finishSubmitCommitsObserver.onChanged(Unit)
        }
        unmockkStatic(Log::class)
    }
}
