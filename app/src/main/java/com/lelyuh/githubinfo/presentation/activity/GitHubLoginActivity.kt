package com.lelyuh.githubinfo.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.transition.TransitionManager
import com.lelyuh.githubinfo.R
import com.lelyuh.githubinfo.databinding.GitHubLoginActivityBinding
import com.lelyuh.githubinfo.presentation.fragment.FavoritesBottomSheetFragment
import com.lelyuh.githubinfo.presentation.viewmodel.GitHubLoginViewModel
import com.lelyuh.githubinfo.presentation.viewmodel.GitHubLoginViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Screen with input information about user's GitHub login to load his public repositories
 *
 * @author Leliukh Aleksandr
 */
@AndroidEntryPoint
class GitHubLoginActivity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: GitHubLoginViewModelFactory
    private val viewModel: GitHubLoginViewModel by viewModels { viewModelFactory }

    private lateinit var binding: GitHubLoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GitHubLoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initFragmentListener()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateFavoritesLogins()
    }

    private fun initViews() {
        with(binding) {
            setInputFocus()
            loginEditTextView.doOnTextChanged { newText, _, _, _ ->
                startLoadButton.isEnabled = newText.isNullOrBlank().not()
            }
            startLoadButton.setOnClickListener {
                startReposActivity(loginEditTextView.text.toString())
            }
        }
    }

    private fun setInputFocus() {
        binding.loginEditTextView.requestFocus()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }

    private fun startReposActivity(login: String) {
        startActivity(
            RepositoriesListActivity.newIntent(this@GitHubLoginActivity, login)
        )
    }

    private fun observeViewModel() {
        viewModel.favoritesLiveData.observe(this) { loginsSet ->
            with(binding) {
                TransitionManager.beginDelayedTransition(root)
                if (loginsSet.isNullOrEmpty()) {
                    loginDescriptionTextView.text = getString(R.string.git_hub_login_description)
                    favoritesTextView.isVisible = false
                } else {
                    loginDescriptionTextView.text = getString(R.string.git_hub_login_description_with_favorites)
                    favoritesTextView.setOnClickListener {
                        loginEditTextView.clearFocus()
                        FavoritesBottomSheetFragment.newInstance(loginsSet)
                            .show(supportFragmentManager, FAVORITES_DIALOG_TAG)
                    }
                    favoritesTextView.isVisible = true
                }
            }
        }
    }

    private fun initFragmentListener() {
        supportFragmentManager.setFragmentResultListener(FAVORITES_FRAGMENT_RESULT_KEY, this) { _, bundle ->
            bundle.getString(FAVORITES_FRAGMENT_BUNDLE_KEY)?.let { result ->
                if (result == FAVORITES_FRAGMENT_CANCEL_VALUE) {
                    setInputFocus()
                } else {
                    binding.loginEditTextView.setText(result)
                    startReposActivity(result)
                }
            }
        }
    }

    companion object {
        /**
         * Key for interactions with fragments in current activity by FragmentResultApi
         */
        const val FAVORITES_FRAGMENT_RESULT_KEY = "FAVORITES_FRAGMENT_RESULT_KEY"

        /**
         * Key for pass parameters between fragments and current activity by FragmentResultApi in [Bundle]
         */
        const val FAVORITES_FRAGMENT_BUNDLE_KEY = "FAVORITES_FRAGMENT_BUNDLE_KEY"

        /**
         * Case for cancel Favorites fragment without any choice
         */
        const val FAVORITES_FRAGMENT_CANCEL_VALUE = "FAVORITES_FRAGMENT_CANCEL_VALUE"

        private const val FAVORITES_DIALOG_TAG = "FavoritesBottomSheetFragment"

        fun newIntent(context: Context) = Intent(context, GitHubLoginActivity::class.java)
    }
}
