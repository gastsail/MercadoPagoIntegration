package com.gaston.meliintegration.data.model

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Gastón Saillén on 05 August 2019
 */
data class Products(
    val imageUri: Uri, val productTitle: String = "",
    val productDesc: String = "", val productPrice: Int = 0
)