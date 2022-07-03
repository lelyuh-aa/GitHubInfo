package com.lelyuh.githubinfo.domain.interactor

/**
 * Interactor interface for work with favorite GitHub logins
 *
 * @author Leliukh Aleksandr
 */
interface GitHubFavoritesInteractor {

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