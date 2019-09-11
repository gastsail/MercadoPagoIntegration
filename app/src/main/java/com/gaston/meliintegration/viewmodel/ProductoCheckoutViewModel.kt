package com.gaston.meliintegration.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gaston.meliintegration.core.exception.Failure
import com.gaston.meliintegration.domain.SendProductUseCase

/**
 * Created by Gastón Saillén on 21 August 2019
 */
class ProductoCheckoutViewModel: ViewModel() {

    private var preference_id = MutableLiveData<String>()
    private var failure = MutableLiveData<Failure>()

    private val productUseCase = SendProductUseCase()


    fun saveProduct(data: HashMap<String,Any>){
        productUseCase(data){
            it.either(::handleFailure, ::handleSuccessResponse)
        }
    }

     private fun handleSuccessResponse(response: String){
        this.preference_id.value = response
    }

     private fun handleFailure(failure: Failure) {
        this.failure.value = failure
    }

    fun getPreferenceIdLiveData():LiveData<String>{
        return preference_id
    }

    fun getFirebaseError():LiveData<Failure>{
        return failure
    }
}