package com.lelyuh.githubinfo.data.repository

import com.lelyuh.githubinfo.data.preference.GitHubPreference
import com.lelyuh.githubinfo.domain.repository.GitHubFavoritesRepository

/**
 * Repository implementation for GitHub Favorites logins
 *
 * @constructor
 * @param   gitHubPreference    interface for work with data source based on shared preferences
 */
internal class GitHubFavoritesRepositoryImpl(
    private val gitHubPreference: GitHubPreference
) : GitHubFavoritesRepository {
    override fun favoritesLogins() = gitHubPreference.favoritesLogins()

    override fun updateFavoriteState(login: String) = gitHubPreference.updateFavoriteState(login)
}