package com.lelyuh.githubinfo.domain.interactor

import com.lelyuh.githubinfo.domain.repository.GitHubFavoritesRepository

/**
 * Implementation of interactor interface for work with favorite GitHub logins
 *
 * @constructor
 * @param   repository  repository interface for work with favorite GitHub logins
 *
 * @author Leliukh Aleksandr
 */
internal class GitHubFavoritesInteractorImpl(
    private val repository: GitHubFavoritesRepository
) : GitHubFavoritesInteractor {
    override fun favoritesLogins() = repository.favoritesLogins()

    override fun updateFavoriteState(login: String) = repository.updateFavoriteState(login)
}