package com.lucasurbas.masterdetail.ui.people

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.AttrRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.lucasurbas.masterdetail.R
import com.lucasurbas.masterdetail.data.Person
import com.lucasurbas.masterdetail.ui.widget.FlipAnimator
import kotlinx.android.synthetic.main.item_view_user.view.item_view_user__row
import kotlinx.android.synthetic.main.item_view_user_internal.view.item_view_user__action
import kotlinx.android.synthetic.main.item_view_user_internal.view.item_view_user__avatar
import kotlinx.android.synthetic.main.item_view_user_internal.view.item_view_user__description
import kotlinx.android.synthetic.main.item_view_user_internal.view.item_view_user__name


class PersonView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, @AttrRes defStyleAttr: Int = 0)
    : ConstraintLayout(context, attrs, defStyleAttr) {

    private var person: Person? = null
    private var onPersonClickListener: OnPersonClickListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.item_view_user_internal, this, true) as PersonView
        isSelected = false
        item_view_user__row.setOnClickListener { onRowClickAction() }
        item_view_user__action.setOnClickListener { onStarClickAction() }
        item_view_user__avatar.setOnClickListener { onDrawableClickAction() }
    }

    fun setUser(person: Person) {
        this.person = person
        item_view_user__name.text = person.name
        item_view_user__description.text = person.description
    }

    fun setOnPersonClickListener(onPersonClickListener: OnPersonClickListener?) {
        this.onPersonClickListener = onPersonClickListener
    }

    private fun onRowClickAction() {
        onPersonClickListener?.onPersonClick(person)
    }

    private fun onStarClickAction() {
        onPersonClickListener?.onPersonActionClick(person)
    }

    private fun onDrawableClickAction() {
        if (isSelected) {
            FlipAnimator.flipOut(context, item_view_user__avatar)
            onPersonClickListener?.onPersonUnselected(person)
            item_view_user__row.isSelected = false
        } else {
            FlipAnimator.flipIn(context, item_view_user__avatar)
            onPersonClickListener?.onPersonSelected(person)
            item_view_user__row.isSelected = true
        }
    }

    fun clearSelection() {
        FlipAnimator.flipOut(context, item_view_user__avatar)
        item_view_user__row.isSelected = false
    }

    interface OnPersonClickListener {
        fun onPersonClick(person: Person?)
        fun onPersonActionClick(person: Person?)
        fun onPersonSelected(person: Person?)
        fun onPersonUnselected(person: Person?)
    }
}