package com.lucasurbas.masterdetail.ui.people;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lucasurbas.masterdetail.R;
import com.lucasurbas.masterdetail.data.Person;
import com.lucasurbas.masterdetail.injection.people.PeopleModule;
import com.lucasurbas.masterdetail.ui.main.MainActivity;
import com.lucasurbas.masterdetail.ui.widget.CustomAppBar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lucas on 04/01/2017.
 */

public class PeopleFragment extends Fragment
        implements PeopleContract.View, PersonView.OnPersonClickListener, ActionMode.Callback {

    @BindView(R.id.fragment_people__swipe_refresh) SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.fragment_people__recycler_view) RecyclerView recyclerView;
    @BindView(R.id.toolbar_top)
    Toolbar toolbar;

    @Inject PeopleContract.Presenter presenter;

    private PeopleAdapter adapter;

    private ActionMode mActionMode;
    private CustomAppBar appBar;


    public static PeopleFragment newInstance() {
        PeopleFragment fragment = new PeopleFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_people, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupToolbar();
        setupRecyclerView();
        setupSwipeRefresh();

        inject();
        presenter.attachView(this);
        presenter.getPeople();
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PeopleAdapter();
        adapter.setOnPersonClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void setupToolbar() {
        appBar = ((MainActivity) getActivity()).getCustomAppBar();
        appBar.setTitle(getString(R.string.fragment_people__title));
        appBar.setMenuRes(R.menu.people_general, R.menu.people_specific, R.menu.people_merged);
    }

    private void setupSwipeRefresh() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadMorePeople();
            }
        });
    }

    private void inject() {
        ((MainActivity) getActivity())
                .getMainComponent()
                .plus(new PeopleModule())
                .inject(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        //swipeRefreshLayout bug
        if (swipeRefresh != null) {
            swipeRefresh.setRefreshing(false);
            swipeRefresh.destroyDrawingCache();
            swipeRefresh.clearAnimation();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter.setOnPersonClickListener(null);
    }

    @Override
    public void showLoading() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showPeopleList(List<Person> peopleList) {
        adapter.setPeopleList(peopleList);
        recyclerView.scrollToPosition(0);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPersonClick(Person person) {
        presenter.loadPersonDetails(person);
    }

    @Override
    public void onPersonActionClick(Person person) {
        presenter.clickPersonAction(person);
    }

    @Override
    public void onPersonSelected(Person person) {
        presenter.select(person);
        adapter.selectedItems.put();
    }

    @Override
    public void onPersonUnselected(Person person) {
        presenter.unselect(person);
        adapter.unselectPerson(person);
    }

    // Called when the action mode is created; startActionMode() was called
    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        // Inflate a menu resource providing context menu items
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.favorites_specific, menu);
        mActionMode = mode;

        swipeRefresh.setEnabled(false);
        return true;
    }

    // Called each time the action mode is shown. Always called after onCreateActionMode, but
    // may be called multiple times if the mode is invalidated.
    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false; // Return false if nothing is done
    }

    // Called when the user selects a contextual menu item
    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_favorites__heart:
                showToast(getString(R.string.action_item_clicked));
                mode.finish(); // Action picked, so close the CAB
                return true;
            default:
                return false;
        }
    }

    // Called when the user exits the action mode
    @Override
    public void onDestroyActionMode(ActionMode mode) {
        Log.d("PeopleFragment", "onDestroyActionMode called");
        presenter.clearSelected();
        swipeRefresh.setEnabled(true);
        mActionMode = null;

    }

    @Override
    public void startActionMode() {
        toolbar.startActionMode(this);
    }

    @Override
    public void stopActionMode() {
        if (mActionMode != null) {
            mActionMode.finish();
        }
    }

    @Override
    public void updateActionModeCount(int count) {
        if (mActionMode != null) {
            mActionMode.setTitle(String.valueOf(count));
            mActionMode.invalidate();
        }
    }

    @Override
    public void clearSelected() {
        adapter.clearSelection();
    }
}
