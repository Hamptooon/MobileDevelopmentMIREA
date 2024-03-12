package ru.mirea.karacheviv.dialog

import android.R
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment


class AlertDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Здравствуй МИРЭА!")
            .setMessage("Успех близок?")
            .setIcon(R.mipmap.sym_def_app_icon)
            .setPositiveButton("Иду дальше", DialogInterface.OnClickListener { dialog, id ->
                (activity as MainActivity?)!!.onOkClicked()
                dialog.cancel()
            })
            .setNeutralButton("На паузе",
                DialogInterface.OnClickListener { dialog, id ->
                    (activity as MainActivity?)!!.onNeutralClicked()
                    dialog.cancel()
                })
            .setNegativeButton("Нет",
                DialogInterface.OnClickListener { dialog, id ->
                    (activity as MainActivity?)!!.onCancelClicked()
                    dialog.cancel()
                })
        return builder.create()
    }
}