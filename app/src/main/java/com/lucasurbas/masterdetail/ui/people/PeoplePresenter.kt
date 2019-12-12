package com.lucasurbas.masterdetail.ui.people

import android.content.Context
import android.os.Handler
import android.util.Log
import android.util.SparseBooleanArray
import com.lucasurbas.masterdetail.R
import com.lucasurbas.masterdetail.data.Person
import java.util.ArrayList
import java.util.Random
import javax.inject.Inject

/**
 * Created by Lucas on 04/01/2017.
 */
class PeoplePresenter @Inject
constructor(private val context: Context, private val navigator: PeopleContract.Navigator) :
    PeopleContract.Presenter {
    private var view: PeopleContract.View? = null

    private val peopleList: MutableList<Person> = ArrayList()
    private val selectedPeopleList = ArrayList<Person>()
    val selectedItems = SparseBooleanArray()

    private val names = mutableListOf<String>()
    private val ids = mutableListOf<String>()

    private val randomName: String
        get() {
            val r = Random()
            return names[r.nextInt(names.size)]
        }

    private val randomId: String
        get() {
            val r = Random()
            return ids[r.nextInt(ids.size)]
        }

    init {
        names.add("Nolan Mcfetridge")
        names.add("Nick Blackford")
        names.add("Carlee Mucci")
        names.add("Tianna Henricksen")
        names.add("Julie Rathburn")
        names.add("Silvana Stiner")
        names.add("Rudolf Grate")
        names.add("Saran Seaman")
        names.add("Carol Pavao")
        names.add("Karey Shatley")
        names.add("Carlita Frye")
        names.add("Sharita Ekberg")
        names.add("Elvia Huitt")
        names.add("Kesha Liebel")
        names.add("Aleida Vincelette")
        names.add("Stormy Rossiter")
        names.add("Carolina Degner")
        names.add("Ruth Slavin")
        names.add("Delilah Hermosillo")
        names.add("Willow Haley")

        val array = arrayOf("010101-0tt1",
            "020202-0TT2",
            "030303-0TT3",
            "040404-0TT4",
            "050505-0TT5",
            "060606-0TT6",
            "070707-0TT7",
            "080808-0TT8",
            "090909-0TT9",
            "101010-0TT1",
            "111111-1TT1",
            "121212-1TT2",
            "131313-1TT3",
            "141414-1TT4",
            "151515-1TT5",
            "161616-1TT6",
            "171717-1TT7",
            "181818-1TT8",
            "191919-1TT9",
            "202020-2TT0")
        ids.addAll(array)
    }

    override fun attachView(view: PeopleContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun getPeople() {
        for (i in 0..7) {
            val person = Person(
                randomId,
                randomName,
                context.getString(R.string.fragment_people__lorem_ipsum)
            )
            peopleList.add(person)
        }
        if (view != null) {
            view?.showPeopleList(peopleList)
        }
    }

    override fun loadPersonDetails(person: Person) {
        if (selectedPeopleList.size == 0) {
            navigator.goToPersonDetails(person)
        }
    }

    override fun clickPersonAction(person: Person) {
        view!!.showToast("Action clicked: " + person.name)
    }

    override fun loadMorePeople() {
        val handler = Handler()
        handler.postDelayed({
            if (view != null) {
                view!!.hideLoading()
                val person = Person(
                    randomId,
                    randomName,
                    context.getString(R.string.fragment_people__lorem_ipsum)
                )
                peopleList.add(0, person)
                view?.showPeopleList(peopleList)
            }
        }, 2000)
    }

    override fun select(person: Person) {
        view!!.showToast(
            "DrawableAction selected: " + person.name +
                    " at index: " + peopleList.indexOf(person)
        )
        if (selectedPeopleList.size == 0) {
            view!!.startActionMode()
        }
        selectedPeopleList.add(person)
        selectedItems.put(peopleList.indexOf(person), true)
        view!!.updateActionModeCount(selectedPeopleList.size)
    }

    override fun unselect(person: Person) {
        view!!.showToast(
            "DrawableAction unselected: " + person.name +
                    " at index: " + peopleList.indexOf(person)
        )
        selectedPeopleList.remove(person)
        selectedItems.delete(peopleList.indexOf(person))
        if (selectedPeopleList.size == 0) {
            view!!.stopActionMode()
        } else {
            view!!.updateActionModeCount(selectedPeopleList.size)
        }
    }

    override fun clearSelected() {
        Log.d("PeoplePresenter", "#clearSelected called...")
        if (selectedPeopleList.size > 0) {
            view!!.reverseAllAnimations(selectedItems)
        }
        selectedPeopleList.clear()
        selectedItems.clear()
    }
}
