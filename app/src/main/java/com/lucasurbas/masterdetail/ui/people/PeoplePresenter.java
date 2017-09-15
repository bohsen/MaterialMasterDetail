package com.lucasurbas.masterdetail.ui.people;

import android.content.Context;
import android.os.Handler;
import android.util.SparseBooleanArray;

import com.lucasurbas.masterdetail.R;
import com.lucasurbas.masterdetail.data.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.inject.Inject;

/**
 * Created by Lucas on 04/01/2017.
 */

public class PeoplePresenter implements PeopleContract.Presenter {

    private Context context;
    private PeopleContract.View view;
    private PeopleContract.Navigator navigator;

    private List<Person> peopleList;
    private List<Person> selectedPeopleList = new ArrayList<>();
    private final SparseBooleanArray selectedItems = new SparseBooleanArray();

    private List<String> names;

    @Inject
    public PeoplePresenter(Context context, PeopleContract.Navigator navigator) {
        this.navigator = navigator;
        this.context = context;

        names = new ArrayList<>();
        names.add("Nolan Mcfetridge");
        names.add("Nick Blackford");
        names.add("Carlee Mucci");
        names.add("Tianna Henricksen");
        names.add("Julie Rathburn");
        names.add("Silvana Stiner");
        names.add("Rudolf Grate");
        names.add("Saran Seaman");
        names.add("Carol Pavao");
        names.add("Karey Shatley");
        names.add("Carlita Frye");
        names.add("Sharita Ekberg");
        names.add("Elvia Huitt");
        names.add("Kesha Liebel");
        names.add("Aleida Vincelette");
        names.add("Stormy Rossiter");
        names.add("Carolina Degner");
        names.add("Ruth Slavin");
        names.add("Delilah Hermosillo");
        names.add("Willow Haley");
    }

    @Override
    public void attachView(PeopleContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void getPeople() {
        peopleList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Person person = new Person(UUID.randomUUID().toString());
            person.setName(getRandomName());
            person.setDescription(context.getString(R.string.fragment_people__lorem_ipsum));
            peopleList.add(person);
        }
        if (view != null) {
            view.showPeopleList(peopleList);
        }
    }

    private String getRandomName() {
        Random r = new Random();
        return names.get(r.nextInt(names.size()));
    }

    @Override
    public void loadPersonDetails(Person person) {
        navigator.goToPersonDetails(person);
    }

    @Override
    public void clickPersonAction(Person person) {
        view.showToast("Action clicked: " + person.getName());
    }

    @Override
    public void loadMorePeople() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (view != null) {
                    view.hideLoading();
                    Person person = new Person(UUID.randomUUID().toString());
                    person.setName(getRandomName());
                    person.setDescription(context.getString(R.string.fragment_people__lorem_ipsum));
                    peopleList.add(0, person);
                    view.showPeopleList(peopleList);
                }
            }
        }, 2000);
    }

    @Override
    public void select(Person person) {
        view.showToast("DrawableAction selected: " + person.getName());
        if(selectedPeopleList.size() == 0) {
            view.startActionMode();
        }
        selectedPeopleList.add(person);
        selectedItems.put(peopleList.indexOf(person), true);
        view.updateActionModeCount(selectedPeopleList.size());
    }

    @Override
    public void unselect(Person person) {
        view.showToast("DrawableAction unselected: " + person.getName());
        selectedPeopleList.remove(person);
        if (selectedItems.get(peopleList.indexOf(person), false)) {
            selectedItems.delete(peopleList.indexOf(person));
        }
        if(selectedPeopleList.size() == 0) {
            view.stopActionMode();
        } else {
            view.updateActionModeCount(selectedPeopleList.size());
        }
    }

    @Override
    public void clearSelected() {
        selectedPeopleList.clear();
        view.clearSelected(selectedItems);
    }
}
