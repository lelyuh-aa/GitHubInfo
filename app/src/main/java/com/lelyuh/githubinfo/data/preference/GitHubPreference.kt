package com.lelyuh.githubinfo.data.preference

/**
 * Data source for work with favorite GitHub logins
 *
 * @author Leliukh Aleksandr
 */
interface GitHubPreference {

    /**
     * Get favorites logins
     */
    fun favoritesLogins(): Set<String>?

    /**
     * Adding or removing current login to/from favorites
     * @return true if added, false if removed
     */
    fun updateFavoriteState(login: String): Boolean
}