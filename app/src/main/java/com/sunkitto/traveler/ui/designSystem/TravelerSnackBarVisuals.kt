package com.sunkitto.traveler.ui.designSystem

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals

class TravelerSnackBarVisuals(
    private val snackBarMessage: String,
) : SnackbarVisuals {

    override val actionLabel: String?
        get() = null

    override val duration: SnackbarDuration
        get() = SnackbarDuration.Short

    override val message: String
        get() = snackBarMessage

    override val withDismissAction: Boolean
        get() = false
}