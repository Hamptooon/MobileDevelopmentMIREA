package ru.mirea.karacheviv.control_lesson2

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class MyProgressDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Подождите...") // Установите текст сообщения
        progressDialog.isIndeterminate = true // Установите, чтобы индикатор был неопределенным (бесконечным)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER) // Установите стиль индикатора (кружок)

        return progressDialog
    }
}