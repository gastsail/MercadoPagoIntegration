package com.gaston.meliintegration.data.local

import android.net.Uri
import com.gaston.meliintegration.data.model.Products

/**
 * Created by Gastón Saillén on 05 August 2019
 */
class ProductsDataSet {

    fun createProducts(): List<Products> {

        return listOf(
            Products(getImageUri("proyector"),"Benq Proyector","Gran Imagen en Pequeños Espacios",56590),
            Products(getImageUri("ps4"),"Playstation 4","Una PS4 más estilizada y pequeña",26999),
            Products(getImageUri("tvphilips"),"Smart Tv Led Philips","Cientos de aplicaciones para navegar y disfrutar",11599)
        )
    }

    fun getImageUri(image_name:String):Uri{
        return Uri.parse("android.resource://com.gaston.meliintegration/drawable/$image_name")
    }

}