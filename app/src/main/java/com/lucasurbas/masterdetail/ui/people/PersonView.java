package com.lucasurbas.masterdetail.ui.people;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lucasurbas.masterdetail.R;
import com.lucasurbas.masterdetail.data.Person;
import com.lucasurbas.masterdetail.ui.widget.FlipAnimator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Lucas on 04/01/2017.
 */

public class PersonView extends FrameLayout {

    @BindView(R.id.item_view_user__row) View row;
    @BindView(R.id.item_view_user__avatar_front) ImageView frontDrawable;
    @BindView(R.id.item_view_user__avatar_back) ImageView backDrawable;
    @BindView(R.id.item_view_user__name) TextView name;
    @BindView(R.id.item_view_user__description) TextView description;
    @BindView(R.id.item_view_user__action) View star;

    private Person person;
    private PersonView.OnPersonClickListener onPersonClickListener;
    private Unbinder mUnbinder;

    public interface OnPersonClickListener {

        void onDrawableClick(Person person);

        void onPersonClick(Person person);

        void onPersonActionClick(Person person);
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
        mUnbinder = ButterKnife.bind(this);
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

    @OnClick(R.id.item_view_user__avatar_front)
    public void onDrawableClickActionFront(View view) {
        if (onPersonClickListener != null) {
            frontDrawable.setVisibility(View.GONE);
            resetIconYAxis(view);
            backDrawable.setVisibility(View.VISIBLE);
            backDrawable.setAlpha(1f);
            FlipAnimator.flipView(getContext(), backDrawable, frontDrawable, true);
            onPersonClickListener.onDrawableClick(person);
        }
    }

    @OnClick(R.id.item_view_user__avatar_back)
    public void onDrawableClickActionBack(View view) {
        if (onPersonClickListener != null) {
            backDrawable.setVisibility(View.GONE);
            resetIconYAxis(view);
            frontDrawable.setVisibility(View.VISIBLE);
            frontDrawable.setAlpha(1f);
            FlipAnimator.flipView(getContext(), backDrawable, frontDrawable, false);
            onPersonClickListener.onDrawableClick(person);
        }
    }

    public void removeOnPersonClickListener() {
        this.onPersonClickListener = null;
    }

    public void unBind() {
        mUnbinder.unbind();
    }

    private void resetIconYAxis(View view) {
        if (view.getRotationY() != 0) {
            view.setRotationY(0);
        }
    }
}
