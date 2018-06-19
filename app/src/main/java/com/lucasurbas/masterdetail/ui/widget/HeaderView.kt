package com.lucasurbas.masterdetail.ui.widget

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.lucasurbas.masterdetail.R
import kotlinx.android.synthetic.main.custom_headerview.view.*


class HeaderView : LinearLayout {

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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    fun setTitle(title: String?) {
        hideOrSetText(header_view_title, title)
    }

    fun setSubtitle(subTitle: String) {
        hideOrSetText(header_view_sub_title, subTitle)
    }

    private fun hideOrSetText(tv: TextView, text: String?) {
        if (text == null || text == "")
            tv.visibility = View.GONE
        else
            tv.text = text
    }
}