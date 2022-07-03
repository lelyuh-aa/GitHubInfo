package com.lelyuh.githubinfo.models.domain

import java.io.Serializable

/**
 * Domain layer model representing list item of each repository
 *
 * @constructor
 * @property name           repository name
 * @property description    repository description
 * @property language       programming language in which the code is written in given repository
 * @property commitsUrl     httpUrl of commit list of given repository
 *
 * @author Leliukh Aleksandr
 */
data class RepositoryListModel(
    val name: String,
    val description: String?,
    val language: String?,
    val commitsUrl: String
): Serializable
