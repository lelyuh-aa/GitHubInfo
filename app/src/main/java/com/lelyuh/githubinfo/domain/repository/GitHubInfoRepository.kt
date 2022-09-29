package com.lelyuh.githubinfo.domain.repository

import com.lelyuh.githubinfo.models.domain.CommitsDomainModel
import com.lelyuh.githubinfo.models.domain.RepositoryListModel

/**
 * Repository interface for GitHub information
 *
 * @author Leliukh Aleksandr
 */
interface GitHubInfoRepository {

    /**
     * Get information about GitHub repos as list of [RepositoryListModel] by [reposUrl]
     *
     * @return [List]<[RepositoryListModel]>
     */
    suspend fun repos(reposUrl: String): List<RepositoryListModel>

    /**
     * Get information about commits on given repo by custom [commitsUrl]
     *
     * @return [CommitsDomainModel]
     */
    suspend fun commitsInfo(commitsUrl: String): CommitsDomainModel
}