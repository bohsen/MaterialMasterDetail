package com.lucasurbas.masterdetail.ui.util

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lucasurbas.masterdetail.app.MasterDetailApplication
import com.lucasurbas.masterdetail.injection.app.ApplicationComponent
import org.jetbrains.annotations.NotNull


abstract class BaseActivity : AppCompatActivity() {


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActivityComponent(MasterDetailApplication.getAppComponent(this))
    }

    protected abstract fun setupActivityComponent(@NotNull applicationComponent: ApplicationComponent)

}
