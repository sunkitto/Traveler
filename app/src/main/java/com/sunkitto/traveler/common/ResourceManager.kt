package com.sunkitto.traveler.common

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun getString(@StringRes id: Int) =
        context.getString(id)
}