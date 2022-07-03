package com.lelyuh.githubinfo.presentation.view

import android.content.Context
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.lelyuh.githubinfo.R
import com.lelyuh.githubinfo.models.presentation.CommitsMonthsModel

/**
 * Custom view for representing information about commits in given repository per every month
 * with animate indication of relative value commits number of current month to max commits number for all months
 * Contains:
 * - info about commits number in current month
 * - name of current month and year
 * - bar view with changeable height according to ratio between number of current month commits and max commits number for all months
 *
 * @author Leliukh Aleksandr
 */
internal class CommitsInfoView : ConstraintLayout {

    private lateinit var progressView: View
    private lateinit var commitsCountTextView: TextView
    private lateinit var periodTextView: TextView
    private lateinit var barView: View
    private lateinit var commitsGroupView: View

    private val maxBarHeight = resources.getDimensionPixelSize(R.dimen.commit_bar_max_height)

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView(context)
    }

    private fun initView(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.commits_info_view, this)
        progressView = findViewById(R.id.progress)
        commitsCountTextView = findViewById(R.id.commits_count_text_view)
        periodTextView = findViewById(R.id.period_text_view)
        barView = findViewById(R.id.bar_view)
        commitsGroupView = findViewById(R.id.commits_info_group)
    }

    /**
     * Set information about commits in current month representing with [commitsModel]
     */
    fun setData(commitsModel: CommitsMonthsModel) {
        TransitionManager.beginDelayedTransition(this)
        commitsCountTextView.text = context.getString(R.string.commits_months_count_format, commitsModel.count)
        periodTextView.text = commitsModel.period
        barView.layoutParams.height = (maxBarHeight * commitsModel.countRatio).toInt()
        if (progressView.isVisible) {
            progressView.isVisible = false
            commitsGroupView.isVisible = true
        }
    }
}