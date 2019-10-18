package com.gaston.meliintegration.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gaston.meliintegration.core.exception.Failure
import com.google.firebase.firestore.FirebaseFirestore
/**
 * Created by Gastón Saillén on 21 August 2019
 */
class SendProductRepo {

    private val db = FirebaseFirestore.getInstance()
    private val tokenData = MutableLiveData<String>()
    private val fberror = MutableLiveData<Failure>()

    fun setProductIntoFirestore(data: HashMap<String, Any>): LiveData<String> {
        db.collection("melitest")
            .add(data)
            .addOnSuccessListener {
                Log.d("DocumentID: ", it.id)
                val docRef = db.collection("melitest").document(it.id)
                docRef.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->

                    if (firebaseFirestoreException != null) {
                        Log.w("firestoreException", "Listen failed.", firebaseFirestoreException)
                        fberror.value = Failure.NetworkConnection
                        return@addSnapshotListener
                    }

                    if (!documentSnapshot?.getString("preference_id").isNullOrEmpty()) {
                        Log.d("Preference ID:", "${documentSnapshot?.getString("preference_id")}")
                        tokenData.value = documentSnapshot?.getString("preference_id")
                    } else {
                        fberror.value = Failure.ItemNotFoundOrEmpty
                    }
                }
            }.addOnFailureListener {
                fberror.value = Failure.FirebaseError
            }

        return tokenData
    }

    fun getTransactionErrorLiveData():LiveData<Failure>{
        return fberror
    }
}