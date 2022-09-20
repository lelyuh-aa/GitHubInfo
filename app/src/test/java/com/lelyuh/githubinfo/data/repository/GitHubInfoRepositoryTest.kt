package com.lelyuh.githubinfo.data.repository

import com.lelyuh.githubinfo.GitHubInfoTestHelper.URL
import com.lelyuh.githubinfo.GitHubInfoTestHelper.createCommitDomainModel
import com.lelyuh.githubinfo.GitHubInfoTestHelper.createCommitResponse
import com.lelyuh.githubinfo.GitHubInfoTestHelper.createRepoModel
import com.lelyuh.githubinfo.GitHubInfoTestHelper.createRepoResponse
import com.lelyuh.githubinfo.data.api.GitHubInfoApi
import com.lelyuh.githubinfo.models.domain.CommitsDomainModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.net.ConnectException

@ExperimentalCoroutinesApi
class GitHubInfoRepositoryTest {
    private val gitHubApi = mockk<GitHubInfoApi>()

    private val gitHubRepository = GitHubInfoRepositoryImpl(gitHubApi)

    @Test
    fun `test success repo list first call`() = runTest {
        val apiResponse = createRepoResponse()
        val expectedModel = createRepoModel()
        coEvery { gitHubApi.repositories(URL) } returns apiResponse

        assertThat(gitHubRepository.repos(URL), `is`(expectedModel))
        coVerify { gitHubApi.repositories(URL) }
    }

    @Test
    fun `test success repo list two calls second from cache`() = runTest {
        val apiResponse = createRepoResponse()
        val expectedModel = createRepoModel()
        coEvery { gitHubApi.repositories(URL) } returns apiResponse

        val actualResult1 = gitHubRepository.repos(URL)
        val actualResult2 = gitHubRepository.repos(URL)

        assertThat(actualResult1, `is`(expectedModel))
        assertThat(actualResult2, `is`(expectedModel))
        coVerify(exactly = 1) { gitHubApi.repositories(URL) }
    }

    @Test
    fun `test success repo list only mandatory fields`() = runTest {
        val apiResponse = createRepoResponse(false)
        val expectedModel = createRepoModel(false)
        coEvery { gitHubApi.repositories(URL) } returns apiResponse

        assertThat(gitHubRepository.repos(URL), `is`(expectedModel))
        coVerify { gitHubApi.repositories(URL) }
    }

    @Test
    fun `test success repo list empty data`() = runTest {
        coEvery { gitHubApi.repositories(URL) } returns emptyList()

        assertThat(gitHubRepository.repos(URL), `is`(emptyList()))
        coVerify { gitHubApi.repositories(URL) }
    }

    @Test(expected = ConnectException::class)
    fun `test error repo list by server error`() = runTest {
        coEvery { gitHubApi.repositories(URL) } throws ConnectException()

        gitHubRepository.repos(URL)

        coVerify { gitHubApi.repositories(URL) }
    }

    @Test
    fun `test success commits list first call`() = runTest {
        val apiResponse = createCommitResponse()
        val expectedModel = createCommitDomainModel()
        coEvery { gitHubApi.commitsInfo(URL) } returns apiResponse

        assertThat(gitHubRepository.commitsInfo(URL), `is`(expectedModel))
        coVerify { gitHubApi.commitsInfo(URL) }
    }

    @Test
    fun `test success commits list two calls second from cache`() = runTest {
        val apiResponse = createCommitResponse()
        val expectedModel = createCommitDomainModel()
        coEvery { gitHubApi.commitsInfo(URL) } returns apiResponse

        val actualResult1 = gitHubRepository.commitsInfo(URL)
        val actualResult2 = gitHubRepository.commitsInfo(URL)

        assertThat(actualResult1, `is`(expectedModel))
        assertThat(actualResult2, `is`(expectedModel))
        coVerify(exactly = 1) { gitHubApi.commitsInfo(URL) }
    }

    @Test
    fun `test success commits list empty data`() = runTest {
        coEvery { gitHubApi.commitsInfo(URL) } returns emptyList()

        assertThat(gitHubRepository.commitsInfo(URL), `is`(CommitsDomainModel(emptyList(), emptySet())))
        coVerify { gitHubApi.commitsInfo(URL) }
    }

    @Test(expected = ConnectException::class)
    fun `test error commits list by server error`() = runTest {
        coEvery { gitHubApi.commitsInfo(URL) } throws ConnectException()

        gitHubRepository.commitsInfo(URL)

        coVerify { gitHubApi.commitsInfo(URL) }
    }
}
