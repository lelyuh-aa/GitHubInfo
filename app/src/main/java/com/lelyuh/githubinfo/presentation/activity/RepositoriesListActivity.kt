package com.lelyuh.githubinfo.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import com.lelyuh.githubinfo.R
import com.lelyuh.githubinfo.databinding.RepoListActivityBinding
import com.lelyuh.githubinfo.models.domain.RepositoryListModel
import com.lelyuh.githubinfo.presentation.adapter.RepositoryListAdapter
import com.lelyuh.githubinfo.presentation.error.ErrorHelper.showErrorDialog
import com.lelyuh.githubinfo.presentation.viewmodel.RepositoryListViewModel
import com.lelyuh.githubinfo.presentation.viewmodel.RepositoryListViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Screen with list of public repositories of given GitHub user
 *
 * @author Leliukh Aleksandr
 */
@AndroidEntryPoint
class RepositoriesListActivity : AppCompatActivity(), RepositoryListAdapter.OnItemClickListener {

    @Inject
    internal lateinit var viewModelFactory: RepositoryListViewModelFactory
    private val viewModel: RepositoryListViewModel by viewModels { viewModelFactory }

    private lateinit var listAdapter: RepositoryListAdapter
    private lateinit var binding: RepoListActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RepoListActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val login = intent.getStringExtra(LOGIN_USER_KEY).orEmpty()
        initToolbar(login)
        initAdapter()
        observeViewModel()
        loadRepos(login)
    }

    override fun onItemClick(model: RepositoryListModel) {
        startActivity(CommitsSummaryActivity.newIntent(this, model))
    }

    private fun initToolbar(title: String) {
        with(binding.toolbar) {
            setSupportActionBar(this)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = title
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
        binding.favoritesIconView.setOnClickListener {
            viewModel.updateFavoriteState()
        }
    }

    private fun initAdapter() {
        listAdapter = RepositoryListAdapter(this)
        binding.recyclerView.adapter = listAdapter
    }

    private fun observeViewModel() {
        viewModel.repoLiveData.observe(this) { reposList ->
            listAdapter.setListData(reposList)
            binding.progressLayout.progress.isVisible = false
        }
        viewModel.initFavoriteLiveData.observe(this) { isFavorite ->
            with(binding.favoritesIconView) {
                setImageResource(if (isFavorite) R.drawable.ic_remove_from_favorite else R.drawable.ic_add_to_favorites)
                isVisible = true
            }
        }
        viewModel.updateFavoriteLiveData.observe(this) { isAddToFavorite ->
            binding.favoritesIconView.setImageResource(
                if (isAddToFavorite) R.drawable.ic_remove_from_favorite else R.drawable.ic_add_to_favorites
            )
            Snackbar
                .make(
                    binding.root,
                    if (isAddToFavorite) R.string.added_to_favorites_text else R.string.removed_from_favorites_text,
                    LENGTH_LONG
                )
                .show()
        }
        viewModel.errorLiveData.observe(this) { errorResId ->
            showErrorDialog(this, errorResId)
            binding.progressLayout.progress.isVisible = false
        }
    }

    private fun loadRepos(login: String) {
        viewModel.loadRepos(getString(R.string.repositories_list_url_format, login), login)
    }

    companion object {
        private const val LOGIN_USER_KEY = "loginUserKey"

        fun newIntent(context: Context, login: String) =
            Intent(context, RepositoriesListActivity::class.java).apply {
                putExtra(LOGIN_USER_KEY, login)
            }
    }
}
