package com.lelyuh.githubinfo.models.domain

import java.util.Date

/**
 * Domain layer model representing information about commits in given repository
 *
 * @constructor
 * @property commitsDateList    list of commit dates in given repository
 * @property authorsSet         info about authors of all commits in given repository in format Name <Email>
 *
 * @author Leliukh Aleksandr
 */
data class CommitsDomainModel(
    val commitsDateList: List<Date>,
    val authorsSet: Set<String>
)
