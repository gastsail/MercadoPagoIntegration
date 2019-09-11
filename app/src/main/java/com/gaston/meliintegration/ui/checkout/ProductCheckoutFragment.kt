package com.gaston.meliintegration.ui.checkout

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.gaston.meliintegration.R
import com.gaston.meliintegration.base.BaseCheckoutFragment
import com.gaston.meliintegration.core.exception.Failure
import com.gaston.meliintegration.utils.Constants.Companion.CHECKOUT_REQUEST_CODE
import com.gaston.meliintegration.utils.Constants.Companion.PUBLIC_KEY
import com.gaston.meliintegration.viewmodel.ProductoCheckoutViewModel
import com.mercadopago.android.px.core.MercadoPagoCheckout
import com.mercadopago.android.px.internal.util.JsonUtil
import com.mercadopago.android.px.model.Payment
import com.mercadopago.android.px.model.exceptions.MercadoPagoError
import kotlinx.android.synthetic.main.fragment_product_checkout.*


class ProductCheckoutFragment : BaseCheckoutFragment<ProductoCheckoutViewModel>(), CheckoutContract.CheckoutContractView {

    private lateinit var imageUri: Uri
    private lateinit var productDesc: String
    private lateinit var productTitle: String
    private var productPrice: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imageUri = ProductCheckoutFragmentArgs.fromBundle(arguments!!).imageUri
        productDesc = ProductCheckoutFragmentArgs.fromBundle(arguments!!).productDesc
        productTitle = ProductCheckoutFragmentArgs.fromBundle(arguments!!).productTitle
        productPrice = ProductCheckoutFragmentArgs.fromBundle(arguments!!).productPrice
    }

    override fun getLayout(): Int = R.layout.fragment_product_checkout

    override fun getViewModel(): ProductoCheckoutViewModel {
     return ViewModelProviders.of(this).get(ProductoCheckoutViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupProduct()
        submitCheckout()
        observeLiveData()
    }

    override fun setupProduct() {
        productInfoDesc_txtView.text = productDesc
        productInfoTitle_txtView.text = productTitle
        // Glide.with(requireActivity()).load(imageUri).centerCrop().into(product_imageView)
        productPriceInfo_txtView.text = "$$productPrice"
    }

    override fun applyDiscount() {
        TODO("not implemented")
    }

    override fun submitCheckout() {
        btnPagar.setOnClickListener {
            showProgress()
            showMessage("Aguardando confirmación del servidor...")
            val data = HashMap<String,Any>()
            data["title"] = productTitle
            data["description"] = productDesc
            data["quantity"] = 1
            data["currency_id"] = "ARS"
            data["unit_price"] = productPrice
            getViewModel().saveProduct(data)
        }
    }

    override fun observeLiveData() {

        val preferenceObserver = Observer<String>{ preference_id ->
            hideProgress()
            setupCheckout(PUBLIC_KEY,preference_id)
            startCheckoutProcess()
        }

        val errorObserver = Observer<Failure>{ failure ->
            hideProgress()
            showMessage(failure.toString())
        }

        getViewModel().getFirebaseError().observe(this,errorObserver)
        getViewModel().getPreferenceIdLiveData().observe(this,preferenceObserver)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CHECKOUT_REQUEST_CODE) {
            if (resultCode == MercadoPagoCheckout.PAYMENT_RESULT_CODE) {
                val payment = data?.getSerializableExtra(MercadoPagoCheckout.EXTRA_PAYMENT_RESULT) as Payment
                showMessage(payment.paymentStatus)
            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (data?.getStringExtra("mercadoPagoError") != null) {
                    val mercadoPagoError = JsonUtil.getInstance()
                        .fromJson(data.getStringExtra("mercadoPagoError"), MercadoPagoError::class.java)
                    showMessage(mercadoPagoError.message)
                    //Resolve error in setupCheckout
                } else {
                    //Resolve canceled setupCheckout
                }
            }
        }
    }
}
