package com.lucasurbas.masterdetail.ui.people;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.lucasurbas.masterdetail.R;
import com.lucasurbas.masterdetail.data.Person;
import com.lucasurbas.masterdetail.ui.widget.FlipAnimator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Lucas on 04/01/2017.
 */

public class PersonView extends FrameLayout {

    @BindView(R.id.item_view_user__row) View row;
    @BindView(R.id.item_view_user__avatar) ImageView imageView;
    @BindView(R.id.item_view_user__name) TextView name;
    @BindView(R.id.item_view_user__description) TextView description;
    @BindView(R.id.item_view_user__action) View star;

    private Person person;
    private PersonView.OnPersonClickListener onPersonClickListener;
    public boolean isSelected;

    interface OnPersonClickListener {

        void onPersonClick(Person person);
        void onPersonActionClick(Person person);
        void onPersonSelected(Person person);
        void onPersonUnselected(Person person);
    }

    public PersonView(Context context) {
        super(context);
        init();
    }

    public PersonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_view_user_internal, this, true);
        ButterKnife.bind(this);
        isSelected = false;
    }

    public void setUser(Person person) {
        this.person = person;
        name.setText(person.getName());
        description.setText(person.getDescription());
    }

    public void setOnPersonClickListener(
            @Nullable final OnPersonClickListener onPersonClickListener) {
        this.onPersonClickListener = onPersonClickListener;
    }

    @OnClick(R.id.item_view_user__row)
    public void onRowClickAction() {

        if (onPersonClickListener != null) {
            onPersonClickListener.onPersonClick(person);
        }
    }

    @OnClick(R.id.item_view_user__action)
    public void onStarClickAction() {
        if (onPersonClickListener != null) {
            onPersonClickListener.onPersonActionClick(person);
        }
    }

    @OnClick(R.id.item_view_user__avatar)
    public void onDrawableClickAction(final View view) {

        final AppCompatImageView imageView = (AppCompatImageView) view;

        if (isSelected) {
            FlipAnimator.flipOut(getContext(), imageView);
            row.setBackgroundResource(0);
            onPersonClickListener.onPersonUnselected(person);
            isSelected = false;
        } else {
            FlipAnimator.flipIn(getContext(), imageView);
            row.setBackgroundResource(R.drawable.selector_fragment_people);
            onPersonClickListener.onPersonSelected(person);
            isSelected = true;
        }
    }

    protected void clearSelection() {
        FlipAnimator.flipOut(getContext(), imageView);
        row.setBackgroundResource(0);
        isSelected = false;
    }
}
