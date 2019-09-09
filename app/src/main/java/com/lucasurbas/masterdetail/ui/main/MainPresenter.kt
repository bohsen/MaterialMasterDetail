package com.lucasurbas.masterdetail.ui.main

import javax.inject.Inject

/**
 * Created by Lucas on 02/01/2017.
 */

class MainPresenter @Inject
constructor(private val navigator: MainContract.Navigator) : MainContract.Presenter {

    private var view: MainContract.View? = null

    override fun attachView(view: MainContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun clickHomeFeed() {
        if (view != null) {
            view!!.highlightHomeFeed()
            view!!.closeDrawer()
        }
        navigator.goToHomeFeed()
    }

    override fun clickPeople() {
        if (view != null) {
            view!!.highlightPeople()
            view!!.closeDrawer()
        }
        navigator.goToPeople()
    }

    override fun clickFavorites() {
        if (view != null) {
            view!!.highlightFavorites()
            view!!.closeDrawer()
        }
        navigator.goToFavorites()
    }

    override fun clickMap() {
        if (view != null) {
            view!!.highlightMap()
            view!!.closeDrawer()
        }
        navigator.goToMap()
    }

    override fun clickSettings() {
        if (view != null) {
            //            view.highlightSettings();
            view!!.closeDrawer()
        }
        navigator.goToSettings()
    }

    override fun clickFeedback() {
        if (view != null) {
            //            view.highlightHomeFeed();
            view!!.closeDrawer()
        }
        navigator.goToFeedback()
    }
}
