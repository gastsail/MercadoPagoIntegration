package com.gaston.meliintegration.domain

import com.gaston.meliintegration.data.local.ProductsDataSet
import com.gaston.meliintegration.ui.model.Products

/**
 * Created by Gastón Saillén on 08 August 2019
 */
class RequestListDataUseCase {

    val productsData = ProductsDataSet()

    fun getProductsList():List<Products>{
        return productsData.createProducts()
    }
}