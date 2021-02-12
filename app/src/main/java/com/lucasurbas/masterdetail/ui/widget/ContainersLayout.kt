package com.lucasurbas.masterdetail.ui.widget

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.lucasurbas.masterdetail.ui.main.MainNavigator.State
import com.lucasurbas.masterdetail.ui.main.MainNavigator.State.SINGLE_COLUMN_DETAILS
import com.lucasurbas.masterdetail.ui.main.MainNavigator.State.SINGLE_COLUMN_MASTER
import com.lucasurbas.masterdetail.ui.main.MainNavigator.State.TWO_COLUMNS_EMPTY
import com.lucasurbas.masterdetail.ui.main.MainNavigator.State.TWO_COLUMNS_WITH_DETAILS
import com.lucasurbas.masterdetail.ui.util.ViewUtils

/**
 * Created by Lucas on 03/01/2017.
 */
class ContainersLayout : FrameLayout {
    private var state: State? = null

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_main_containers, this, true)
    }

    fun hasTwoColumns(): Boolean {
        return activity_main__space_master != null && activity_main__space_details != null
    }

    private fun singleColumnMaster() {
        if (hasTwoColumns()) {
            activity_main__space_master.setVisibility(View.GONE)
            activity_main__space_details.setVisibility(View.GONE)
            frameDetails.setVisibility(View.GONE)
        } else {
            animateOutFrameDetails()
        }
        activity_main__frame_master.setVisibility(View.VISIBLE)
    }

    private fun singleColumnDetails() {
        if (hasTwoColumns()) {
            activity_main__space_master.setVisibility(View.GONE)
            activity_main__space_details.setVisibility(View.GONE)
        }
        activity_main__frame_master.setVisibility(View.GONE)
        frameDetails.setVisibility(View.VISIBLE)
    }

    private fun twoColumnsEmpty() {
        if (hasTwoColumns()) {
            activity_main__space_master.setVisibility(View.VISIBLE)
            activity_main__space_details.setVisibility(View.VISIBLE)
            frameDetails.setVisibility(View.VISIBLE)
        } else {
            animateOutFrameDetails()
        }
        activity_main__frame_master.setVisibility(View.VISIBLE)
    }

    private fun twoColumnsWithDetails() {
        if (hasTwoColumns()) {
            activity_main__space_master.setVisibility(View.VISIBLE)
            activity_main__space_details.setVisibility(View.VISIBLE)
            activity_main__frame_master.setVisibility(View.VISIBLE)
            frameDetails.setVisibility(View.VISIBLE)
        } else {
            animateInFrameDetails()
        }
    }

    private fun animateInFrameDetails() {
        frameDetails.setVisibility(View.VISIBLE)
        ViewUtils.onLaidOut(frameDetails) {
            val alpha: ObjectAnimator =
                ObjectAnimator.ofFloat<View>(frameDetails, View.ALPHA, 0.4f, 1f)
            val translate: ObjectAnimator = ObjectAnimator.ofFloat<View>(
                frameDetails,
                View.TRANSLATION_Y,
                frameDetails.getHeight() * 0.3f,
                0f
            )
            val set = AnimatorSet()
            set.playTogether(alpha, translate)
            set.setDuration(ANIM_DURATION.toLong())
            set.setInterpolator(LinearOutSlowInInterpolator())
            set.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    activity_main__frame_master.setVisibility(View.GONE)
                }
            })
            set.start()
        }
    }

    private fun animateOutFrameDetails() {
        ViewUtils.onLaidOut(frameDetails, Runnable {
            if (!frameDetails.isShown()) {
                return@Runnable
            }
            val alpha: ObjectAnimator =
                ObjectAnimator.ofFloat<View>(frameDetails, View.ALPHA, 1f, 0f)
            val translate: ObjectAnimator = ObjectAnimator.ofFloat<View>(
                frameDetails,
                View.TRANSLATION_Y,
                0f,
                frameDetails.getHeight() * 0.3f
            )
            val set = AnimatorSet()
            set.playTogether(alpha, translate)
            set.setDuration(ANIM_DURATION.toLong())
            set.setInterpolator(FastOutLinearInInterpolator())
            set.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    frameDetails.setAlpha(1f)
                    frameDetails.setTranslationY(0f)
                    frameDetails.setVisibility(View.GONE)
                }
            })
            set.start()
        })
    }

    fun setState(state: State?) {
        this.state = state
        when (state) {
            SINGLE_COLUMN_MASTER -> singleColumnMaster()
            SINGLE_COLUMN_DETAILS -> singleColumnDetails()
            TWO_COLUMNS_EMPTY -> twoColumnsEmpty()
            TWO_COLUMNS_WITH_DETAILS -> twoColumnsWithDetails()
        }
    }

    protected override fun onSaveInstanceState(): Parcelable? {
        val bundle = Bundle()
        bundle.putParcelable(STATE_SUPER, super.onSaveInstanceState())
        bundle.putString(STATE_CONTAINERS_STATE, state!!.name)
        return bundle
    }

    override fun onRestoreInstanceState(parcelable: Parcelable) {
        var parcelable: Parcelable = parcelable
        if (parcelable is Bundle) {
            val bundle: Bundle = parcelable as Bundle
            setState(MainNavigator.State.valueOf(bundle.getString(STATE_CONTAINERS_STATE)))
            parcelable = bundle.getParcelable<Parcelable>(STATE_SUPER)
        }
        super.onRestoreInstanceState(parcelable)
    }

    fun getState(): State? {
        return state
    }

    companion object {
        const val ANIM_DURATION = 250
        private const val STATE_SUPER = "state_super"
        private const val STATE_CONTAINERS_STATE = "state_containers_state"
    }
}