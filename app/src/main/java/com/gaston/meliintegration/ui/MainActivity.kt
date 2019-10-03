package com.gaston.meliintegration.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.gaston.meliintegration.R
import com.gaston.meliintegration.ui.checkout.ProductCheckoutFragmentDirections
import com.gaston.meliintegration.utils.Constants
import com.mercadopago.android.px.core.MercadoPagoCheckout
import com.mercadopago.android.px.internal.util.JsonUtil
import com.mercadopago.android.px.model.Payment
import com.mercadopago.android.px.model.exceptions.MercadoPagoError


class MainActivity : AppCompatActivity(){

    private var checkout: MercadoPagoCheckout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }



    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


}
