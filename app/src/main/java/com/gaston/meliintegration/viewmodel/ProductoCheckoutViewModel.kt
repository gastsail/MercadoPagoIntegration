package com.gaston.meliintegration.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gaston.meliintegration.data.remote.SearchCuponRepo
import com.gaston.meliintegration.domain.ApplyCuponUseCase
import com.gaston.meliintegration.domain.SendProductUseCase

/**
 * Created by Gastón Saillén on 21 August 2019
 */
class ProductoCheckoutViewModel: ViewModel() {

    private var preferenceId = MutableLiveData<String>()
    private var cuponCodeResponse = MutableLiveData<String>()
    private val productUseCase = SendProductUseCase()
    private val cuponUseCase = ApplyCuponUseCase()

    fun saveProduct(data:HashMap<String,Any>){
        productUseCase.sendProductToFirebase(data).observeForever { pref_id ->
            preferenceId.value = pref_id
        }
    }

    fun getPreferenceIdLiveData():LiveData<String>{
        return preferenceId
    }

    fun applyCupon(cuponCode:String){
        cuponUseCase.applyCupon(cuponCode).observeForever { cupon_status ->
            cuponCodeResponse.value = cupon_status
        }
    }

    fun getCuponCodeStatus():LiveData<String>{
        return cuponCodeResponse
    }
}