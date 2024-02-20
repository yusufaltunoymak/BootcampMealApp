package com.example.bootcampmealapp.util.components

import android.content.Context
import com.example.bootcampmealapp.R
import com.example.bootcampmealapp.util.Errors
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CustomAlertDialogBuilder {
    companion object {
        var alertPositiveOnClickListener: () -> Unit = {}
        var alertNegativeOnClickListener: () -> Unit = {}

        fun alertPositiveOnClickListener(block: () -> Unit) {
            alertPositiveOnClickListener = block
        }
        fun createDialog(
            context: Context,
            message: String,
            positiveButtonText: String,
            positiveButtonClickListener: (() -> Unit)? = null
        ) {
            val builder = MaterialAlertDialogBuilder(context)
            builder.setMessage(message)
            builder.setPositiveButton(positiveButtonText) { _, _ ->
                positiveButtonClickListener?.invoke()
            }
            builder.show()
        }

        fun createDialog(
            context: Context,
            message: String,
            positiveButtonText: String,
            negativeButtonText : String,
            positiveButtonClickListener: (() -> Unit)?,
            negativeButtonClickListener: (() -> Unit)? = null
        ) {
            val builder = MaterialAlertDialogBuilder(context)
            builder.setMessage(message)
            builder.setPositiveButton(positiveButtonText) { _, _ ->
                positiveButtonClickListener?.invoke()
            }
            builder.setNegativeButton(negativeButtonText) { _, _ ->
                negativeButtonClickListener?.invoke()
            }
            builder.show()
        }

        fun createErrorDialog(errorMessage: String, context: Context,positiveButtonClickListener:(()->Unit)?= null) {
            when (errorMessage) {
                Errors.EMAIL_FORMAT_ERROR -> {
                    createDialog(
                        context = context,
                        message = context.getString(R.string.emailFormatErrorText),
                        positiveButtonText = context.getString(R.string.okText), positiveButtonClickListener = positiveButtonClickListener
                    )
                }

                Errors.PASSWORD_INVALID_ERROR -> {
                    createDialog(
                        context = context,
                        message = context.getString(R.string.passwordFormatErrorText),
                        positiveButtonText = context.getString(R.string.okText), positiveButtonClickListener = positiveButtonClickListener
                    )
                }

                else -> {
                    createDialog(
                        context = context,
                        message = errorMessage,
                        positiveButtonText = context.getString(R.string.okText), positiveButtonClickListener = positiveButtonClickListener
                    )
                }
            }
        }
    }
}