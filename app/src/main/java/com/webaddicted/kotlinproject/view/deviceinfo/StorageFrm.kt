package com.webaddicted.kotlinproject.view.deviceinfo

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.ActivityManager
import android.content.Context
import android.os.*
import android.text.Html
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.webaddicted.kotlinproject.R
import com.webaddicted.kotlinproject.databinding.FrmDevStorageBinding
import com.webaddicted.kotlinproject.view.base.BaseFragment
import java.io.File
import java.text.DecimalFormat
import java.util.*

class StorageFrm : BaseFragment() {
    private lateinit var mBinding: FrmDevStorageBinding
    private val df = DecimalFormat("#")
    companion object {
        val TAG = StorageFrm::class.java.simpleName
        fun getInstance(bundle: Bundle): StorageFrm {
            val fragment =
                StorageFrm()
            fragment.arguments = bundle
            return StorageFrm()
        }
    }

    override fun getLayout(): Int {
        return R.layout.frm_dev_storage
    }

    override fun onViewsInitialized(binding: ViewDataBinding?, view: View) {
        mBinding = binding as FrmDevStorageBinding
        val handler = Handler()
        val runnable = object : Runnable {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
            override fun run() {
                showRAMUsage()
                handler.postDelayed(this, 1000)
            }
        }

        handler.postDelayed(runnable, 0)
        setUpStorageDetails()
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @SuppressLint("SetTextI18n")
    private fun setUpStorageDetails() {
        val ramUsedInt:String
        val ramUsedExt:String

        /** Internal Memory usage */
        val totalInternalValue = getTotalInternalMemorySize()
        val freeInternalValue = getAvailableInternalMemorySize()
        val usedInternalValue = totalInternalValue - freeInternalValue
        ramUsedInt =
            "<font color=\"#000000\">${resources.getString(R.string.used_memory)} : </font>${formatSize(
                usedInternalValue
            )}<br>" +
                    "<font color=\"#000000\">${resources.getString(R.string.free)} : </font>${formatSize(
                        freeInternalValue
                    )}<br>" +
                    "<font color=\"#000000\">${resources.getString(R.string.total)} : </font>${formatSize(
                        totalInternalValue
                    )}<br>"
        mBinding.tvUsedIntmemory.setText(Html.fromHtml(ramUsedInt))
        mBinding.donutInternalStorage.progress = calculatePercentage(
            usedInternalValue.toDouble(),
            totalInternalValue.toDouble()
        ).toFloat()

        if (getExternalMounts()?.size!! > 0) {
            val dirs: Array<File> = ContextCompat.getExternalFilesDirs(activity!!, null)
            mBinding.llExtMemory?.visibility = View.VISIBLE
            /** External Memory usage */
            val totalExternalValue = getTotalExternalMemorySize(dirs)
            val freeExternalValue = getAvailableExternalMemorySize(dirs)
            val usedExternalValue = totalExternalValue - freeExternalValue
            ramUsedExt =
                "<font color=\"#000000\">${resources.getString(R.string.used_memory)} : </font>${formatSize(
                    usedInternalValue
                )}<br>" +
                        "<font color=\"#000000\">${resources.getString(R.string.free)} : </font>${formatSize(
                            freeInternalValue
                        )}<br>" +
                        "<font color=\"#000000\">${resources.getString(R.string.total)} : </font>${formatSize(
                            totalInternalValue
                        )}<br>"
            mBinding.tvUsedExtmemory.setText(Html.fromHtml(ramUsedExt))
            mBinding.donutExternalStorage.progress = df.format(
                calculatePercentage(
                    usedExternalValue.toDouble(),
                    totalExternalValue.toDouble()
                )
            ).toFloat()
        } else {
            mBinding.llExtMemory.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showRAMUsage() {
        val ramUsed:String
        /** RAM Usage count */
        val totalRamValue = totalRamMemorySize()
        val freeRamValue = freeRamMemorySize()
        val usedRamValue = totalRamValue - freeRamValue

        ramUsed =
            "<font color=\"#000000\">${resources.getString(R.string.used_memory)} : </font>${formatSize(
                usedRamValue
            )}<br>" +
                    "<font color=\"#000000\">${resources.getString(R.string.free)} : </font>${formatSize(
                        freeRamValue
                    )}<br>" +
                    "<font color=\"#000000\">${resources.getString(R.string.total)} : </font>${formatSize(
                        totalRamValue
                    )}<br>"
        mBinding.tvUsedMemory.setText(Html.fromHtml(ramUsed))
        mBinding.donutRamUsage.progress =
            calculatePercentage(usedRamValue.toDouble(), totalRamValue.toDouble()).toFloat()
    }

    private fun freeRamMemorySize(): Long {
        val mi = ActivityManager.MemoryInfo()
        val activityManager =
            activity?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.getMemoryInfo(mi)

        return mi.availMem
    }

    private fun totalRamMemorySize(): Long {
        val mi = ActivityManager.MemoryInfo()
        val activityManager =
            activity?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.getMemoryInfo(mi)
        return mi.totalMem
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun getAvailableInternalMemorySize(): Long {
        val path = Environment.getDataDirectory()
        val stat = StatFs(path.path)
        val blockSize = stat.blockSizeLong
        val availableBlocks = stat.availableBlocksLong
        return availableBlocks * blockSize
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun getTotalInternalMemorySize(): Long {
        val path = Environment.getDataDirectory()
        val stat = StatFs(path.path)
        val blockSize = stat.blockSizeLong
        val totalBlocks = stat.blockCountLong
        return totalBlocks * blockSize
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun getTotalExternalMemorySize(dirs: Array<File>): Long {
        return if (dirs.size > 1) {
            val stat = StatFs(dirs[1].path)
            val blockSize = stat.blockSizeLong
            val totalBlocks = stat.blockCountLong
            totalBlocks * blockSize
        } else {
            0
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun getAvailableExternalMemorySize(dirs: Array<File>): Long {
        return if (dirs.size > 1) {
            val stat = StatFs(dirs[1].path)
            val blockSize = stat.blockSizeLong
            val availableBlocks = stat.availableBlocksLong
            availableBlocks * blockSize
        } else {
            0
        }
    }

    private fun formatSize(size: Long): String {
        if (size <= 0)
            return "0"
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (Math.log10(size.toDouble()) / Math.log10(1024.0)).toInt()
        return DecimalFormat("#,##0.#").format(
            size / Math.pow(
                1024.0,
                digitGroups.toDouble()
            )
        ) + " " + units[digitGroups]
    }

    /* Checks if external storage is available for read and write */
    fun isExternalStorageWritable(): Boolean {
        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }
    fun calculatePercentage(value: Double, total: Double): Int {
        val usage: Double = (value * 100.0f / total)
        return usage.toInt()
    }
    fun getExternalMounts(): HashSet<String>? {
        val out = HashSet<String>()
        val reg = "(?i).*vold.*(vfat|ntfs|exfat|fat32|ext3|ext4).*rw.*"
        var s = ""
        try {
            val process = ProcessBuilder().command("mount")
                .redirectErrorStream(true).start()
            process.waitFor()
            val `is` = process.inputStream
            val buffer = ByteArray(1024)
            while (`is`.read(buffer) != -1) {
                s = s + String(buffer)
            }
            `is`.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // parse output
        val lines = s.split("\n").toTypedArray()
        for (line in lines) {
            if (!line.toLowerCase(Locale.US).contains("asec")) {
                if (line.matches(Regex(reg))) {
                    val parts = line.split(" ").toTypedArray()
                    for (part in parts) {
                        if (part.startsWith("/")) if (!part.toLowerCase(Locale.US).contains(
                                "vold"
                            )
                        ) out.add(part)
                    }
                }
            }
        }
        return out
    }

}
