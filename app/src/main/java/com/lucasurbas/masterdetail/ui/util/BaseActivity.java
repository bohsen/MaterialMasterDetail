package com.lucasurbas.masterdetail.ui.util;

import android.os.Bundle;

import com.lucasurbas.masterdetail.app.MasterDetailApplication;
import com.lucasurbas.masterdetail.injection.app.ApplicationComponent;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Lucas on 19/06/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActivityComponent(MasterDetailApplication.getAppComponent(this));
    }

    protected abstract void setupActivityComponent(ApplicationComponent applicationComponent);
}
