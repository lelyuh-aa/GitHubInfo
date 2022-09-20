package com.lelyuh.githubinfo.data.api

import com.lelyuh.githubinfo.models.data.CommitResponse
import com.lelyuh.githubinfo.models.data.RepositoryResponse
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Interface for interaction with server via Retrofit library
 *
 * @author Leliukh Aleksandr
 */
interface GitHubInfoApi {

    /**
     * Get list of public repositories of given user by [reposUrl]
     */
    @GET
    suspend fun repositories(
        @Url reposUrl: String
    ): List<RepositoryResponse>

    /**
     * Get information about commits on given repo by custom [commitsUrl]
     */
    @GET
    suspend fun commitsInfo(
        @Url commitsUrl: String
    ): List<CommitResponse>
}
