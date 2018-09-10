package com.lucasurbas.masterdetail.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.lucasurbas.masterdetail.R
import kotlinx.android.synthetic.main.custom_headerview.view.custom_headerview_study_priority


class HeaderView : ConstraintLayout {

    init {
        LayoutInflater.from(context).inflate(
            R.layout.custom_headerview,
            this,
            true)

        custom_headerview_study_priority.apply {
            val spinnerAdapter = ArrayAdapter.createFromResource(context, R.array.study_priority, R.layout.custom_headerview_spinner_item_layout)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
            adapter = spinnerAdapter
        }

        custom_headerview_study_priority.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

        }
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