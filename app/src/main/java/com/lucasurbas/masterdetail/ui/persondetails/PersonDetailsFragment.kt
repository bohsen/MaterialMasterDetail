package com.lucasurbas.masterdetail.ui.persondetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.lucasurbas.masterdetail.R
import com.lucasurbas.masterdetail.data.Person
import com.lucasurbas.masterdetail.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_person_details.fragment_person_details__toolbar
import kotlinx.android.synthetic.main.fragment_person_details.fragment_person_details_appBar
import kotlinx.android.synthetic.main.fragment_person_details_content.fragment_person_details__description

/**
 * Created by Lucas on 02/01/2017.
 */

class PersonDetailsFragment : Fragment() {

    private var person: Person? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_person_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.person = arguments!!.getParcelable(KEY_PERSON)

        setupToolbar()
        setPerson(person!!)
    }

    private fun setupToolbar() {
        fragment_person_details__toolbar.inflateMenu(R.menu.person_details)

        if (!(activity as MainActivity).containersLayout.hasTwoColumns()) {
            fragment_person_details__toolbar.setNavigationIcon(R.drawable.ic_back_24dp)
            fragment_person_details__toolbar.setNavigationOnClickListener { _ -> activity!!.onBackPressed() }
        }

        // This prevents users from being able to expand/collapse AppBarLayout by flicking the AppbarLayout
        // This way the AppBarLayout will always stay expanded, when the content below doesn't scroll
        val params = fragment_person_details_appBar.layoutParams as CoordinatorLayout.LayoutParams
        if (params.behavior == null)
            params.behavior = AppBarLayout.Behavior()
        val behaviour = params.behavior as AppBarLayout.Behavior
        behaviour.setDragCallback(object : AppBarLayout.Behavior.DragCallback() {
            override fun canDrag(appBarLayout: AppBarLayout): Boolean {
                return false
            }
        })
    }

    private fun setPerson(person: Person) {
//        fragment_person_details__toolbar.title = person.name
//        fragment_person_details__toolbar.subtitle = person.name
        fragment_person_details__description.text = person.description
    }

    companion object {

        private val KEY_PERSON = "key_person"

        fun newInstance(person: Person): PersonDetailsFragment {
            val fragment = PersonDetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable(KEY_PERSON, person)
            fragment.arguments = bundle
            return fragment
        }
    }
}
