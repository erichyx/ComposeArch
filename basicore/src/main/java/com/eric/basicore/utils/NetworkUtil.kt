package com.eric.basicore.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build


/**
 * Created by eric on 2022/5/27
 */
// 获取网络瞬时连接状态
fun isNetworkConnected(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val caps = cm?.getNetworkCapabilities(cm.activeNetwork) ?: return false
        caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    } else {
        cm?.activeNetworkInfo?.isConnectedOrConnecting ?: false
    }
}
