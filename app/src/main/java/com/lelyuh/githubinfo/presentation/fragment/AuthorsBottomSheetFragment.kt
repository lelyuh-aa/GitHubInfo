package com.lelyuh.githubinfo.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lelyuh.githubinfo.databinding.AuthorsInfoFragmentBinding

/**
 * Bottom fragment with information about commits authors representing as list of [String] with format Name <Email>
 *
 * @author Leliukh Aleksandr
 */
internal class AuthorsBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: AuthorsInfoFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = AuthorsInfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(AUTHORS_INFO_KEY)?.let { authorsInfo ->
            binding.authorsTextView.text = authorsInfo
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val AUTHORS_INFO_KEY = "authorsInfoKey"

        fun newInstance(authorsInfo: String) = AuthorsBottomSheetFragment().apply {
            arguments = Bundle().apply {
                putString(AUTHORS_INFO_KEY, authorsInfo)
            }
        }
    }
}