package com.lucasurbas.masterdetail.ui.persondetails

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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lucasurbas.masterdetail.R
import com.lucasurbas.masterdetail.data.Person
import com.lucasurbas.masterdetail.data.Standard
import com.lucasurbas.masterdetail.ui.main.MainActivity
import com.lucasurbas.masterdetail.ui.widget.DataBindingAdapter
import kotlinx.android.synthetic.main.custom_inputview.view.*
import kotlinx.android.synthetic.main.fragment_person_details.*
import kotlinx.android.synthetic.main.fragment_person_details_content.*


/**
 * Created by Lucas on 02/01/2017.
 */

class PersonDetailsFragment : Fragment() {

    private var person: Person? = null

    private val model by lazy {
        ViewModelProviders.of(this).get(StandardsViewModel::class.java)
    }

    private val adapter = StandardsAdapter()

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
                // TODO: Change this to use a PopupWindow instead - this makes it possible to use adapters with a predifined layout
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
            setTouchable(true)
            setFocusable(true)
            setOutsideTouchable(true)
            setContentView(popupView)
            showAsDropDown(fragment_person_details__description)
        }
        val list = popupView
            .findViewById<RecyclerView>(R.id.standard_recyclerview_popup)
        list.apply {
            //            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = this@PersonDetailsFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }
        adapter.submitList(model.standards)
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

class StandardsViewModel: ViewModel() {
    val standards = listOf(Standard(0, 1244), Standard(1, 1455), Standard(2, 1566), Standard(3, 1155))
}

class StandardsAdapter : DataBindingAdapter<Standard>(DiffCallback()) {

    class DiffCallback : DiffUtil.ItemCallback<Standard>() {
        override fun areItemsTheSame(oldItem: Standard, newItem: Standard): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Standard, newItem: Standard): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int) = R.layout.item_view_standard_listitem
}

