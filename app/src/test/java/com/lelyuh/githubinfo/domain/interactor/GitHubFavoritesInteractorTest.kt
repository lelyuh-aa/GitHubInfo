package com.lelyuh.githubinfo.domain.interactor

import com.lelyuh.githubinfo.domain.repository.GitHubFavoritesRepository
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Test

class GitHubFavoritesInteractorTest {
    private val gitHubRepository = mockk<GitHubFavoritesRepository>()

    private val interactor = GitHubFavoritesInteractorImpl(gitHubRepository)

    @Test
    fun `test favorites logins`() {
        val expectedFavoritesSet = setOf("1", "2", "3")
        every { gitHubRepository.favoritesLogins() } returns expectedFavoritesSet

        assertThat(interactor.favoritesLogins(), `is`(expectedFavoritesSet))
    }

    @Test
    fun `test update favorite state`() {
        val login = "12345"
        every { gitHubRepository.updateFavoriteState(login) } returns true

        assertTrue(interactor.updateFavoriteState(login))
    }
}