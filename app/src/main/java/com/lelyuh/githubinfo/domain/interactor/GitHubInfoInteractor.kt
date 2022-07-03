package com.lelyuh.githubinfo.domain.interactor

import com.lelyuh.githubinfo.models.domain.RepositoryListModel
import com.lelyuh.githubinfo.models.presentation.CommitsPresentationModel
import io.reactivex.rxjava3.core.Single

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
     * @return [Single] reactive source of [List]<[RepositoryListModel]>
     */
    fun repos(reposUrl: String): Single<List<RepositoryListModel>>

    /**
     * Get information about commits on given repo by custom [commitsUrl]
     *
     * @return [Single] reactive source of [CommitsPresentationModel]
     */
    fun commitsInfo(commitsUrl: String): Single<CommitsPresentationModel>
}