package com.lucasurbas.masterdetail.ui.widget

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

//    fun setTitle(title: String?) {
//        hideOrSetText(hea, title)
//    }
//
//    fun setSubtitle(subTitle: String) {
//        hideOrSetText(header_view_sub_title, subTitle)
//    }

    private fun hideOrSetText(tv: TextView, text: String?) {
        if (text == null || text == "")
            tv.visibility = View.GONE
        else
            tv.text = text
    }
}

class ViewBehavior(private val mContext: Context, attrs: AttributeSet) :
    CoordinatorLayout.Behavior<HeaderView>() {

    private var mStartMarginLeft: Int = 0
    private var mEndMarginLeft: Int = 0
    private var mMarginRight: Int = 0
    private var mStartMarginBottom: Int = 0
    private var isHide: Boolean = false

    private var initialized = false


    val toolbarHeight: Int
        get() {
            var result = 0
            val tv = TypedValue()
            if (mContext.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                result = TypedValue.complexToDimensionPixelSize(
                    tv.data,
                    mContext.resources.displayMetrics
                )
            }
            return result
        }

    override fun layoutDependsOn(
        parent: CoordinatorLayout?,
        child: HeaderView?,
        dependency: View?
    ): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout?,
        child: HeaderView?,
        dependency: View?
    ): Boolean {

        if (parent == null || child == null || dependency == null) {
            return false
        }
        shouldInitProperties(child, dependency)
        val maxScroll = (dependency as AppBarLayout).totalScrollRange
        val percentage = Math.abs(dependency.y) / maxScroll.toFloat()

        var childPosition = (dependency.height + dependency.y
                - child.height.toFloat()
                - (toolbarHeight - child.height) * percentage / 2)


        childPosition -= mStartMarginBottom * (1f - percentage)

        val lp = child.layoutParams as CoordinatorLayout.LayoutParams
        lp.leftMargin = (percentage * mEndMarginLeft).toInt() + mStartMarginLeft
        lp.rightMargin = mMarginRight
        child.layoutParams = lp

        child.y = childPosition

        if (isHide && percentage < 1) {
            child.apply {
                visibility = View.VISIBLE
                isHide = false
            }
        } else if (!isHide && percentage == 1f) {
            child.apply {
                visibility = View.GONE
                isHide = true
            }
        }

        return true
    }

    private fun shouldInitProperties(child: HeaderView, dependency: View) {

        if (!initialized) {
            val params = child.layoutParams as ViewGroup.MarginLayoutParams

            mStartMarginLeft = params.leftMargin
            mEndMarginLeft = params.marginEnd
            mStartMarginBottom = params.bottomMargin
            mMarginRight = params.rightMargin
            initialized = true
        }
    }
}