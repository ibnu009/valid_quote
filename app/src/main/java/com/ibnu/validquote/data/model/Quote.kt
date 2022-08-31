package com.ibnu.validquote.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Quote (
    val quote: String?,
    val author: String?,
    val category: String?
) : Parcelable

