package com.lelyuh.githubinfo.models.data

import androidx.annotation.Keep

/**
 * Server response bean for commit list
 *
 * @constructor
 * @property commit server bean for represent commit info
 *
 * @author Leliukh Aleksandr
 */
data class CommitResponse(
    val commit: CommitBody
)

/**
 * Server entity for represent commit info
 *
 * @constructor
 * @property author server bean for represent author info
 *
 * @author Leliukh Aleksandr
 */
data class CommitBody(
    val author: AuthorBody
)

/**
 * Server entity for represent author info
 *
 * @constructor
 * @property name   name of commit author
 * @property email  email of commit author
 * @property date   date of commit
 *
 * @author Leliukh Aleksandr
 */
data class AuthorBody(
    val name: String,
    val email: String,
    val date: String
)
