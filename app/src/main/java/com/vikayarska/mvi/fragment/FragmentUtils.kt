package com.vikayarska.mvi.fragment

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.vikayarska.mvi.R

fun Fragment.showError(message: String?, onCloseClick: (()-> Unit)? = null) {
    val alertDialogBuilder = AlertDialog.Builder(this.requireContext())

    alertDialogBuilder.apply {
        setTitle(R.string.error)
        setMessage(message ?: getString(R.string.unknown_error_message))
        setNegativeButton(R.string.ok) { dialog, _ ->
            onCloseClick?.let {
                it()
            }
            dialog.dismiss()
        }
    }.create().show()

}

/**
 * Show/Hide progress bar
 * @param visibility visibility which will be set to progress bar
 *
 * Firstly, progress bar is created programmatically and added to Fragment container
 * Then, progress bar reference is accessing by id
 */
fun Fragment.showLoading(visibility: Visibility) {
    val container = this.view as ViewGroup
    var progress: ProgressBar? = container.findViewById(R.id.id_progress_bar)
    if (progress == null) {
        progress = ProgressBar(this.requireContext()).apply {
            id = R.id.id_progress_bar
            indeterminateDrawable.setTint(
                ContextCompat.getColor(
                    this@showLoading.requireContext(),
                    R.color.teal_700
                )
            )
            scaleY = 0.2f
            scaleX = 0.2f
        }
        container.addView(progress, ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT))
    }
    progress.visibility = visibility.value
}

enum class Visibility(val value: Int) {
    Show(View.VISIBLE),
    Hide(View.GONE)
}