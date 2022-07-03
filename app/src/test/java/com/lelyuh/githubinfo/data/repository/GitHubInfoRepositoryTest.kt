package com.lelyuh.githubinfo.data.repository

import com.lelyuh.githubinfo.GitHubInfoTestHelper.URL
import com.lelyuh.githubinfo.GitHubInfoTestHelper.createCommitDomainModel
import com.lelyuh.githubinfo.GitHubInfoTestHelper.createCommitResponse
import com.lelyuh.githubinfo.GitHubInfoTestHelper.createRepoModel
import com.lelyuh.githubinfo.GitHubInfoTestHelper.createRepoResponse
import com.lelyuh.githubinfo.data.api.GitHubInfoApi
import com.lelyuh.githubinfo.models.domain.CommitsDomainModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import java.net.ConnectException

class GitHubInfoRepositoryTest {
    private val gitHubApi = mockk<GitHubInfoApi>()

    private val gitHubRepository = GitHubInfoRepositoryImpl(gitHubApi)

    @Test
    fun `test success repo list first call`() {
        val apiResponse = createRepoResponse()
        val expectedModel = createRepoModel()
        every { gitHubApi.repositories(URL) } returns Single.just(apiResponse)

        gitHubRepository.repos(URL)
            .test()
            .assertValue(expectedModel)

        verify { gitHubApi.repositories(URL) }
    }

    @Test
    fun `test success repo list two calls second from cache`() {
        val apiResponse = createRepoResponse()
        val expectedModel = createRepoModel()
        every { gitHubApi.repositories(URL) } returns Single.just(apiResponse)

        gitHubRepository.repos(URL)
            .test()
            .assertValue(expectedModel)
        gitHubRepository.repos(URL)
            .test()
            .assertValue(expectedModel)

        verify(exactly = 1) { gitHubApi.repositories(URL) }
    }

    @Test
    fun `test success repo list only mandatory fields`() {
        val apiResponse = createRepoResponse(false)
        val expectedModel = createRepoModel(false)
        every { gitHubApi.repositories(URL) } returns Single.just(apiResponse)

        gitHubRepository.repos(URL)
            .test()
            .assertValue(expectedModel)

        verify { gitHubApi.repositories(URL) }
    }

    @Test
    fun `test success repo list empty data`() {
        every { gitHubApi.repositories(URL) } returns Single.just(emptyList())

        gitHubRepository.repos(URL)
            .test()
            .assertValue(emptyList())

        verify { gitHubApi.repositories(URL) }
    }

    @Test
    fun `test error repo list by server error`() {
        every { gitHubApi.repositories(URL) } returns Single.error(ConnectException())

        gitHubRepository.repos(URL)
            .test()
            .assertError(ConnectException::class.java)

        verify { gitHubApi.repositories(URL) }
    }

    @Test
    fun `test success commits list first call`() {
        val apiResponse = createCommitResponse()
        val expectedModel = createCommitDomainModel()
        every { gitHubApi.commitsInfo(URL) } returns Single.just(apiResponse)

        gitHubRepository.commitsInfo(URL)
            .test()
            .assertValue(expectedModel)

        verify { gitHubApi.commitsInfo(URL) }
    }

    @Test
    fun `test success commits list two calls second from cache`() {
        val apiResponse = createCommitResponse()
        val expectedModel = createCommitDomainModel()
        every { gitHubApi.commitsInfo(URL) } returns Single.just(apiResponse)

        gitHubRepository.commitsInfo(URL)
            .test()
            .assertValue(expectedModel)
        gitHubRepository.commitsInfo(URL)
            .test()
            .assertValue(expectedModel)

        verify(exactly = 1) { gitHubApi.commitsInfo(URL) }
    }

    @Test
    fun `test success commits list empty data`() {
        every { gitHubApi.commitsInfo(URL) } returns Single.just(emptyList())

        gitHubRepository.commitsInfo(URL)
            .test()
            .assertValue(CommitsDomainModel(emptyList(), emptySet()))

        verify { gitHubApi.commitsInfo(URL) }
    }

    @Test
    fun `test error commits list by server error`() {
        every { gitHubApi.commitsInfo(URL) } returns Single.error(ConnectException())

        gitHubRepository.commitsInfo(URL)
            .test()
            .assertError(ConnectException::class.java)

        verify { gitHubApi.commitsInfo(URL) }
    }
}
