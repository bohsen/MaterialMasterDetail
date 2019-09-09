package com.lucasurbas.masterdetail.ui.main


import com.lucasurbas.masterdetail.data.Person
import com.lucasurbas.masterdetail.ui.util.BaseNavigator
import com.lucasurbas.masterdetail.ui.util.BasePresenter
import com.lucasurbas.masterdetail.ui.util.BaseView

/**
 * Created by Lucas on 12/06/16.
 */
interface MainContract {

    interface Navigator : BaseNavigator {

        fun goToHomeFeed()

        fun goToPeople()

        fun goToPersonDetails(person: Person)

        fun goToFavorites()

        fun goToMap()

        fun goToSettings()

        fun goToFeedback()

        fun onBackPressed(): Boolean
    }

    interface View : BaseView {

        fun closeDrawer()

        fun openDrawer()

        fun highlightHomeFeed()

        fun highlightPeople()

        fun highlightFavorites()

        fun highlightMap()

        fun highlightSettings()

        fun highlightFeedback()

    }

    interface Presenter : BasePresenter<View> {

        fun clickHomeFeed()

        fun clickPeople()

        fun clickFavorites()

        fun clickMap()

        fun clickSettings()

        fun clickFeedback()
    }
}
