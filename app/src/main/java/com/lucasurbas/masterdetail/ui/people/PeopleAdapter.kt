package com.lucasurbas.masterdetail.ui.people

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lucasurbas.masterdetail.R
import com.lucasurbas.masterdetail.data.Person
import java.util.ArrayList


class PeopleAdapter : RecyclerView.Adapter<PeopleAdapter.PersonViewHolder>() {

    private val mPeopleList: MutableList<Person>
    private var mOnPersonClickListener: PersonView.OnPersonClickListener? = null

    class PersonViewHolder(var personView: PersonView) : RecyclerView.ViewHolder(personView)

    init {
        this.mPeopleList = ArrayList()
    }

    fun setOnPersonClickListener(onPersonClickListener: PersonView.OnPersonClickListener?) {
        this.mOnPersonClickListener = onPersonClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_user, parent, false) as PersonView
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.personView.setUser(mPeopleList[position])
        holder.personView.setOnPersonClickListener(mOnPersonClickListener)
    }

    override fun getItemCount(): Int {
        return mPeopleList.size
    }

    fun setPeopleList(peopleList: List<Person>) {
        val diffResult = DiffUtil.calculateDiff(PeopleListDiffCallback(this.mPeopleList, peopleList))
        this.mPeopleList.clear()
        this.mPeopleList.addAll(peopleList)
        diffResult.dispatchUpdatesTo(this)
    }

}
