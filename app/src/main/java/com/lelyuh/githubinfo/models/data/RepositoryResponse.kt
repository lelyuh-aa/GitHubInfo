package com.lelyuh.githubinfo.models.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * Server response for repositories list
 *
 * @constructor
 * @property name           repository name
 * @property description    repository description
 * @property language       programming language in which the code is written in given repository
 * @property commitsUrl     httpUrl of commit list of given repository
 *
 * @author Leliukh Aleksandr
 */
data class RepositoryResponse(
    val name: String,
    val description: String?,
    val language: String?,
    @SerializedName("commits_url") val commitsUrl: String
)
