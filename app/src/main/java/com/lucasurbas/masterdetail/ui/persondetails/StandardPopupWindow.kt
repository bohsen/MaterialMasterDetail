package com.lucasurbas.masterdetail.ui.persondetails

import android.util.Log
import android.view.View
import android.widget.PopupWindow
import androidx.core.widget.PopupWindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * Created on 11/10/2018.
 */
class StandardPopupWindow() : PopupWindow(), LifecycleObserver {

    init {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun dismiss() {
        Log.i("StandardPopupWindow", "Dismissing popupwindow")
        super.dismiss()
    }

    override fun showAsDropDown(anchor: View?, xoff: Int, yoff: Int, gravity: Int) {
        if (anchor != null) {
            PopupWindowCompat.showAsDropDown(this, anchor, xoff, yoff, gravity)
        } else {
            super.showAsDropDown(anchor, xoff, yoff, gravity)
        }
    }
}