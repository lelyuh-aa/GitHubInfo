package com.lelyuh.githubinfo.domain.interactor

import com.lelyuh.githubinfo.models.domain.RepositoryListModel
import com.lelyuh.githubinfo.models.presentation.CommitsPresentationModel

/**
 * Interactor interface for git hub information
 * Contains all use cases to interact with user actions
 *
 * @author Leliukh Aleksandr
 */
interface GitHubInfoInteractor {

    /**
     * Get information about GitHub repos as list of [RepositoryListModel] by [reposUrl]
     *
     * @return [List]<[RepositoryListModel]>
     */
    suspend fun repos(reposUrl: String): List<RepositoryListModel>

    /**
     * Get information about commits on given repo by custom [commitsUrl]
     *
     * @return [CommitsPresentationModel]
     */
    suspend fun commitsInfo(commitsUrl: String): CommitsPresentationModel
}