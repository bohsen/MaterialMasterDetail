package com.lucasurbas.masterdetail.ui.people

import android.util.SparseBooleanArray

import com.lucasurbas.masterdetail.data.Person
import com.lucasurbas.masterdetail.ui.util.BaseNavigator
import com.lucasurbas.masterdetail.ui.util.BasePresenter
import com.lucasurbas.masterdetail.ui.util.BaseView

interface PeopleContract {

    interface Navigator : BaseNavigator {
        fun goToPersonDetails(person: Person)
    }

    interface View : BaseView {
        fun showLoading()
        fun hideLoading()
        fun showPeopleList(peopleList: List<Person>)
        fun showToast(message: String)
        fun startActionMode()
        fun stopActionMode()
        fun updateActionModeCount(count: Int)
        fun reverseAllAnimations(selectedItems: SparseBooleanArray)
    }

    interface Presenter : BasePresenter<View> {
        fun getPeople()
        fun loadPersonDetails(person: Person)
        fun clickPersonAction(person: Person)
        fun loadMorePeople()
        fun select(person: Person)
        fun unselect(person: Person)
        fun clearSelected()
    }
}
