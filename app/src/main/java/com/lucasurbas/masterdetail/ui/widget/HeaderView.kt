package com.lucasurbas.masterdetail.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.lucasurbas.masterdetail.R


class HeaderView : ConstraintLayout {

    init {
        LayoutInflater.from(context).inflate(
            R.layout.custom_headerview,
            this,
            true)
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private fun hideOrSetText(tv: TextView, text: String?) {
        if (text == null || text == "")
            tv.visibility = View.GONE
        else
            tv.text = text
    }
}