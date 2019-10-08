package com.gaston.meliintegration.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gaston.meliintegration.data.remote.SearchCuponRepo
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by Gastón Saillén on 08 October 2019
 */
class ApplyCuponUseCase {

    private val searchCuponRepo = SearchCuponRepo()

        fun applyCupon(cuponCode:String):LiveData<String>{
            return searchCuponRepo.searchCupon(cuponCode)
        }
}