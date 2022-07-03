package com.lelyuh.githubinfo.presentation.adapter

import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.lelyuh.githubinfo.R
import com.lelyuh.githubinfo.models.domain.RepositoryListModel

/**
 * View Holder for item view in [RecyclerView] for representing repository info
 */
internal class RepositoryItemViewHolder(
    private val listener: RepositoryListAdapter.OnItemClickListener,
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    /**
     * Bind data about given repository by it [repositoryModel]
     */
    fun bind(repositoryModel: RepositoryListModel) {
        with(itemView) {
            findViewById<TextView>(R.id.name_text_view).text = repositoryModel.name
            with(findViewById<TextView>(R.id.description_text_view)) {
                if (repositoryModel.description.isNullOrBlank()) {
                    isVisible = false
                } else {
                    text = repositoryModel.description
                    isVisible = true
                }
            }
            setOnClickListener {
                listener.onItemClick(repositoryModel)
            }
        }
    }
}
