package com.lucasurbas.masterdetail.ui.people;

import com.lucasurbas.masterdetail.data.Person;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

/**
 * Created by Lucas on 04/01/2017.
 */

public class PeopleListDiffCallback extends DiffUtil.Callback {

    private List<Person> oldList;
    private List<Person> newList;

    PeopleListDiffCallback(List<Person> oldList, List<Person> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList != null ? oldList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newList != null ? newList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return newList.get(newItemPosition) == oldList.get(oldItemPosition);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return newList.get(newItemPosition).equals(oldList.get(oldItemPosition));
    }
}
