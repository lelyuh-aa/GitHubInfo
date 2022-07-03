package com.lelyuh.githubinfo.domain.repository

/**
 * Repository interface for work with favorite GitHub logins
 *
 * @author Leliukh Aleksandr
 */
interface GitHubFavoritesRepository {

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