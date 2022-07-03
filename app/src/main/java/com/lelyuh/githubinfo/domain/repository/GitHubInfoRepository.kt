package com.lelyuh.githubinfo.domain.repository

import com.lelyuh.githubinfo.models.domain.CommitsDomainModel
import com.lelyuh.githubinfo.models.domain.RepositoryListModel
import io.reactivex.rxjava3.core.Single

/**
 * Repository interface for GitHub information
 *
 * @author Leliukh Aleksandr
 */
interface GitHubInfoRepository {

    /**
     * Get information about GitHub repos as list of [RepositoryListModel] by [reposUrl]
     *
     * @return [Single] reactive source of [List]<[RepositoryListModel]>
     */
    fun repos(reposUrl: String): Single<List<RepositoryListModel>>

    /**
     * Get information about commits on given repo by custom [commitsUrl]
     *
     * @return [Single] reactive source of [CommitsDomainModel]
     */
    fun commitsInfo(commitsUrl: String): Single<CommitsDomainModel>
}