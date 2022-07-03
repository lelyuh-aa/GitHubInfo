package com.lelyuh.githubinfo.data.repository

import com.lelyuh.githubinfo.data.api.GitHubInfoApi
import com.lelyuh.githubinfo.domain.repository.GitHubInfoRepository
import com.lelyuh.githubinfo.models.data.CommitResponse
import com.lelyuh.githubinfo.models.domain.CommitsDomainModel
import com.lelyuh.githubinfo.models.domain.RepositoryListModel
import io.reactivex.rxjava3.core.Single
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Repository implementation for GitHub information
 * Realize caching of loaded information to avoid redundant queries to server
 *
 * @constructor
 * @param   api  server api for loading information about GitHub repositories
 *
 * @author Leliukh Aleksandr
 */
internal class GitHubInfoRepositoryImpl(
    private val api: GitHubInfoApi
) : GitHubInfoRepository {

    private val cacheRepositories = hashMapOf<String, List<RepositoryListModel>>()
    private val cacheCommitsInfo = hashMapOf<String, CommitsDomainModel>()

    override fun repos(reposUrl: String): Single<List<RepositoryListModel>> =
        cacheRepositories[reposUrl]?.let { repoList ->
            Single.just(repoList)
        } ?: api.repositories(reposUrl)
            .map { response ->
                val repoModelList = response.map { responseListItem ->
                    RepositoryListModel(
                        responseListItem.name,
                        responseListItem.description,
                        responseListItem.language,
                        responseListItem.commitsUrl.substringBefore(LEFT_BRACE_SYMBOL)
                    )
                }
                cacheRepositories[reposUrl] = repoModelList
                repoModelList
            }

    override fun commitsInfo(commitsUrl: String): Single<CommitsDomainModel> =
        cacheCommitsInfo[commitsUrl]?.let { commitModel ->
            Single.just(commitModel)
        } ?: api.commitsInfo(commitsUrl)
            .map { response ->
                mapResponseCommitList(commitsUrl, response)
            }

    private fun mapResponseCommitList(commitsUrl: String, responseList: List<CommitResponse>): CommitsDomainModel {
        val dateList = arrayListOf<Date>()
        val authorsSet = sortedSetOf<String>()
        responseList.forEach { commitBean ->
            val authorBean = commitBean.commit.author
            DATE_FORMATTER.parse(authorBean.date)?.let { date ->
                dateList.add(date)
            }
            authorsSet.add(String.format(AUTHOR_FORMAT, authorBean.name, authorBean.email))
        }
        val domainModel = CommitsDomainModel(dateList, authorsSet)
        cacheCommitsInfo[commitsUrl] = domainModel
        return domainModel
    }

    companion object {
        private const val LEFT_BRACE_SYMBOL = "{"
        private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
        private val DATE_FORMATTER = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)
        private const val AUTHOR_FORMAT = "%s <%s>"
    }
}
