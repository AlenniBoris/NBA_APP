package com.alenniboris.nba_app.presentation.learning_recycler

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.res.getResourceIdOrThrow
import androidx.core.content.res.getStringOrThrow
import androidx.core.view.isVisible
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.databinding.MyAlertDialogBinding

class MyAlertDialog @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val binding = MyAlertDialogBinding
        .inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    private val xml = context.theme.obtainStyledAttributes(
        attrs,
        R.styleable.MyAlertDialog,
        R.attr.MyAlertDialogStyle,
        R.style.MyAlertDialogDefaultStyle
    )

    val headerTextView = binding.myDialogHeader
    val messageTextView = binding.myDialogMessageText
    val approveButton = binding.myDialogApproveButton
    val declineButton = binding.myDialogDeclineButton

    var header: String = xml.getStringOrThrow(R.styleable.MyAlertDialog__title_MyAlertDialog)
        .apply {
            header = this
        }
        set(value) {
            field = value
            headerTextView.text = value
        }

    var message: String =
        xml.getStringOrThrow(R.styleable.MyAlertDialog__message_string_MyAlertDialog)
            .apply {
                message = this
            }
        set(value) {
            field = value
            messageTextView.text = value
        }

    var background: Int =
        xml.getResourceIdOrThrow(R.styleable.MyAlertDialog__background_MyAlertDialog)
            .apply {
                background = this
            }
        set(backgroundResourceInt) {
            field = backgroundResourceInt
            binding.root.setBackgroundResource(backgroundResourceInt)
        }

    var approveButtonAction: () -> Unit = {
        binding.root.visibility = View.GONE
    }
        set(action) {
            field = action
            approveButton.setOnClickListener { action() }
        }

    var approveButtonTextResource: Int =
        xml.getResourceIdOrThrow(R.styleable.MyAlertDialog__approve_button_text_resource_MyAlertDialog)
            .apply {
                approveButtonTextResource = this
            }
        set(resource) {
            field = resource
            approveButton.text = context.getString(resource)
        }

    var declineButtonAction: () -> Unit = {
        binding.root.visibility = View.GONE
    }.apply { declineButton.setOnClickListener { this() } }
        set(action) {
            field = action
            declineButton.setOnClickListener { action() }
        }

    var declineButtonTextResource: Int =
        xml.getResourceIdOrThrow(R.styleable.MyAlertDialog__decline_button_text_resource_MyAlertDialog)
            .apply {
                declineButtonTextResource = this
            }
        set(resource) {
            field = resource
            declineButton.text = context.getString(resource)
        }

    init {
        xml.recycle()
    }

}