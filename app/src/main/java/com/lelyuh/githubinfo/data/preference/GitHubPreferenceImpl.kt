package com.lelyuh.githubinfo.data.preference

import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * Implementation for data source for work with favorite GitHub logins
 *
 * @constructor
 * @param   sharedPreference    Android preference storage for work with long saved simple data
 */
internal class GitHubPreferenceImpl(
    private val sharedPreference: SharedPreferences
) : GitHubPreference {

    override fun favoritesLogins(): Set<String>? = sharedPreference.getStringSet(GIT_HUB_LOGINS_PREFS, null)

    override fun updateFavoriteState(login: String): Boolean {
        val currentSet = LinkedHashSet(favoritesLogins())
        val isFavorite = currentSet.contains(login)
        sharedPreference.edit {
            putStringSet(GIT_HUB_LOGINS_PREFS, currentSet.apply {
                if (isFavorite) {
                    remove(login)
                } else {
                    add(login)
                }
            })
        }
        return isFavorite.not()
    }

    private companion object {
        private const val GIT_HUB_LOGINS_PREFS = "git_hub_login_prefs"
    }
}