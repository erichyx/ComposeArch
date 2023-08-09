package com.eric.composearch.ui

import android.content.Context
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.eric.basicore.utils.isNetworkConnected

/**
 * Created by eric on 2022/7/8
 */
@Composable
fun OfflineDialog(onRetry: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = "连接失败") },
        text = { Text(text = "网络连接不可用，请稍后再试") },
        confirmButton = {
            TextButton(onClick = onRetry) {
                Text(text = "重试")
            }
        }
    )
}

@Composable
fun rememberNetworkConnectState(
    context: Context = LocalContext.current
) = remember(context) {
    NetworkConnectState(context)
}

class NetworkConnectState(private val context: Context) {
    var isOnline by mutableStateOf(isNetworkConnected(context))
        private set

    fun refreshOnline() {
        isOnline = isNetworkConnected(context)
    }
}

@Composable
fun DetectNetworkState(content: @Composable () -> Unit) {
    val networkState = rememberNetworkConnectState()
    if (networkState.isOnline) {
        content()
    } else {
        OfflineDialog { networkState.refreshOnline() }
    }
}
