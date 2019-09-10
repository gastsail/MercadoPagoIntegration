package com.gaston.meliintegration.data.remote

import android.util.Log
import com.gaston.meliintegration.core.exception.Failure
import com.gaston.meliintegration.core.functional.Either
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call

/**
 * Created by Gastón Saillén on 21 August 2019
 */
class SendProductRepo {

    private val db = FirebaseFirestore.getInstance()

    //TODO FIX THIS
     fun setProductIntoFirestore(data: HashMap<String,Any>): Either<Failure,Boolean>{
         db.collection("melitest")
            .add(data)
            .addOnSuccessListener {
                //txt_status.text = "Aguardando Confirmación del servidor..."
                Log.d("DocumentID: ", it.id)
                waitForPreferenceId(it.id)
            }
            .addOnFailureListener {
                Log.d("FirestoreData", "Failure: " + it.message)
                //progressBar.visibility = View.GONE
                //btnSubmitProducto.isEnabled = true
            }
    }

    private fun waitForPreferenceId(docKey:String):{

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

    private fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> Either.Right(transform((response.body() ?: default)))
                false -> Either.Left(Failure.ServerError)
            }
        } catch (exception: Throwable) {
            Either.Left(Failure.ServerError)
        }
    }
}