package com.gaston.meliintegration.viewmodel

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.gaston.meliintegration.domain.RequestListDataUseCase

/**
 * Created by Gastón Saillén on 08 August 2019
 */
class ViewModelFactory(val requestListDataUseCase: RequestListDataUseCase):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RequestListDataUseCase::class.java).newInstance(requestListDataUseCase)
    }
}