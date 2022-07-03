package com.lelyuh.githubinfo.presentation.viewmodel

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.lelyuh.githubinfo.GitHubInfoTestHelper.URL
import com.lelyuh.githubinfo.GitHubInfoTestHelper.createRepoModel
import com.lelyuh.githubinfo.R
import com.lelyuh.githubinfo.domain.interactor.GitHubFavoritesInteractor
import com.lelyuh.githubinfo.domain.interactor.GitHubInfoInteractor
import com.lelyuh.githubinfo.domain.rx.RxSchedulersStub
import com.lelyuh.githubinfo.models.domain.RepositoryListModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RepositoryListViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val gitHubInfoInteractor = mockk<GitHubInfoInteractor>()
    private val favoritesInteractor = mockk<GitHubFavoritesInteractor>()

    private val repositoriesObserver = mockk<Observer<List<RepositoryListModel>>>(relaxUnitFun = true)
    private val initFavoriteObserver = mockk<Observer<Boolean>>(relaxUnitFun = true)
    private val updateFavoriteObserver = mockk<Observer<Boolean>>(relaxUnitFun = true)

    private val errorObserver = mockk<Observer<Int>>(relaxUnitFun = true)

    private val viewModel = RepositoryListViewModel(gitHubInfoInteractor, favoritesInteractor, RxSchedulersStub())

    @Before
    fun `set up`() {
        mockkStatic(Log::class)
        viewModel.repoLiveData.observeForever(repositoriesObserver)
        viewModel.initFavoriteLiveData.observeForever(initFavoriteObserver)
        viewModel.updateFavoriteLiveData.observeForever(updateFavoriteObserver)
        viewModel.errorLiveData.observeForever(errorObserver)
        every { Log.e(any(), any(), any()) } returns 0
    }

    @Test
    fun `test success get repositories with new login`() {
        val repoModels = createRepoModel()
        val currentFavoritesSet = setOf("2", "3")
        val currentLogin = "1"
        every { gitHubInfoInteractor.repos(URL) } returns Single.just(repoModels)
        every { favoritesInteractor.favoritesLogins() } returns currentFavoritesSet

        viewModel.loadRepos(URL, currentLogin)

        verify {
            repositoriesObserver.onChanged(repoModels)
            initFavoriteObserver.onChanged(false)
        }
        verify(exactly = 0) { errorObserver.onChanged(any()) }
    }

    @Test
    fun `test success get repositories with existing login`() {
        val repoModels = createRepoModel()
        val currentFavoritesSet = setOf("1", "2", "3")
        val currentLogin = "1"
        every { gitHubInfoInteractor.repos(URL) } returns Single.just(repoModels)
        every { favoritesInteractor.favoritesLogins() } returns currentFavoritesSet

        viewModel.loadRepos(URL, currentLogin)

        verify {
            repositoriesObserver.onChanged(repoModels)
            initFavoriteObserver.onChanged(true)
        }
        verify(exactly = 0) { errorObserver.onChanged(any()) }
    }

    @Test
    fun `test success get repositories with null favorites`() {
        val repoModels = createRepoModel()
        every { gitHubInfoInteractor.repos(URL) } returns Single.just(repoModels)
        every { favoritesInteractor.favoritesLogins() } returns null

        viewModel.loadRepos(URL, "any")

        verify {
            repositoriesObserver.onChanged(repoModels)
            initFavoriteObserver.onChanged(false)
        }
        verify(exactly = 0) { errorObserver.onChanged(any()) }
    }

    @Test
    fun `test error on get empty repositories`() {
        val currentFavoritesSet = setOf("1", "2", "3")
        val currentLogin = "1"
        every { gitHubInfoInteractor.repos(URL) } returns Single.just(emptyList())
        every { favoritesInteractor.favoritesLogins() } returns currentFavoritesSet

        viewModel.loadRepos(URL, currentLogin)

        verify {
            initFavoriteObserver.onChanged(true)
            errorObserver.onChanged(R.string.empty_list_message)
        }
        verify(exactly = 0) { repositoriesObserver.onChanged(any()) }
    }

    @Test
    fun `test any exceptions on get repositories`() {
        every { gitHubInfoInteractor.repos(URL) } returns Single.error(Throwable())

        viewModel.loadRepos(URL, "any")

        verify { errorObserver.onChanged(R.string.error_message) }
        verify(exactly = 0) {
            repositoriesObserver.onChanged(any())
            initFavoriteObserver.onChanged(any())
        }
    }

    @Test
    fun `test update favorite state`() {
        val currentLogin = "1"
        val currentFavoritesSet = setOf("1", "2", "3")
        val repoModels = createRepoModel()
        every { gitHubInfoInteractor.repos(URL) } returns Single.just(repoModels)
        every { favoritesInteractor.favoritesLogins() } returns currentFavoritesSet
        every { favoritesInteractor.updateFavoriteState(currentLogin) } returns false

        viewModel.loadRepos(URL, currentLogin)
        viewModel.updateFavoriteState()

        verify {
            repositoriesObserver.onChanged(repoModels)
            initFavoriteObserver.onChanged(true)
            updateFavoriteObserver.onChanged(false)
        }
        verify(exactly = 0) {
            errorObserver.onChanged(any())
        }
    }

    @After
    fun `clear data`() {
        unmockkStatic(Log::class)
    }
}
