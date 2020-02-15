package com.webaddicted.kotlinproject.global.common

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.webaddicted.kotlinproject.R
import com.webaddicted.kotlinproject.global.koin.*
import com.webaddicted.kotlinproject.global.sharedpref.PreferenceUtils
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import com.facebook.stetho.Stetho;
/**
 * Created by Deepak Sharma on 01/07/19.
 */
class AppApplication : Application() {
    private val mNetworkReceiver = NetworkChangeReceiver()
    companion object {
        lateinit var context: Context

    }

    override fun onCreate() {
        super.onCreate()
        context = this
        Stetho.initializeWithDefaults(this)
        FileUtils.createApplicationFolder()
        setupDefaultFont()
        PreferenceUtils.Companion.getInstance(this)
        startKoin {
            androidLogger()
            androidContext(this@AppApplication)
            modules(getModule())
        }
        checkInternetConnection()
    }

    /**
     * set default font for app
     */
    fun setupDefaultFont() {
        CalligraphyConfig.initDefault(
            CalligraphyConfig.Builder()
                .setDefaultFontPath("font/opensans_regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
    }

    private fun getModule(): Iterable<Module> {
        return listOf(
            appModule,
            viewModelModule,
            repoModule,
            commonModelModule,
            dbModule
        )
    }
    private fun checkInternetConnection() {
        registerReceiver(mNetworkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }
}
