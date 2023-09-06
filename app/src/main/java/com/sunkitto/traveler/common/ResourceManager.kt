package com.sunkitto.traveler.common

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Handles application resources from a provided [context].
 * @param context application context.
 */
class ResourceManager @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    /**
     * Returns a string for the specified string resource id.
     * @param id string resource id.
     */
    fun getString(@StringRes id: Int): String =
        context.getString(id)
}