package com.gaston.meliintegration.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mercadopago.android.px.core.MercadoPagoCheckout
import com.gaston.meliintegration.R
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
