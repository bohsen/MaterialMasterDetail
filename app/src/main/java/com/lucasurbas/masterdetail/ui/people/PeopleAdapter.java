package com.lucasurbas.masterdetail.ui.people;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lucasurbas.masterdetail.R;
import com.lucasurbas.masterdetail.data.Person;

import java.util.ArrayList;
import java.util.List;


public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PersonViewHolder> {

    private List<Person> mPeopleList;
    private PersonView.OnPersonClickListener mOnPersonClickListener;

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        public PersonView personView;

        public PersonViewHolder(PersonView personView) {
            super(personView);
            this.personView = personView;
        }
    }

    public PeopleAdapter() {
        this.mPeopleList = new ArrayList<>();
    }

    public void setOnPersonClickListener(PersonView.OnPersonClickListener onPersonClickListener) {
        this.mOnPersonClickListener = onPersonClickListener;
    }

    @Override
    public PeopleAdapter.PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PersonView view = (PersonView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_user, parent, false);
        return new PersonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        holder.personView.setUser(mPeopleList.get(position));
        holder.personView.setOnPersonClickListener(mOnPersonClickListener);
    }

    @Override
    public int getItemCount() {
        return mPeopleList.size();
    }

    void setPeopleList(List<Person> peopleList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new PeopleListDiffCallback(this.mPeopleList, peopleList));
        this.mPeopleList.clear();
        this.mPeopleList.addAll(peopleList);
        diffResult.dispatchUpdatesTo(this);
    }

}
