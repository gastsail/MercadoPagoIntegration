package com.gaston.meliintegration.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.gaston.meliintegration.ui.checkout.ProductCheckoutFragment
import com.gaston.meliintegration.utils.Constants.Companion.CHECKOUT_REQUEST_CODE
import com.mercadopago.android.px.core.MercadoPagoCheckout
import kotlinx.android.synthetic.main.fragment_product_checkout.*


/**
 * Created by Gastón Saillén on 22 August 2019
 */
abstract class BaseCheckoutFragment<T: ViewModel>: Fragment() {

    private lateinit var viewModel: T
    private var checkout: MercadoPagoCheckout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    @LayoutRes
    abstract fun getLayout():Int

    abstract fun getViewModel():T

    fun startCheckoutProcess() {
        checkout?.startPayment(requireContext(),CHECKOUT_REQUEST_CODE)
    }

    fun setupCheckout(public_key:String, preference_id:String) {
        checkout = MercadoPagoCheckout.Builder(public_key, preference_id)
            .build()
    }

    fun showProgress() {
        progressBar.visibility = View.VISIBLE
        btnPagar.visibility = View.GONE
    }

    fun hideProgress() {
        progressBar.visibility = View.GONE
        btnPagar.visibility = View.VISIBLE
    }

    fun showMessage(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

}