package com.lelyuh.githubinfo.data.repository

import com.lelyuh.githubinfo.data.preference.GitHubPreference
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Test

class GitHubFavoritesRepositoryTest {
    private val gitHubPreference = mockk<GitHubPreference>()

    private val repository = GitHubFavoritesRepositoryImpl(gitHubPreference)

    @Test
    fun `test favorites logins`() {
        val expectedFavoritesSet = setOf("1", "2", "3")
        every { gitHubPreference.favoritesLogins() } returns expectedFavoritesSet

        assertThat(repository.favoritesLogins(), `is`(expectedFavoritesSet))
    }

    @Test
    fun `test update favorite state`() {
        val login = "12345"
        every { gitHubPreference.updateFavoriteState(login) } returns true

        assertTrue(repository.updateFavoriteState(login))
    }
}