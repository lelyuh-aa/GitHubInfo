package com.lelyuh.githubinfo.domain.interactor

import com.lelyuh.githubinfo.GitHubInfoTestHelper.URL
import com.lelyuh.githubinfo.GitHubInfoTestHelper.createCommitDomainModel
import com.lelyuh.githubinfo.GitHubInfoTestHelper.createCommitPresentationModel
import com.lelyuh.githubinfo.GitHubInfoTestHelper.createRepoModel
import com.lelyuh.githubinfo.domain.coroutines.CoroutineDispatchersTest
import com.lelyuh.githubinfo.domain.repository.GitHubInfoRepository
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
class GitHubInfoInteractorTest {
    private val gitHubRepisitory = mockk<GitHubInfoRepository>()

    private val gitHubInfoInteractor = GitHubInfoInteractorImpl(CoroutineDispatchersTest(), gitHubRepisitory)

    @Test
    fun `test success repo list`() = runTest {
        val repoListModel = createRepoModel()
        coEvery { gitHubRepisitory.repos(URL) } returns repoListModel

        assertThat(gitHubInfoInteractor.repos(URL), `is`(repoListModel))
        coVerify { gitHubRepisitory.repos(URL) }
    }

    @Test(expected = ConnectException::class)
    fun `test error repo list`() = runTest {
        coEvery { gitHubRepisitory.repos(URL) } throws ConnectException()

        gitHubInfoInteractor.repos(URL)

        coVerify { gitHubRepisitory.repos(URL) }
    }

    @Test
    fun `test success commit info list`() = runTest {
        val commitDomainModel = createCommitDomainModel()
        val expectedModel = createCommitPresentationModel()
        coEvery { gitHubRepisitory.commitsInfo(URL) } returns commitDomainModel

        assertThat(gitHubInfoInteractor.commitsInfo(URL), `is`(expectedModel))
        coVerify { gitHubRepisitory.commitsInfo(URL) }
    }

    @Test(expected = NoSuchElementException::class)
    fun `test success commit info empty commits data`() = runTest {
        val commitDomainModel = createCommitDomainModel(isCommitsExist = false)
        coEvery { gitHubRepisitory.commitsInfo(URL) } returns commitDomainModel

        gitHubInfoInteractor.commitsInfo(URL)

        coVerify { gitHubRepisitory.commitsInfo(URL) }
    }

    @Test
    fun `test success commit info empty authors data`() = runTest {
        val commitDomainModel = createCommitDomainModel(isAuthorsExist = false)
        val expectedModel = createCommitPresentationModel(isAuthorsExist = false)
        coEvery { gitHubRepisitory.commitsInfo(URL) } returns commitDomainModel

        assertThat(gitHubInfoInteractor.commitsInfo(URL), `is`(expectedModel))
        coVerify { gitHubRepisitory.commitsInfo(URL) }
    }

    @Test(expected = ConnectException::class)
    fun `test error commit info`() = runTest {
        coEvery { gitHubRepisitory.commitsInfo(URL) } throws ConnectException()

        gitHubInfoInteractor.commitsInfo(URL)

        coVerify { gitHubRepisitory.commitsInfo(URL) }
    }
}
