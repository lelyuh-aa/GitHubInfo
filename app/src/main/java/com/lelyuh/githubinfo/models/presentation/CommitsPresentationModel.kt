package com.lelyuh.githubinfo.models.presentation

/**
 * Presentation layer model representing information about commits in given repository
 *
 * @constructor
 * @property commitsModelList   list of commits models in given repository per every months, where they are exist
 * @property authorsInfo        info about authors of all commits in given repository with format Name <Email>
 *
 * @author Leliukh Aleksandr
 */
data class CommitsPresentationModel(
    val commitsModelList: List<CommitsMonthsModel>,
    val authorsInfo: String
)

/**
 * Commits models in given repository per every months, where they are exist
 *
 * @constructor
 * @property countRatio    ratio between commits count per current month and maximum for all months
 * @property count         commits count per current moths
 * @property period        period for which data are presented in format <Months Year>
 *
 * @author Leliukh Aleksandr
 */
data class CommitsMonthsModel(
    val countRatio: Float,
    val count: Int,
    val period: String
)
