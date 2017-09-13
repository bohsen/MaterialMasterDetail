package com.lucasurbas.masterdetail.ui.people;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lucasurbas.masterdetail.R;
import com.lucasurbas.masterdetail.data.Person;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Lucas on 04/01/2017.
 */

public class PersonView extends FrameLayout {

    @BindView(R.id.item_view_user__row) View row;
    @BindView(R.id.item_view_user__avatar) ImageView avatar;
    @BindView(R.id.item_view_user__name) TextView name;
    @BindView(R.id.item_view_user__description) TextView description;
    @BindView(R.id.item_view_user__action) View star;

    private Person person;
    private PersonView.OnPersonClickListener onPersonClickListener;
    private Unbinder mUnbinder;
    private boolean isSelected = false;

    public interface OnPersonClickListener {

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
        mUnbinder = ButterKnife.bind(this);
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
    public void onDrawableClickActionFront(final View view) {

        final AppCompatImageView imageView = (AppCompatImageView) view;

        if (isSelected) {
            imageView.setBackgroundResource(R.drawable.ic_circle_spin_out);
            AnimationDrawable slideShowAnimation = (AnimationDrawable) imageView.getBackground();
            AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(
                    getContext(),
                    R.animator.card_flip_right_out);
            set.setTarget(imageView);
            set.start();
            slideShowAnimation.start();
            onPersonClickListener.onPersonUnselected(person);
            isSelected = false;
        } else {
            imageView.setBackgroundResource(R.drawable.ic_circle_spin_in);
            AnimationDrawable slideShowAnimation = (AnimationDrawable) imageView.getBackground();
            AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(
                    getContext(),
                    R.animator.card_flip_left_in);
            set.setTarget(imageView);
            set.start();
            slideShowAnimation.start();
            onPersonClickListener.onPersonSelected(person);
            isSelected = true;
        }
    }

    public void removeOnPersonClickListener() {
        this.onPersonClickListener = null;
    }

    public void unBind() {
        mUnbinder.unbind();
    }
}
