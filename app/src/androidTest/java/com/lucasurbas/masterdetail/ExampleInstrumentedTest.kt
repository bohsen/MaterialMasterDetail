package com.lucasurbas.masterdetail


import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lucasurbas.masterdetail.app.MasterDetailApplication
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    @Throws(Exception::class)
    fun useAppContext() { // Context of the app under test.
        val appContext = ApplicationProvider.getApplicationContext<MasterDetailApplication>()
        Assert.assertEquals("com.lucasurbas.masterdetails", appContext.packageName)
    }
}