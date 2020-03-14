package com.webaddicted.kotlinproject.view.deviceinfo

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.databinding.ViewDataBinding
import com.webaddicted.kotlinproject.R
import com.webaddicted.kotlinproject.databinding.FrmDevFeatureBinding
import com.webaddicted.kotlinproject.view.base.BaseFragment

class DeviceFeaturesFrm : BaseFragment() {
    private lateinit var mBinding: FrmDevFeatureBinding

    companion object {
        val TAG = DeviceFeaturesFrm::class.java.simpleName
        fun getInstance(bundle: Bundle): DeviceFeaturesFrm {
            val fragment = DeviceFeaturesFrm()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayout(): Int {
        return R.layout.frm_dev_feature
    }

    override fun initUI(binding: ViewDataBinding?, view: View) {
        mBinding = binding as FrmDevFeatureBinding
        getDeviceFeatures()
    }

    private fun getDeviceFeatures() {
        val connManager =
            activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val packageManager = activity?.packageManager
        val featureInfo =
            """<font color="#000000">Wifi : </font>${getAvailability(mWifi.isAvailable)}<br><font color="#000000">WIFI Direct : </font>${getAvailability(
                packageManager?.hasSystemFeature(PackageManager.FEATURE_WIFI_DIRECT)!!
            )}<br><font color="#000000">Bluetooth : </font>${getAvailability(
                packageManager.hasSystemFeature(
                    PackageManager.FEATURE_BLUETOOTH
                )
            )}<br><font color="#000000">Bluetooth LE : </font>${getAvailability(
                packageManager.hasSystemFeature(
                    PackageManager.FEATURE_BLUETOOTH_LE
                )
            )}<br><font color="#000000">GPS : </font>${getAvailability(
                packageManager.hasSystemFeature(
                    PackageManager.FEATURE_LOCATION_GPS
                )
            )}<br><font color="#000000">Camera Flash : </font>${getAvailability(
                packageManager.hasSystemFeature(
                    PackageManager.FEATURE_CAMERA_FLASH
                )
            )}<br><font color="#000000">Camera Front : </font>${getAvailability(
                packageManager.hasSystemFeature(
                    PackageManager.FEATURE_CAMERA_FRONT
                )
            )}<br><font color="#000000">Microphone : </font>${getAvailability(
                packageManager.hasSystemFeature(
                    PackageManager.FEATURE_MICROPHONE
                )
            )}<br><font color="#000000">NFC : </font>${getAvailability(
                packageManager.hasSystemFeature(
                    PackageManager.FEATURE_NFC
                )
            )}<br><font color="#000000">USB Host : </font>${getAvailability(
                packageManager.hasSystemFeature(
                    PackageManager.FEATURE_USB_HOST
                )
            )}<br><font color="#000000">USB Accessory : </font>${getAvailability(
                packageManager.hasSystemFeature(
                    PackageManager.FEATURE_USB_ACCESSORY
                )
            )}<br><font color="#000000">Multitouch : </font>${getAvailability(
                packageManager.hasSystemFeature(
                    PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH
                )
            )}<br><font color="#000000">Audio low-latency : </font>${getAvailability(
                packageManager.hasSystemFeature(
                    PackageManager.FEATURE_AUDIO_LOW_LATENCY
                )
            )}<br><font color="#000000">Audio Output : </font>${getAvailability(
                packageManager.hasSystemFeature(
                    PackageManager.FEATURE_AUDIO_OUTPUT
                )
            )}<br><font color="#000000">Professional Audio : </font>${getAvailability(
                packageManager.hasSystemFeature(
                    PackageManager.FEATURE_AUDIO_PRO
                )
            )}<br><font color="#000000">Consumer IR : </font>${getAvailability(
                packageManager.hasSystemFeature(
                    PackageManager.FEATURE_CONSUMER_IR
                )
            )}<br><font color="#000000">Gamepad Support : </font>${getAvailability(
                packageManager.hasSystemFeature(
                    PackageManager.FEATURE_GAMEPAD
                )
            )}<br><font color="#000000">HIFI Sensor : </font>${getAvailability(
                packageManager.hasSystemFeature(
                    PackageManager.FEATURE_HIFI_SENSORS
                )
            )}<br><font color="#000000">Printing : </font>${getAvailability(
                packageManager.hasSystemFeature(
                    PackageManager.FEATURE_PRINTING
                )
            )}<br><font color="#000000">CDMA : </font>${getAvailability(
                packageManager.hasSystemFeature(
                    PackageManager.FEATURE_TELEPHONY_CDMA
                )
            )}<br><font color="#000000">GSM : </font>${getAvailability(
                packageManager.hasSystemFeature(
                    PackageManager.FEATURE_TELEPHONY_GSM
                )
            )}<br><font color="#000000">Finger-print : </font>${getAvailability(
                packageManager.hasSystemFeature(
                    PackageManager.FEATURE_FINGERPRINT
                )
            )}<br><font color="#000000">App Widgets : </font>${getAvailability(
                packageManager.hasSystemFeature(
                    PackageManager.FEATURE_APP_WIDGETS
                )
            )}<br><font color="#000000">SIP : </font>${getAvailability(
                packageManager.hasSystemFeature(
                    PackageManager.FEATURE_SIP
                )
            )}<br><font color="#000000">SIP based VOIP : </font>${getAvailability(
                packageManager.hasSystemFeature(
                    PackageManager.FEATURE_SIP_VOIP
                )
            )}<br><font color="#000000">Microphone : </font>${getAvailability(
                packageManager.hasSystemFeature(
                    PackageManager.FEATURE_MICROPHONE
                )
            )}<br><font color="#000000">Microphone : </font>${getAvailability(
                packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)
            )}<br><font color="#000000">Microphone : </font>${getAvailability(
                packageManager.hasSystemFeature(
                    PackageManager.FEATURE_MICROPHONE
                )
            )}<br><font color="#000000">Microphone : </font>${getAvailability(
                packageManager.hasSystemFeature(
                    PackageManager.FEATURE_MICROPHONE
                )
            )}<br><font color="#000000">Microphone : </font>${getAvailability(
                packageManager.hasSystemFeature(
                    PackageManager.FEATURE_MICROPHONE
                )
            )}<br>"""
        mBinding.txtDeviceFeature.text = Html.fromHtml(featureInfo)
    }

    private fun getAvailability(available: Boolean): String {
        return if (available) "Available"
        else "Not Supported"
    }
}
