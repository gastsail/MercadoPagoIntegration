package com.gaston.meliintegration.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaston.meliintegration.core.exception.Failure
import com.gaston.meliintegration.domain.SendProductUseCase
import kotlinx.coroutines.launch

/**
 * Created by Gastón Saillén on 21 August 2019
 */
class ProductoCheckoutViewModel: ViewModel() {

    var success = MutableLiveData<Boolean>()
    var failure = MutableLiveData<Failure>()

    private val productUseCase = SendProductUseCase()


    fun saveProduct(data: HashMap<String,Any>){
        productUseCase(data){
            it.either(::handleFailure, ::handleSuccessResponse)
        }
    }

    private fun handleSuccessResponse(response: Boolean){
        this.success.value = response
    }

    private fun handleFailure(failure: Failure) {
        this.failure.value = failure
    }
}