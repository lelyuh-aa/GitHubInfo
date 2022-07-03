package com.lelyuh.githubinfo.presentation.error

import android.app.Activity
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.lelyuh.githubinfo.R

/**
 * Helper to show information about any errors on loading data
 *
 * @author Leliukh Aleksandr
 */
object ErrorHelper {

    /**
     * Show information about error in AlertDialog
     *
     * @param activity      current activity for futher actions
     * @param messageResId  main message in alert
     */
    fun showErrorDialog(activity: Activity, @StringRes messageResId: Int = R.string.error_message) {
        AlertDialog.Builder(activity)
            .setTitle(R.string.error_title)
            .setMessage(messageResId)
            .setPositiveButton(R.string.error_ok_button) { _, _ ->
                activity.finish()
            }
            .setCancelable(false)
            .show()
    }
}