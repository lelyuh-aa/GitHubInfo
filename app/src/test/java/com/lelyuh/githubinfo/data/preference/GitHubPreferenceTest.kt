package com.lelyuh.githubinfo.data.preference

import android.content.SharedPreferences
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GitHubPreferenceTest {
    private val sharedPreferences = mockk<SharedPreferences>()
    private val editor = mockk<SharedPreferences.Editor>(relaxUnitFun = true)

    private val gitHubPreference = GitHubPreferenceImpl(sharedPreferences)

    @Before
    fun `set up`() {
        every { sharedPreferences.edit() } returns editor
    }

    @Test
    fun `test favorites logins`() {
        val expectedFavoritesSet = setOf("1", "2", "3")
        every { sharedPreferences.getStringSet(GIT_HUB_LOGINS_PREFS, null) } returns expectedFavoritesSet

        assertThat(gitHubPreference.favoritesLogins(), `is`(expectedFavoritesSet))
    }

    @Test
    fun `test update favorite state with new login`() {
        val currentFavoritesSet = setOf("1", "2", "3")
        val expectedFavoritesSet = setOf("2", "3")
        val currentLogin = "1"
        every { sharedPreferences.getStringSet(GIT_HUB_LOGINS_PREFS, null) } returns currentFavoritesSet
        every { editor.putStringSet(GIT_HUB_LOGINS_PREFS, expectedFavoritesSet) } returns editor

        assertFalse(gitHubPreference.updateFavoriteState(currentLogin))

        verify {
            editor.putStringSet(GIT_HUB_LOGINS_PREFS, expectedFavoritesSet)
            editor.apply()
        }
    }

    @Test
    fun `test update favorite state with existing login`() {
        val currentFavoritesSet = setOf("2", "3")
        val expectedFavoritesSet = setOf("1", "2", "3")
        val currentLogin = "1"
        every { sharedPreferences.getStringSet(GIT_HUB_LOGINS_PREFS, null) } returns currentFavoritesSet
        every { editor.putStringSet(GIT_HUB_LOGINS_PREFS, expectedFavoritesSet) } returns editor

        assertTrue(gitHubPreference.updateFavoriteState(currentLogin))

        verify {
            editor.putStringSet(GIT_HUB_LOGINS_PREFS, expectedFavoritesSet)
            editor.apply()
        }
    }

    private companion object {
        private const val GIT_HUB_LOGINS_PREFS = "git_hub_login_prefs"
    }
}