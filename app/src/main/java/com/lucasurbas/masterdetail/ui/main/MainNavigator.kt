package com.lucasurbas.masterdetail.ui.main

import androidx.fragment.app.FragmentTransaction
import com.lucasurbas.masterdetail.R
import com.lucasurbas.masterdetail.data.Person
import com.lucasurbas.masterdetail.ui.favorites.FavoritesFragment
import com.lucasurbas.masterdetail.ui.homefeed.HomeFeedFragment
import com.lucasurbas.masterdetail.ui.map.MapFragment
import com.lucasurbas.masterdetail.ui.people.PeopleFragment
import com.lucasurbas.masterdetail.ui.persondetails.PersonDetailsFragment
import javax.inject.Inject

class MainNavigator @Inject
constructor(private val mainActivity: MainActivity) : MainContract.Navigator {

    enum class State {
        SINGLE_COLUMN_MASTER, SINGLE_COLUMN_DETAILS, TWO_COLUMNS_EMPTY, TWO_COLUMNS_WITH_DETAILS
    }

    override fun goToHomeFeed() {
        clearDetails()
        mainActivity.getCustomAppBar().setState(State.SINGLE_COLUMN_MASTER)
        mainActivity.getContainersLayout().state = State.SINGLE_COLUMN_MASTER
        val fragment = HomeFeedFragment.newInstance()
        mainActivity.supportFragmentManager.beginTransaction().replace(R.id.activity_main__frame_master, fragment, TAG_MASTER).commitNow()
    }

    override fun goToPeople() {
        clearDetails()
        mainActivity.getCustomAppBar().setState(State.TWO_COLUMNS_EMPTY)
        mainActivity.getContainersLayout().state = State.TWO_COLUMNS_EMPTY
        val master = PeopleFragment.newInstance()
        mainActivity.supportFragmentManager.beginTransaction().replace(R.id.activity_main__frame_master, master, TAG_MASTER).commitNow()
    }

    override fun goToFavorites() {
        clearDetails()
        mainActivity.getCustomAppBar().setState(State.SINGLE_COLUMN_MASTER)
        mainActivity.getContainersLayout().state = State.SINGLE_COLUMN_MASTER
        val fragment = FavoritesFragment.newInstance()
        mainActivity.supportFragmentManager.beginTransaction().replace(R.id.activity_main__frame_master, fragment, TAG_MASTER).commitNow()
    }

    override fun goToMap() {
        clearMaster()
        mainActivity.getCustomAppBar().setState(State.SINGLE_COLUMN_DETAILS)
        mainActivity.getContainersLayout().state = State.SINGLE_COLUMN_DETAILS
        val fragment = MapFragment.newInstance()
        mainActivity.supportFragmentManager.beginTransaction().replace(R.id.activity_main__frame_details, fragment, TAG_DETAILS).commitNow()
    }

    override fun goToPersonDetails(person: Person) {
        mainActivity.getCustomAppBar().setState(State.TWO_COLUMNS_WITH_DETAILS)
        mainActivity.getContainersLayout().state = State.TWO_COLUMNS_WITH_DETAILS
        val fragment = PersonDetailsFragment.newInstance(person)
        mainActivity.supportFragmentManager
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .replace(R.id.activity_main__frame_details, fragment, TAG_DETAILS)
            .commitNow()
    }

    override fun goToSettings() {
        //start new activity
    }

    override fun goToFeedback() {
        //start new activity
    }

    override fun onBackPressed(): Boolean {
        val state = mainActivity.getContainersLayout().state
        if (state == State.TWO_COLUMNS_WITH_DETAILS && !mainActivity.getContainersLayout().hasTwoColumns()) {
            if (clearDetails()) {
                mainActivity.getContainersLayout().state = State.TWO_COLUMNS_EMPTY
                return true
            }
        }
        return false
    }

    private fun clearDetails(): Boolean {
        val details = mainActivity.supportFragmentManager.findFragmentByTag(TAG_DETAILS)
        if (details != null) {
            mainActivity.supportFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .remove(details)
                .commitNow()
            return true
        }
        return false
    }

    private fun clearMaster() {
        val master = mainActivity.supportFragmentManager.findFragmentByTag(TAG_MASTER)
        if (master != null) {
            mainActivity.supportFragmentManager.beginTransaction().remove(master).commitNow()
        }
    }

    companion object {

        private const val TAG_DETAILS = "tag_details"
        private const val TAG_MASTER = "tag_master"
    }
}
