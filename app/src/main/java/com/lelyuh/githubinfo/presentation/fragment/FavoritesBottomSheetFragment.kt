package com.lelyuh.githubinfo.presentation.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lelyuh.githubinfo.databinding.FavotitesInfoFragmentBinding
import com.lelyuh.githubinfo.presentation.activity.GitHubLoginActivity.Companion.FAVORITES_FRAGMENT_BUNDLE_KEY
import com.lelyuh.githubinfo.presentation.activity.GitHubLoginActivity.Companion.FAVORITES_FRAGMENT_CANCEL_VALUE
import com.lelyuh.githubinfo.presentation.activity.GitHubLoginActivity.Companion.FAVORITES_FRAGMENT_RESULT_KEY

/**
 * Bottom fragment with information about favorites logins representing as list of [String]
 * Each login list item is clickable and automatically launches loading repositories by chosen login
 *
 * @author Leliukh Aleksandr
 */
internal class FavoritesBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FavotitesInfoFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FavotitesInfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getStringArray(LOGINS_KEY)?.let { logins ->
            with(binding.loginsListView) {
                adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, logins)
                onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                    setResult(logins[position])
                    this@FavoritesBottomSheetFragment.dismiss()
                }
            }
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        setResult(FAVORITES_FRAGMENT_CANCEL_VALUE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setResult(result: String) {
        parentFragmentManager.setFragmentResult(
            FAVORITES_FRAGMENT_RESULT_KEY,
            Bundle().apply {
                putString(FAVORITES_FRAGMENT_BUNDLE_KEY, result)
            }
        )
    }

    companion object {
        private const val LOGINS_KEY = "loginsSetKey"

        fun newInstance(loginsSet: Set<String>) = FavoritesBottomSheetFragment().apply {
            arguments = Bundle().apply {
                putStringArray(LOGINS_KEY, loginsSet.toTypedArray())
            }
        }
    }
}