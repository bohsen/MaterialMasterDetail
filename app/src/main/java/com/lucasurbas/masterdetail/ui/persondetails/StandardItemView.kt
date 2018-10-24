package com.lucasurbas.masterdetail.ui.persondetails

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.lucasurbas.masterdetail.R
import com.lucasurbas.masterdetail.data.Standard

/**
 * Created on 24/10/2018.
 */
class StandardItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    interface OnViewClickListener {
        fun onStandardItemClick(item: Standard)
        fun onStandardItemLongClick(item: Standard)
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.item_view_standard_listitem, this, true)
    }
}
