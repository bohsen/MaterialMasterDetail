package com.lucasurbas.masterdetail.ui.persondetails

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.getColorOrThrow
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.lucasurbas.masterdetail.R
import kotlinx.android.synthetic.main.custom_inputview.view.custom_inputview_image_view
import kotlinx.android.synthetic.main.custom_inputview.view.custom_inputview_text_input_edit_text
import kotlinx.android.synthetic.main.custom_inputview.view.custom_inputview_text_input_layout


class InputView(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var textInputLayout: TextInputLayout
    private var textView: TextInputEditText
    private var imageView: ImageView

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_inputview, this, true)

        textInputLayout = view.custom_inputview_text_input_layout
        textView = view.custom_inputview_text_input_edit_text
        imageView = view.custom_inputview_image_view

        attrs.let {
            context.theme.obtainStyledAttributes(
                it,
                R.styleable.InputView,
                defStyleAttr, 0).apply {

                try {
                    textView.textSize = getDimension(R.styleable.InputView_android_textSize, 16.0f)
                    textView.setText(getText(R.styleable.InputView_android_text))
                    textInputLayout.hint = getText(R.styleable.InputView_android_hint)
                    if (getDrawable(R.styleable.InputView_android_src) == null) {
                        Log.d("InputView", "Please remove me")
                        imageView.isVisible = false
                    } else {
                        imageView.setImageDrawable(getDrawable(R.styleable.InputView_android_src))
                        imageView.setColorFilter(getColorOrThrow(R.styleable.InputView_android_tint))
                    }
                    textView.maxLines = getInteger(R.styleable.InputView_android_maxLines, Int.MAX_VALUE)
                    textView.minLines = getInteger(R.styleable.InputView_android_minLines, 0)
                    textView.setLines(getInteger(R.styleable.InputView_android_lines, Int.MAX_VALUE))
                } finally {
                    recycle()
                }
            }
        }
    }
}