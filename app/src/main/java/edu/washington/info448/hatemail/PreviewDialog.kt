package edu.washington.info448.hatemail

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.util.Log

class PreviewDialog : DialogFragment() {
    private var message: String? = null
    internal lateinit var listener: PreviewDialogListener
    interface PreviewDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as PreviewDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(message: String) =
            PreviewDialog().apply {
                arguments = Bundle().apply {
                    putString("message", message)
                }
            }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreate(savedInstanceState)
        arguments?.let {
            message = it.getString("message")
        }

        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Message preview")
                .setMessage(message)
                .setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        Log.i("PreviewDialog", "Schedule canceled")
                    })
                .setPositiveButton("Schedule",
                    DialogInterface.OnClickListener { dialog, id ->
                        listener.onDialogPositiveClick(this)
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}