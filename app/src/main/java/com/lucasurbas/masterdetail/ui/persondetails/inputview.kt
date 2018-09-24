package com.lucasurbas.masterdetail.ui.persondetails

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
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
                    textView.text = SpannableStringBuilder(getString(R.styleable.InputView_android_text) ?: "")
                    textInputLayout.hint = getText(R.styleable.InputView_android_hint)
                    if (getDrawable(R.styleable.InputView_android_src) == null) {
                        imageView.isVisible = false
                    } else {
                        imageView.setImageDrawable(getDrawable(R.styleable.InputView_android_src))
                        imageView.setColorFilter(getColorOrThrow(R.styleable.InputView_android_tint))
                    }
                    textView.maxLines = getInteger(R.styleable.InputView_android_maxLines, 1)
                    textView.minLines = getInteger(R.styleable.InputView_android_minLines, 0)
                    textView.setLines(getInteger(R.styleable.InputView_android_lines, 1))
                    textView.inputType = getInteger(R.styleable.InputView_android_inputType, EditorInfo.IME_NULL)
                    textView.setCompoundDrawablesWithIntrinsicBounds(
                        getDrawable(R.styleable.InputView_android_drawableStart),
                        getDrawable(R.styleable.InputView_android_drawableTop),
                        getDrawable(R.styleable.InputView_android_drawableEnd),
                        getDrawable(R.styleable.InputView_android_drawableBottom))
                } finally {
                    recycle()
                }
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    public override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        val ss = SavedState(superState)
        ss.childrenStates = SparseArray()
        for (i in 0 until childCount) {
            getChildAt(i).saveHierarchyState(ss.childrenStates as SparseArray<Parcelable>)
        }
        return ss
    }

    @Suppress("UNCHECKED_CAST")
    public override fun onRestoreInstanceState(state: Parcelable) {
        val ss = state as SavedState
        super.onRestoreInstanceState(ss.superState)
        for (i in 0 until childCount) {
            getChildAt(i).restoreHierarchyState(ss.childrenStates as SparseArray<Parcelable>)
        }
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
        dispatchFreezeSelfOnly(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        dispatchThawSelfOnly(container)
    }

    class SavedState(superState: Parcelable?) : BaseSavedState(superState) {
        var childrenStates: SparseArray<Any>? = null

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            childrenStates?.let {
                out.writeSparseArray(it)
            }
        }
    }
}