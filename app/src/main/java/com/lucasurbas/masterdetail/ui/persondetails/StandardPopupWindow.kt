package com.lucasurbas.masterdetail.ui.persondetails

import android.util.Log
import android.widget.PopupWindow
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * Created on 11/10/2018.
 */
class StandardPopupWindow() : PopupWindow(), LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun dismiss() {
        Log.i("StandardPopupWindow", "Dismissing popupwindow")
        super.dismiss()
    }
}