package com.gaston.meliintegration.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.gaston.meliintegration.R
import com.gaston.meliintegration.utils.Constants
import com.mercadopago.android.px.core.MercadoPagoCheckout
import com.mercadopago.android.px.internal.util.JsonUtil
import com.mercadopago.android.px.model.Payment
import com.mercadopago.android.px.model.exceptions.MercadoPagoError


class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.CHECKOUT_REQUEST_CODE) {
            if (resultCode == MercadoPagoCheckout.PAYMENT_RESULT_CODE) {
                val payment = data?.getSerializableExtra(MercadoPagoCheckout.EXTRA_PAYMENT_RESULT) as Payment
                showToast(payment.paymentStatus)
                if(payment.paymentStatus == "approved")
                startActivity(Intent(this,CongratsActivity::class.java))
            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (data?.getStringExtra("mercadoPagoError") != null) {
                    val mercadoPagoError = JsonUtil.getInstance()
                        .fromJson(data.getStringExtra("mercadoPagoError"), MercadoPagoError::class.java)
                    showToast(mercadoPagoError.message)
                    //Resolve error in setupCheckout
                } else {
                    //Resolve canceled setupCheckout
                }
            }
        }
    }


}
