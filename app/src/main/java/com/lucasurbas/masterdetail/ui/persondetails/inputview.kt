package com.lucasurbas.masterdetail.ui.persondetails

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.getColorOrThrow
import androidx.core.graphics.toRectF
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

        textView.onFocusChangeListener = object : OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (!hasFocus) textView.hideKeyboard()
            }
        }

        attrs.let {
            context.theme.obtainStyledAttributes(
                it,
                R.styleable.InputView,
                defStyleAttr, 0).apply {

                try {
                    textView.textSize = getDimension(R.styleable.InputView_android_textSize, 16.0f)
//                    textView.setTextColor(getColor(R.styleable.InputView_android_textColor, android.R.attr.textColor))
                    textView.setText(getText(R.styleable.InputView_android_text))
                    textInputLayout.hint = getText(R.styleable.InputView_android_hint)
                    imageView.setImageDrawable(getDrawable(R.styleable.InputView_android_src))
                    imageView.setColorFilter(getColorOrThrow(R.styleable.InputView_android_tint))
                    textView.maxLines = getInteger(R.styleable.InputView_android_maxLines, Int.MAX_VALUE)
                    textView.minLines = getInteger(R.styleable.InputView_android_minLines, 0)
                    textView.setLines(getInteger(R.styleable.InputView_android_lines, Int.MAX_VALUE))
                } finally {
                    recycle()
                }
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev!!.action == MotionEvent.ACTION_UP) Log.d("InputView", "User clicked: X : ${ev.rawX} Y : ${ev.rawY}")

        val textViewrect = Rect()
        textView.getGlobalVisibleRect(textViewrect)
        val fRect = textViewrect.toRectF()

        if (ev.action == MotionEvent.ACTION_UP) {
            if (fRect.contains(ev.rawX, ev.rawY)) {
                Log.d("InputView", "ACTION_UP event happened in X : ${ev.rawX} Y : ${ev.rawY}")
            }
            if (!fRect.contains(ev.rawX, ev.rawY)) {
                Log.d("InputView", "ACTION_UP event happened outside InputView in X : ${ev.rawX} Y : ${ev.rawY}")
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}