package com.gaston.meliintegration.ui.model

import android.net.Uri

/**
 * Created by Gastón Saillén on 05 August 2019
 */
data class Products(
    val imageUri: Uri, val productTitle: String = "",
    val productDesc: String = "", val productPrice: Int = 0
)