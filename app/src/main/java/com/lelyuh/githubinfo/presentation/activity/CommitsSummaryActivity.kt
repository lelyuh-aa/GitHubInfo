package com.lelyuh.githubinfo.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.lelyuh.githubinfo.GitHubInfoApplication
import com.lelyuh.githubinfo.R
import com.lelyuh.githubinfo.databinding.RepoSummaryActivityBinding
import com.lelyuh.githubinfo.models.domain.RepositoryListModel
import com.lelyuh.githubinfo.presentation.error.ErrorHelper.showErrorDialog
import com.lelyuh.githubinfo.presentation.fragment.AuthorsBottomSheetFragment
import com.lelyuh.githubinfo.presentation.viewmodel.CommitsSummaryViewModel
import com.lelyuh.githubinfo.presentation.viewmodel.CommitsSummaryViewModelFactory
import javax.inject.Inject

/**
 * Screen with detail information about commits in given repository
 *
 * @author Leliukh Aleksandr
 */
class CommitsSummaryActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: CommitsSummaryViewModelFactory
    private val viewModel: CommitsSummaryViewModel by viewModels { viewModelFactory }

    private lateinit var binding: RepoSummaryActivityBinding
    private lateinit var repoModel: RepositoryListModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as GitHubInfoApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = RepoSummaryActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        runStartAnimation()
        initLoad()
    }

    override fun onBackPressed() {
        with(binding) {
            summaryContainer.startAnimation(
                AnimationUtils.loadAnimation(baseContext, R.anim.bottom_container_down_animation)
            )
            root.postDelayed(
                {
                    summaryContainer.isGone = true
                    finish()
                },
                DELAY_CLOSE_SCREEN
            )
        }
    }

    private fun runStartAnimation() {
        with(binding) {
            root.startAnimation(
                AnimationUtils.loadAnimation(baseContext, R.anim.background_enter_animation)
            )
            summaryContainer.startAnimation(
                AnimationUtils.loadAnimation(baseContext, R.anim.bottom_container_up_animation)
            )
        }
    }

    private fun initLoad() {
        (intent.getSerializableExtra(REPO_MODEL_KEY) as? RepositoryListModel)?.let { model ->
            repoModel = model
            initView()
            observeViewModel()
            loadCommitsInfo()
        } ?: showErrorDialog(this)
    }

    private fun initView() {
        with(binding) {
            repoNameTextView.text = repoModel.name
            repoModel.description?.let { description ->
                repoDescriptionTextView.text = description
                repoDescriptionTextView.isVisible = true
            }
            repoModel.language?.let { language ->
                repoLanguageTextView.text = getString(R.string.language_format, language)
                repoLanguageTextView.isVisible = true
            }
        }
    }

    private fun observeViewModel() {
        viewModel.authorsLiveData.observe(this) { authorsInfo ->
            TransitionManager.beginDelayedTransition(binding.summaryContainer)
            val listener = View.OnClickListener { showAuthorsDialog(authorsInfo) }
            binding.authorsTextView.setOnClickListener(listener)
            binding.chevronIconView.setOnClickListener(listener)
            binding.authorsGroup.isVisible = true
        }

        viewModel.commitsMonthLiveData.observe(this, binding.commitsInfoView::setData)

        viewModel.finishSubmitCommitsLiveData.observe(this) {
            with(binding.root) {
                postDelayed(
                    {
                        Snackbar
                            .make(this, R.string.commits_shown_success_info, Snackbar.LENGTH_LONG)
                            .show()
                    },
                    DELAY_SHOW_SNACKBAR_INFO
                )
            }
        }

        viewModel.errorLiveData.observe(this) {
            showErrorDialog(this)
        }
    }

    private fun loadCommitsInfo() {
        viewModel.commitsInfo(repoModel.commitsUrl)
    }

    private fun showAuthorsDialog(authorsInfo: String) {
        AuthorsBottomSheetFragment
            .newInstance(authorsInfo)
            .show(supportFragmentManager, AUTHORS_DIALOG_TAG)
    }

    companion object {
        private const val REPO_MODEL_KEY = "repoModelKey"
        private const val DELAY_CLOSE_SCREEN = 500L
        private const val DELAY_SHOW_SNACKBAR_INFO = 1000L
        private const val AUTHORS_DIALOG_TAG = "AuthorsBottomSheetFragment"

        fun newIntent(context: Context, repoModel: RepositoryListModel) =
            Intent(context, CommitsSummaryActivity::class.java).apply {
                putExtra(REPO_MODEL_KEY, repoModel)
            }
    }
}
