package com.webaddicted.kotlinproject.view.base

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.webaddicted.kotlinproject.R
import com.webaddicted.kotlinproject.apiutils.ApiResponse
import com.webaddicted.kotlinproject.global.common.*
import com.webaddicted.kotlinproject.global.sharedpref.PreferenceMgr
import com.webaddicted.kotlinproject.model.bean.eventBus.EventBusListener
import com.webaddicted.kotlinproject.view.dialog.LoaderDialog
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.koin.android.ext.android.inject
import java.io.File


/**
 * Created by Deepak Sharma on 15/1/19.
 */
abstract class BaseFragment : Fragment(), View.OnClickListener , PermissionHelper.Companion.PermissionListener,
    MediaPickerUtils.ImagePickerListener {
    private lateinit var mBinding: ViewDataBinding
    private var loaderDialog: LoaderDialog? = null
    protected val mediaPicker: MediaPickerUtils by inject()
    protected val preferenceMgr: PreferenceMgr  by inject()
    abstract fun getLayout(): Int
    protected abstract fun onViewsInitialized(binding: ViewDataBinding?, view: View)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return super.onCreateView(inflater, container, savedInstanceState)
        mBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onViewsInitialized(mBinding, view)
        super.onViewCreated(view, savedInstanceState)
        if (loaderDialog == null) {
            loaderDialog = LoaderDialog.dialog()
            loaderDialog?.isCancelable = false
        }
    }

    protected fun showApiLoader() {
        if (loaderDialog != null) {
            val fragment = fragmentManager?.findFragmentByTag(LoaderDialog.TAG)
            if (fragment != null) fragmentManager?.beginTransaction()?.remove(fragment)?.commit()
            loaderDialog?.show(fragmentManager!!, LoaderDialog.TAG)
        }
    }


    protected fun hideApiLoader() {
        if (loaderDialog != null && loaderDialog?.isVisible!!) loaderDialog?.dismiss()
    }

    protected fun <T> apiResponseHandler(view: View, response: ApiResponse<T>) {
        when (response?.status) {
            ApiResponse.Status.LOADING -> {
                showApiLoader()
            }
            ApiResponse.Status.ERROR -> {
                hideApiLoader()
                if (response.errorMessage != null && response.errorMessage?.length!! > 0)
                    ValidationHelper.showSnackBar(view, response.errorMessage!!)
                else activity?.showToast(getString(R.string.something_went_wrong))
            }
        }
    }
    override fun onResume() {
        super.onResume()
        activity?.let { GlobalUtility.hideKeyboard(it) }
    }
    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun EventBusListener(eventBusListener: EventBusListener) {
    }

    protected fun navigateFragment(layoutContainer: Int, fragment: Fragment, isEnableBackStack: Boolean) {
        if (getActivity() != null) {
            (getActivity() as BaseActivity).navigateFragment(layoutContainer, fragment, isEnableBackStack)
        }
    }

    protected fun navigateAddFragment(layoutContainer: Int, fragment: Fragment, isEnableBackStack: Boolean) {
        if (getActivity() != null) {
            (getActivity() as BaseActivity).navigateAddFragment(layoutContainer, fragment, isEnableBackStack)
        }
    }

    protected fun navigateChildFragment(layoutContainer: Int, fragment: Fragment, isEnableBackStack: Boolean) {
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(layoutContainer, fragment)
        if (isEnableBackStack)
            fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
        fragmentTransaction.commitAllowingStateLoss()
    }

    override fun onClick(v: View) {
//        GlobalUtility.Companion.btnClickAnimation(v)
    }

//    fun getLocation() {
//        (getActivity() as BaseLocation).getLocation()
//    }

//    fun getLocation(@NonNull timeInterval: Long, @NonNull fastInterval: Long, @NonNull displacement: Long) {
//        (getActivity() as BaseLocation).getLocation(timeInterval, fastInterval, displacement)
//    }
//
//    fun getLocationWithAddress(@NonNull timeInterval: Long, @NonNull fastInterval: Long, @NonNull displacement: Long) {
//        (getActivity() as BaseLocation).getLocation(timeInterval, fastInterval, displacement)
//        (getActivity() as BaseLocation).isAddressEnabled(true)
//    }
//
//    fun stopUpdateLocation() {
//        (getActivity() as BaseLocation).stopLocationUpdates()
//    }

    fun checkStoragePermission() {
        val multiplePermission = ArrayList<String>()
        multiplePermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        multiplePermission.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        multiplePermission.add(Manifest.permission.CAMERA)
        if (PermissionHelper.checkMultiplePermission(activity!!, multiplePermission)) {
            FileUtils.createApplicationFolder()
            onPermissionGranted(multiplePermission)
        } else
            PermissionHelper.requestMultiplePermission(activity!!, multiplePermission, this)
    }


     fun checkBlinkPermission() {
        val multiplePermission = ArrayList<String>()
        multiplePermission.add(Manifest.permission.CAMERA)
        multiplePermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        multiplePermission.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        multiplePermission.add(Manifest.permission.ACCESS_FINE_LOCATION)
        multiplePermission.add(Manifest.permission.ACCESS_COARSE_LOCATION)
         if (PermissionHelper.checkMultiplePermission(activity!!, multiplePermission))onPermissionGranted(multiplePermission)
         else PermissionHelper.requestMultiplePermission(activity!!, multiplePermission, this)
    }

    override fun onPermissionGranted(mCustomPermission: List<String>) {
        FileUtils.createApplicationFolder()
    }


    override fun onPermissionDenied(mCustomPermission: List<String>) {
    }

    override fun imagePath(filePath: List<File>) {
    }

    fun checkLocationPermission() {
        (getActivity() as BaseActivity).checkLocationPermission()
    }


    fun getPlaceHolder(imageLoaderPos: Int): String {
        val imageLoader = getResources().getStringArray(R.array.image_loader)
        return imageLoader[imageLoaderPos]
    }
    protected fun addBlankSpace(bottomSpace: LinearLayout) {
        KeyboardEventListener(activity as AppCompatActivity) { isKeyboardOpen: Boolean, softkeybordHeight: Int ->
            if (isKeyboardOpen)
                bottomSpace.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    softkeybordHeight
                )
            else bottomSpace.layoutParams =
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0)
        }
    }
}
