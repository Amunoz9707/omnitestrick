package com.test.omni_test_rick.utils

import android.content.Context


fun getString(stringName: String, context: Context): String {
    val resId = context.resources.getIdentifier(stringName, "string", context.packageName)
    return context.resources.getString(resId)
}