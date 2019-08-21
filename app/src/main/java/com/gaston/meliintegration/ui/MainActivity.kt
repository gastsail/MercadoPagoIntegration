package com.gaston.meliintegration.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.view.View
import com.mercadopago.android.px.core.MercadoPagoCheckout
import kotlinx.android.synthetic.main.activity_main.*
import com.mercadopago.android.px.internal.util.JsonUtil
import com.mercadopago.android.px.model.Payment
import com.mercadopago.android.px.model.exceptions.MercadoPagoError
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.gaston.meliintegration.R
import com.gaston.meliintegration.data.ProductsDataSet
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.main.*


class MainActivity : AppCompatActivity(){

    val public_key = "TEST-81d946e6-87be-4004-a302-39dd463cd449"
    val CHECKOUT_REQUEST_CODE = 1
    var checkout: MercadoPagoCheckout? = null
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

   /* fun setupCheckOut(public_key:String, preference_id:String){
        checkout = MercadoPagoCheckout.Builder(public_key, preference_id)
            .build()
    }

    fun submit(view: View) {
        checkout?.startPayment(this, CHECKOUT_REQUEST_CODE)
    }

    //Sample product
    fun submitProducto(view: View) {
        txt_status.text = "Procesando pago..."
        progressBar.visibility = View.VISIBLE
        btnSubmitProducto.isEnabled = false
        val data = HashMap<String, Any>()
        data["title"] = "Xiaomi Redmi Note 7"
        data["description"] = "Celular xiaomi con todo lo que necesitas para preparar pururus"
        data["quantity"] = 1
        data["currency_id"] = "ARS"
        data["unit_price"] = 11000
        db.collection("melitest")
            .add(data)
            .addOnSuccessListener {
                txt_status.text = "Aguardando Confirmación del servidor..."
                Log.d("DocumentID: ", it.id)
                waitForPreferenceId(it.id)
            }
            .addOnFailureListener {
                Log.d("FirestoreData", "Failure: " + it.message)
                progressBar.visibility = View.GONE
                btnSubmitProducto.isEnabled = true
            }

    }

    //Wait for the preference_id to proceed to setupCheckout
    fun waitForPreferenceId(docKey:String){

        val docRef = db.collection("melitest").document(docKey)
        docRef.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->

            if (firebaseFirestoreException != null) {
                Log.w("firestoreException", "Listen failed.", firebaseFirestoreException)
                return@addSnapshotListener
            }

            if(!documentSnapshot?.getString("preference_id").isNullOrEmpty()){
                txt_status.text = "Listo para pagar"
                setupCheckOut(public_key, documentSnapshot?.getString("preference_id")!!)
                progressBar.visibility = View.GONE
                btnPagar.isEnabled = true
                Toast.makeText(this,"Preference ID : ${documentSnapshot.getString("preference_id")}  ",Toast.LENGTH_SHORT).show()
            }else{
                Log.e("Exception","ERRORRR")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CHECKOUT_REQUEST_CODE) {
            if (resultCode == MercadoPagoCheckout.PAYMENT_RESULT_CODE) {
                val payment = data?.getSerializableExtra(MercadoPagoCheckout.EXTRA_PAYMENT_RESULT) as Payment
                mp_results.text = "Resultado del pago: " + payment.paymentStatus
                //Done!
            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (data?.getStringExtra("mercadoPagoError") != null) {
                    val mercadoPagoError = JsonUtil.getInstance()
                        .fromJson(data.getStringExtra("mercadoPagoError"), MercadoPagoError::class.java)
                    mp_results.text = "Error: " + mercadoPagoError.message
                    //Resolve error in setupCheckout
                } else {
                    //Resolve canceled setupCheckout
                }
            }
        }
    }*/
}
