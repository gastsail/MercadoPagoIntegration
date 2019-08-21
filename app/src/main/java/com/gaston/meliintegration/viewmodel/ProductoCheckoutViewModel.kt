package com.gaston.meliintegration.viewmodel

import androidx.lifecycle.ViewModel
import com.gaston.meliintegration.domain.SendProductUseCase

/**
 * Created by Gastón Saillén on 21 August 2019
 */
class ProductoCheckoutViewModel: ViewModel() {

    private val productUseCase = SendProductUseCase()

    fun saveProduct(data: HashMap<String,Any>){
        productUseCase.setProductIntoFirestore(data)
    }
}