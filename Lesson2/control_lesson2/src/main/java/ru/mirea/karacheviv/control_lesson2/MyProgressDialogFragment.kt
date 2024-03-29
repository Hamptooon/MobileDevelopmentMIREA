package ru.mirea.karacheviv.control_lesson2

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class MyProgressDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Подождите...")
        progressDialog.isIndeterminate = true
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)

        return progressDialog
    }
}
