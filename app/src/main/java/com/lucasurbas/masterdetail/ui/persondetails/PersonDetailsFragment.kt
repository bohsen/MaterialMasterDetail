package com.lucasurbas.masterdetail.ui.persondetails

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.PopupWindow
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.lucasurbas.masterdetail.R
import com.lucasurbas.masterdetail.data.Person
import com.lucasurbas.masterdetail.ui.main.MainActivity
import kotlinx.android.synthetic.main.custom_inputview.view.custom_inputview_text_input_edit_text
import kotlinx.android.synthetic.main.fragment_person_details.fragment_person_details_patient_id
import kotlinx.android.synthetic.main.fragment_person_details.fragment_person_details_patient_name
import kotlinx.android.synthetic.main.fragment_person_details.fragment_person_details_study_priority
import kotlinx.android.synthetic.main.fragment_person_details.home
import kotlinx.android.synthetic.main.fragment_person_details_content.fragment_person_details__description
import kotlinx.android.synthetic.main.fragment_person_details_content.fragment_person_details_standard


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

        fragment_person_details_study_priority.apply {
            val spinnerAdapter = ArrayAdapter.createFromResource(context, R.array.study_priority, R.layout.study_priority_spinner_item_layout)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
            adapter = spinnerAdapter
        }

        fragment_person_details_standard.apply {
            custom_inputview_text_input_edit_text.setOnClickListener {
                // TODO: Change this to use a ListPopupWindow instead - this makes it possible to use adapters with a predifined layout
                // Check this SO question: https://stackoverflow.com/questions/34030720/listpopupwindow-doesnt-display-correctly-in-textinputlayout
                val popup = PopupMenu(context, it)
                val inflater: MenuInflater = popup.menuInflater
                inflater.inflate(R.menu.standard_test, popup.menu)
                popup.setOnMenuItemClickListener { item ->
                    fragment_person_details_standard.custom_inputview_text_input_edit_text.text = SpannableStringBuilder(item.toString())
                    true
                }
                popup.show()
            }
        }

        fragment_person_details__description.apply {
            setOnClickListener {
                showPopupWindow()
            }
        }

        this.person = arguments!!.getParcelable(KEY_PERSON)
        setPerson(person!!)
    }

    private fun showPopupWindow() {
        val popupView = layoutInflater.inflate(R.layout.standard_popup_window, null)
        PopupWindow(context).apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            isTouchable = true
            isFocusable = true
            isOutsideTouchable = true
            overlapAnchor = true
            contentView = popupView
            showAsDropDown(fragment_person_details__description)
        }
//        ListPopupWindow(requireContext()).apply {
//            anchorView = fragment_person_details__description
//            setPromptView(popupView)
//        }.show()
    }

    private fun setPerson(person: Person) {
        fragment_person_details_patient_id.text = person.id
        fragment_person_details_patient_name.text = person.name
//        custom_headerview_patient_height.setText(person.height?.toString() ?: "")
//        custom_headerview_patient_weight.setText(person.weight?.toString() ?: "")
        fragment_person_details_study_priority.adapter.getItem(person.priority.ordinal)
    }

    companion object {

        private const val KEY_PERSON = "key_person"

        fun newInstance(person: Person): PersonDetailsFragment {
            val fragment = PersonDetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable(KEY_PERSON, person)
            fragment.arguments = bundle
            return fragment
        }
    }
}
