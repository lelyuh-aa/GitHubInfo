package com.lelyuh.githubinfo.data.api

import com.lelyuh.githubinfo.models.data.CommitResponse
import com.lelyuh.githubinfo.models.data.RepositoryResponse
import io.reactivex.rxjava3.core.Single
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
    fun repositories(
        @Url reposUrl: String
    ): Single<List<RepositoryResponse>>

    /**
     * Get information about commits on given repo by custom [commitsUrl]
     */
    @GET
    fun commitsInfo(
        @Url commitsUrl: String
    ): Single<List<CommitResponse>>
}
