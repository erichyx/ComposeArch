package com.eric.composearch.utils

import com.eric.composearch.BuildConfig


const val APP_DEBUG_TAG = "eric"
val TEST_MODE = BuildConfig.DEBUG || BuildConfig.FLAVOR == "beta"
