package com.lucasurbas.masterdetail.ui.widget;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

import com.lucasurbas.masterdetail.R;

/**
 * Created by Thomas on 14/09/2017.
 */

public class FlipAnimator {

    public static void flipIn(Context context, ImageView view) {
        view.setBackgroundResource(R.drawable.animation_list_circle_spin_in);
        AnimationDrawable slideShowAnimation = (AnimationDrawable) view.getBackground();
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(
                context,
                R.animator.card_flip_left_in);
        set.setTarget(view);
        set.start();
        slideShowAnimation.start();

    }

    public static void flipOut(Context context, ImageView view) {
        view.setBackgroundResource(R.drawable.animation_list_circle_spin_out);
        AnimationDrawable slideShowAnimation = (AnimationDrawable) view.getBackground();
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(
                context,
                R.animator.card_flip_right_out);
        set.setTarget(view);
        set.start();
        slideShowAnimation.start();
    }
}
