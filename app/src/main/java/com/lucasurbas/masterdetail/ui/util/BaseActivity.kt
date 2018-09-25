package com.lucasurbas.masterdetail.ui.util

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lucasurbas.masterdetail.app.MasterDetailApplication
import com.lucasurbas.masterdetail.injection.app.ApplicationComponent
import org.jetbrains.annotations.NotNull


/**
 * Created by Lucas on 19/06/16.
 */
abstract class BaseActivity : AppCompatActivity() {

    val onTouchListeners = listOf<View.OnTouchListener>()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActivityComponent(MasterDetailApplication.getAppComponent(this))
    }

    protected abstract fun setupActivityComponent(@NotNull applicationComponent: ApplicationComponent)

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        for (listener in onTouchListeners)
            listener.onTouch(null, ev)
        return super.dispatchTouchEvent(ev)
    }
}
