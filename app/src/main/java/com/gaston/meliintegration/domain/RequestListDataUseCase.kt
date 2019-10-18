package com.gaston.meliintegration.domain

import com.gaston.meliintegration.data.local.ProductsDataSet
import com.gaston.meliintegration.data.model.Products

/**
 * Created by Gastón Saillén on 08 August 2019
 */
class RequestListDataUseCase {

    private val productsData = ProductsDataSet()

    fun getProductsList():List<Products>{
        return productsData.createProducts()
    }
}