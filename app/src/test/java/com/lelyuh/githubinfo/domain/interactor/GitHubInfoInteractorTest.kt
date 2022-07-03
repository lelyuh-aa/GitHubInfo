package com.lelyuh.githubinfo.domain.interactor

import com.lelyuh.githubinfo.GitHubInfoTestHelper.URL
import com.lelyuh.githubinfo.GitHubInfoTestHelper.createCommitDomainModel
import com.lelyuh.githubinfo.GitHubInfoTestHelper.createCommitPresentationModel
import com.lelyuh.githubinfo.GitHubInfoTestHelper.createRepoModel
import com.lelyuh.githubinfo.domain.repository.GitHubInfoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import java.net.ConnectException

class GitHubInfoInteractorTest {
    private val gitHubRepisitory = mockk<GitHubInfoRepository>()

    private val gitHubInfoInteractor = GitHubInfoInteractorImpl(gitHubRepisitory)

    @Test
    fun `test success repo list`() {
        val repoListModel = createRepoModel()
        every { gitHubRepisitory.repos(URL) } returns Single.just(repoListModel)

        gitHubInfoInteractor.repos(URL)
            .test()
            .assertValue(repoListModel)

        verify { gitHubRepisitory.repos(URL) }
    }

    @Test
    fun `test error repo list`() {
        every { gitHubRepisitory.repos(URL) } returns Single.error(ConnectException())

        gitHubInfoInteractor.repos(URL)
            .test()
            .assertError(ConnectException::class.java)

        verify { gitHubRepisitory.repos(URL) }
    }

    @Test
    fun `test success commit info list`() {
        val commitDomainModel = createCommitDomainModel()
        val expectedModel = createCommitPresentationModel()
        every { gitHubRepisitory.commitsInfo(URL) } returns Single.just(commitDomainModel)

        gitHubInfoInteractor.commitsInfo(URL)
            .test()
            .assertValue(expectedModel)

        verify { gitHubRepisitory.commitsInfo(URL) }
    }

    @Test
    fun `test success commit info empty commits data`() {
        val commitDomainModel = createCommitDomainModel(isCommitsExist = false)
        every { gitHubRepisitory.commitsInfo(URL) } returns Single.just(commitDomainModel)

        gitHubInfoInteractor.commitsInfo(URL)
            .test()
            .assertError(NoSuchElementException::class.java)

        verify { gitHubRepisitory.commitsInfo(URL) }
    }

    @Test
    fun `test success commit info empty authors data`() {
        val commitDomainModel = createCommitDomainModel(isAuthorsExist = false)
        val expectedModel = createCommitPresentationModel(isAuthorsExist = false)
        every { gitHubRepisitory.commitsInfo(URL) } returns Single.just(commitDomainModel)

        gitHubInfoInteractor.commitsInfo(URL)
            .test()
            .assertValue(expectedModel)

        verify { gitHubRepisitory.commitsInfo(URL) }
    }

    @Test
    fun `test error commit info`() {
        every { gitHubRepisitory.commitsInfo(URL) } returns Single.error(ConnectException())

        gitHubInfoInteractor.commitsInfo(URL)
            .test()
            .assertError(ConnectException::class.java)

        verify { gitHubRepisitory.commitsInfo(URL) }
    }
}
