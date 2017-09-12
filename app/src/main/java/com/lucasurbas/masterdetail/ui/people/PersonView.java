package com.lucasurbas.masterdetail.ui.people;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
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
    private boolean isSelected;

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
            ObjectAnimator.ofFloat(view, "rotationY", 360f, 180f).setDuration(800).start();
        } else {
            ObjectAnimator.ofFloat(view, "rotationY", 180f, 360f).setDuration(800).start();
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isSelected) {
                    imageView.setImageDrawable(getContext().getDrawable(R.drawable.list_drawable_front));
                    isSelected = false;
                } else {
                    imageView.setImageDrawable(getContext().getDrawable(R.drawable.list_drawable_back));
                    isSelected = true;
                }
            }
        }, 400);
        onPersonClickListener.onDrawableClick(person);
    }

    public void removeOnPersonClickListener() {
        this.onPersonClickListener = null;
    }

    public void unBind() {
        mUnbinder.unbind();
    }
}
