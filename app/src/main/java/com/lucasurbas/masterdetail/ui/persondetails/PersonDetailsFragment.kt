package com.lucasurbas.masterdetail.ui.persondetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.lucasurbas.masterdetail.R
import com.lucasurbas.masterdetail.data.Person
import com.lucasurbas.masterdetail.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_person_details.*

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

        if (!(activity as MainActivity).containersLayout.hasTwoColumns()) {
            home.visibility = View.VISIBLE
            home.setOnClickListener { _ -> activity!!.onBackPressed() }
        } else {
            home.visibility = View.INVISIBLE
            home.setOnClickListener(null)
        }

        custom_headerview_study_priority.apply {
            val spinnerAdapter = ArrayAdapter.createFromResource(context, R.array.study_priority, R.layout.study_priority_spinner_item_layout)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
            adapter = spinnerAdapter
        }

        this.person = arguments!!.getParcelable(KEY_PERSON)
        setPerson(person!!)
    }

    private fun setPerson(person: Person) {
        custom_headerview_patient_id.text = person.id
        custom_headerview_patient_name.text = person.name
//        custom_headerview_patient_height.setText(person.height?.toString() ?: "")
//        custom_headerview_patient_weight.setText(person.weight?.toString() ?: "")
        custom_headerview_study_priority.adapter.getItem(person.priority.ordinal)
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
