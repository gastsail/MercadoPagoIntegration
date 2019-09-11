package com.gaston.meliintegration.data.remote

import android.os.Handler
import android.util.Log
import com.gaston.meliintegration.core.exception.Failure
import com.gaston.meliintegration.core.functional.Either
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call

/**
 * Created by Gastón Saillén on 21 August 2019
 */
class SendProductRepo {

    private val db = FirebaseFirestore.getInstance()

    fun returnTestPref(): Either<Failure,String> = Either.Right("SDASD")


    //TODO FIX THIS
     fun setProductIntoFirestore(data: HashMap<String,Any>){
         db.collection("melitest")
             .add(data)
             .addOnSuccessListener {
                 Log.d("DocumentID: ", it.id)
                 waitForPreferenceId(it.id)
             }
             .addOnFailureListener {
                 Log.d("FirestoreData", "Failure: " + it.message)
             }
    }

    private fun waitForPreferenceId(docKey:String){

        val docRef = db.collection("melitest").document(docKey)
        docRef.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->

            if (firebaseFirestoreException != null) {
                Log.w("firestoreException", "Listen failed.", firebaseFirestoreException)
                return@addSnapshotListener
            }

            if(!documentSnapshot?.getString("preference_id").isNullOrEmpty()){
                //txt_status.text = "Listo para pagar"
                //setupCheckOut(public_key, documentSnapshot?.getString("preference_id")!!)
                //progressBar.visibility = View.GONE
                //btnPagar.isEnabled = true
                Either.Right(true)

                Log.d("Preference ID:","${documentSnapshot?.getString("preference_id")}")
            }else{
                Log.e("Exception","ERRORRR")
            }
        }
    }
}