package com.lelyuh.githubinfo

import com.lelyuh.githubinfo.models.data.AuthorBody
import com.lelyuh.githubinfo.models.data.CommitBody
import com.lelyuh.githubinfo.models.data.CommitResponse
import com.lelyuh.githubinfo.models.data.RepositoryResponse
import com.lelyuh.githubinfo.models.domain.CommitsDomainModel
import com.lelyuh.githubinfo.models.domain.RepositoryListModel
import com.lelyuh.githubinfo.models.presentation.CommitsMonthsModel
import com.lelyuh.githubinfo.models.presentation.CommitsPresentationModel
import java.util.Date

object GitHubInfoTestHelper {

    const val URL = "url"
    const val COMMIT_PRESENTATION_AUTHORS_INFO = "Aleksandr <email2>\n\nJohn <email1>"

    private const val REPO_NAME_1 = "repository1"
    private const val REPO_NAME_2 = "repository2"
    private const val REPO_DESCRIPTION_1 = "repositoryDescription1"
    private const val REPO_DESCRIPTION_2 = "repositoryDescription2"
    private const val REPO_LANGUAGE_1 = "repositoryLanguage1"
    private const val REPO_LANGUAGE_2 = "repositoryLanguage2"
    private const val REPO_COMMITS_URL_1 = "repositoryCommitsUrl1"
    private const val REPO_COMMITS_URL_2 = "repositoryCommitsUrl2"

    private const val COMMIT_AUTHOR_1 = "John"
    private const val COMMIT_AUTHOR_2 = "Aleksandr"
    private const val COMMIT_EMAIL_1 = "email1"
    private const val COMMIT_EMAIL_2 = "email2"
    private const val COMMIT_SERVER_DATE_1 = "2013-10-02T23:58:48Z"
    private const val COMMIT_SERVER_DATE_2 = "2013-06-10T23:38:59Z"
    private const val COMMIT_SERVER_DATE_3 = "2013-06-06T16:26:22Z"
    private const val COMMIT_MODEL_AUTHOR_1 = "John <email1>"
    private const val COMMIT_MODEL_AUTHOR_2 = "Aleksandr <email2>"
    private const val COMMIT_MODEL_DATE_1 = 1380743928000
    private const val COMMIT_MODEL_DATE_2 = 1370893139000
    private const val COMMIT_MODEL_DATE_3 = 1370521582000
    private const val COMMIT_PRESENTATION_DATE_1 = "June 2013"
    private const val COMMIT_PRESENTATION_DATE_2 = "October 2013"

    fun createRepoResponse(isAllFields: Boolean = true) =
        listOf(
            RepositoryResponse(
                REPO_NAME_1,
                REPO_DESCRIPTION_1.takeIf { isAllFields },
                REPO_LANGUAGE_1.takeIf { isAllFields },
                REPO_COMMITS_URL_1
            ),
            RepositoryResponse(
                REPO_NAME_2,
                REPO_DESCRIPTION_2.takeIf { isAllFields },
                REPO_LANGUAGE_2.takeIf { isAllFields },
                REPO_COMMITS_URL_2
            )
        )

    fun createRepoModel(isAllFields: Boolean = true) =
        listOf(
            RepositoryListModel(
                REPO_NAME_1,
                REPO_DESCRIPTION_1.takeIf { isAllFields },
                REPO_LANGUAGE_1.takeIf { isAllFields },
                REPO_COMMITS_URL_1
            ),
            RepositoryListModel(
                REPO_NAME_2,
                REPO_DESCRIPTION_2.takeIf { isAllFields },
                REPO_LANGUAGE_2.takeIf { isAllFields },
                REPO_COMMITS_URL_2
            )
        )

    fun createCommitResponse() =
        listOf(
            CommitResponse(
                CommitBody(
                    AuthorBody(COMMIT_AUTHOR_1, COMMIT_EMAIL_1, COMMIT_SERVER_DATE_1)
                )
            ),
            CommitResponse(
                CommitBody(
                    AuthorBody(COMMIT_AUTHOR_2, COMMIT_EMAIL_2, COMMIT_SERVER_DATE_2)
                )
            ),
            CommitResponse(
                CommitBody(
                    AuthorBody(COMMIT_AUTHOR_2, COMMIT_EMAIL_2, COMMIT_SERVER_DATE_3)
                )
            )
        )

    fun createCommitDomainModel(isCommitsExist: Boolean = true, isAuthorsExist: Boolean = true) =
        CommitsDomainModel(
            if (isCommitsExist)
                listOf(
                    Date(COMMIT_MODEL_DATE_1), Date(COMMIT_MODEL_DATE_2), Date(COMMIT_MODEL_DATE_3)
                ) else
                emptyList(),
            if (isAuthorsExist)
                setOf(COMMIT_MODEL_AUTHOR_2, COMMIT_MODEL_AUTHOR_1)
            else
                emptySet()
        )

    fun createCommitPresentationModel(isCommitsExist: Boolean = true, isAuthorsExist: Boolean = true) =
        CommitsPresentationModel(
            if (isCommitsExist)
                listOf(
                    createFirstCommitMonthModel(),
                    createSecondCommitMonthModel(),
                )
            else
                emptyList(),
            if (isAuthorsExist) COMMIT_PRESENTATION_AUTHORS_INFO else ""
        )

    fun createFirstCommitMonthModel() = CommitsMonthsModel(1.0F, 2, COMMIT_PRESENTATION_DATE_1)

    fun createSecondCommitMonthModel() = CommitsMonthsModel(0.5F, 1, COMMIT_PRESENTATION_DATE_2)
}
