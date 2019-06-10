package edu.washington.info448.hatemail

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log

class ConfirmDialog : DialogFragment() {
    internal lateinit var listener: ConfirmDialogListener
    interface ConfirmDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as ConfirmDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ConfirmDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreate(savedInstanceState)
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Are you sure you want to delete this scheduled message?")
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        Log.i("ConfirmDialog", "Deletion canceled")
                    })
                .setPositiveButton("OK",
                    DialogInterface.OnClickListener { dialog, id ->
                        listener.onDialogPositiveClick(this)
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}