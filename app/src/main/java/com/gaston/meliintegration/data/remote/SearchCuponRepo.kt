package com.gaston.meliintegration.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Created by Gastón Saillén on 08 October 2019
 */
class SearchCuponRepo {

    private val db = FirebaseFirestore.getInstance()
    private val mutableLiveData = MutableLiveData<String>()


    fun searchCupon(cuponCode: String): LiveData<String> {
        db.collection("cupones").get().addOnSuccessListener { result ->
            if (!result.isEmpty) {
                for (documento in result) {
                    if (documento.getString("cupon")!!.trim() == cuponCode.trim()) {
                        db.collection("cupones").document(documento.id).delete()
                            .addOnSuccessListener {
                                mutableLiveData.value = cuponCode
                            }.addOnFailureListener {
                                Log.e("SearchCupon error: ", "${it.message}")
                                mutableLiveData.value = "error"
                            }
                    }
                }
            } else {
                mutableLiveData.value = "error"
            }
        }
        return mutableLiveData
    }
}