package com.lelyuh.githubinfo.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.lelyuh.githubinfo.domain.interactor.GitHubFavoritesInteractor
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class GitHubLoginViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `test update favorites logins`() {
        val favoritesInteractor = mockk<GitHubFavoritesInteractor>()
        val favoritesObserver = mockk<Observer<Set<String>?>>(relaxUnitFun = true)
        val currentFavoritesSet = setOf("1", "2", "3")
        every { favoritesInteractor.favoritesLogins() } returns currentFavoritesSet
        val viewModel = GitHubLoginViewModel(favoritesInteractor)
        viewModel.favoritesLiveData.observeForever(favoritesObserver)

        viewModel.updateFavoritesLogins()

        verify { favoritesObserver.onChanged(currentFavoritesSet) }
    }
}