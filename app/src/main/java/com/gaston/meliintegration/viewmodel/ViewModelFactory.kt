package com.gaston.meliintegration.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gaston.meliintegration.domain.RequestListDataUseCase

/**
 * Created by Gastón Saillén on 08 August 2019
 */
class ViewModelFactory(val requestListDataUseCase: RequestListDataUseCase):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RequestListDataUseCase::class.java).newInstance(requestListDataUseCase)
    }
}