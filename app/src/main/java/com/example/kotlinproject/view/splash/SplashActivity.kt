package com.example.kotlinproject.view.splash

import android.content.Intent
import android.os.Handler
import android.util.Log
import androidx.databinding.ViewDataBinding
import com.example.kotlinproject.R
import com.example.kotlinproject.databinding.ActivitySplashBinding
import com.example.kotlinproject.global.constant.AppConstant
import com.example.kotlinproject.view.base.BaseActivity
import com.example.kotlinproject.view.language.LanguageActivity

/**
 * Created by Deepak Sharma on 01/07/19.
 */
class SplashActivity : BaseActivity() {
    public val TAG: String = SplashActivity::class.java.simpleName
    private lateinit var mBinding: ActivitySplashBinding
    override fun getLayout(): Int {
        return R.layout.activity_splash
    }

    override fun initUI(binding: ViewDataBinding) {
        mBinding = binding as ActivitySplashBinding
        init()
    }

    private fun init() {
        Log.d(TAG, "splash  ${mCommonViewModel.channelResponse}")
        mCommonViewModel.channelResponse = "my name"
        Log.d(TAG, "splash  ${mCommonViewModel.channelResponse}")
        navigateToNext()
    }

    /**
     * navigate to welcome activity after Splash timer Delay
     */
    private fun navigateToNext() {
        Handler().postDelayed({
           naviController.navigateToLanguage(this)
            finish()
        }, AppConstant.SPLASH_DELAY)
    }
}