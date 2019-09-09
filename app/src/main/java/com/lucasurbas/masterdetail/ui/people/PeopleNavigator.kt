package com.lucasurbas.masterdetail.ui.people

import com.lucasurbas.masterdetail.data.Person
import com.lucasurbas.masterdetail.ui.main.MainContract

import javax.inject.Inject

/**
 * Created by Lucas on 17/01/2017.
 */

class PeopleNavigator @Inject
constructor(private val mainNavigator: MainContract.Navigator) : PeopleContract.Navigator {

    override fun goToPersonDetails(person: Person) {
        mainNavigator.goToPersonDetails(person)
    }
}
