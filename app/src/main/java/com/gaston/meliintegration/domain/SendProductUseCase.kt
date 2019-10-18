package com.gaston.meliintegration.domain

import androidx.lifecycle.LiveData
import com.gaston.meliintegration.core.exception.Failure
import com.gaston.meliintegration.data.remote.SendProductRepo

/**
 * Created by Gastón Saillén on 21 August 2019
 */
class SendProductUseCase {

    private val repo = SendProductRepo()

    fun sendProductToFirebase(data:HashMap<String,Any>):LiveData<String>{
        return repo.setProductIntoFirestore(data)
    }

    fun getErrorResponse():LiveData<Failure>{return repo.getTransactionErrorLiveData()}
}