package com.eric.composearch.api

import android.util.Base64
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * Created by eric on 2022/5/8
 */
object FrescoSigner {
    private const val MAC_KEY = "bf7dddc7c9cfe6f7"
    private const val MAC_ALGORITHM = "HmacSHA1"
    private val mac: Mac = Mac.getInstance("HmacSHA1")

    init {
        val secretByte = MAC_KEY.encodeToByteArray()
        val secret = SecretKeySpec(secretByte, MAC_ALGORITHM)
        mac.init(secret)
    }

    @Synchronized
    fun createSign(content: String): String {
        return try {
            val encodeBytes = mac.doFinal(content.encodeToByteArray())
            Base64.encodeToString(encodeBytes, Base64.NO_WRAP)
        } catch (e : Exception) {
            e.printStackTrace()
            ""
        }
    }
}