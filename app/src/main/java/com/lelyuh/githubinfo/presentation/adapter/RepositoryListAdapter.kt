package com.lelyuh.githubinfo.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lelyuh.githubinfo.R
import com.lelyuh.githubinfo.models.domain.RepositoryListModel

/**
 * Adapter for [RecyclerView] for representing repositories list
 *
 * @author Leliukh Aleksandr
 */
internal class RepositoryListAdapter(
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<RepositoryItemViewHolder>() {

    private var modelsList: List<RepositoryListModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repo_list_item, parent, false)
        return RepositoryItemViewHolder(listener, view)
    }

    override fun onBindViewHolder(holder: RepositoryItemViewHolder, position: Int) {
        holder.bind(modelsList[position])
    }

    override fun getItemCount() = modelsList.size

    /**
     * Set new list of loaded repositories by [newList]
     */
    @SuppressLint("NotifyDataSetChanged")
    fun setListData(newList: List<RepositoryListModel>) {
        modelsList = ArrayList(newList)
        notifyDataSetChanged()
    }

    /**
     * Callback to handle clicks on list items
     */
    interface OnItemClickListener {

        /**
         * Click on item happens
         *
         * @param model current model associated with item that was clicked
         */
        fun onItemClick(model: RepositoryListModel)
    }
}