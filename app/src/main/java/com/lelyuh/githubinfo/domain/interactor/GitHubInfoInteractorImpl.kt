package com.lelyuh.githubinfo.domain.interactor

import com.lelyuh.githubinfo.domain.coroutines.CoroutineDispatchers
import com.lelyuh.githubinfo.domain.repository.GitHubInfoRepository
import com.lelyuh.githubinfo.models.domain.CommitGroupKeyModel
import com.lelyuh.githubinfo.models.domain.CommitsDomainModel
import com.lelyuh.githubinfo.models.presentation.CommitsMonthsModel
import com.lelyuh.githubinfo.models.presentation.CommitsPresentationModel
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Interactor implementation for git hub information
 *
 * @constructor
 * @param   dispatchers coroutine dispatchers for choosing context
 * @param   repository  repository for retrieving information about public GitHub repositories
 *
 * @author Leliukh Aleksandr
 */
internal class GitHubInfoInteractorImpl(
    private val dispatchers: CoroutineDispatchers,
    private val repository: GitHubInfoRepository
) : GitHubInfoInteractor {

    override suspend fun repos(reposUrl: String) = withContext(dispatchers.ioDispatcher()) {
        repository.repos(reposUrl)
    }

    override suspend fun commitsInfo(commitsUrl: String) = withContext(dispatchers.ioDispatcher()) {
        mapCommitDomainModel(repository.commitsInfo(commitsUrl))
    }

    private fun mapCommitDomainModel(domainModel: CommitsDomainModel): CommitsPresentationModel {
        val groupingMap = groupDateByMonths(domainModel.commitsDateList)
        val maxCommitsPerMonth = groupingMap.values.maxOf { it }.toFloat()
        val commitModelList = mutableListOf<CommitsMonthsModel>()
        groupingMap.forEach { entry ->
            commitModelList.add(
                CommitsMonthsModel(
                    entry.value.toFloat() / maxCommitsPerMonth,
                    entry.value,
                    entry.key.keyStr
                )
            )
        }
        return CommitsPresentationModel(commitModelList, convertAuthorsSet(domainModel.authorsSet))
    }

    private fun groupDateByMonths(dateList: List<Date>): Map<CommitGroupKeyModel, Int> {
        val groupingMap = sortedMapOf<CommitGroupKeyModel, Int>()
        dateList.forEach { commitDate ->
            val calendar = Calendar.getInstance()
            calendar.time = commitDate
            val monthKey = CommitGroupKeyModel(
                Pair(calendar[Calendar.MONTH], calendar[Calendar.YEAR]),
                String.format(
                    GROUP_KEY_STR_FORMAT,
                    calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH),
                    calendar.get(Calendar.YEAR)
                )
            )
            if (groupingMap.containsKey(monthKey)) {
                groupingMap[monthKey] = groupingMap[monthKey]?.inc()
            } else {
                groupingMap[monthKey] = 1
            }
        }
        return groupingMap
    }

    private fun convertAuthorsSet(authorsSet: Set<String>): String {
        val builder = StringBuilder()
        val iterator = authorsSet.iterator()
        while (iterator.hasNext()) {
            builder.append(iterator.next())
            if (iterator.hasNext()) {
                builder.append(DELIMITER)
            }
        }
        return builder.toString()
    }

    companion object {
        private const val GROUP_KEY_STR_FORMAT = "%s %s"
        private const val DELIMITER = "\n\n"
    }
}
