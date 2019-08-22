package com.gaston.meliintegration.ui.checkout

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.gaston.meliintegration.R
import com.gaston.meliintegration.base.BaseFragment
import com.gaston.meliintegration.viewmodel.ProductoCheckoutViewModel
import com.mercadopago.android.px.core.MercadoPagoCheckout
import kotlinx.android.synthetic.main.fragment_product_checkout.*


class ProductCheckoutFragment : BaseFragment<ProductoCheckoutViewModel>(), CheckoutContract.CheckoutContractView {

    private lateinit var imageUri: Uri
    private lateinit var productDesc: String
    private lateinit var productTitle: String
    private var productPrice: Int = -1
    var checkout: MercadoPagoCheckout? = null

    companion object{
        private const val CHECKOUT_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imageUri = ProductCheckoutFragmentArgs.fromBundle(
            arguments!!
        ).imageUri
        productDesc = ProductCheckoutFragmentArgs.fromBundle(
            arguments!!
        ).productDesc
        productTitle = ProductCheckoutFragmentArgs.fromBundle(
            arguments!!
        ).productTitle
        productPrice = ProductCheckoutFragmentArgs.fromBundle(
            arguments!!
        ).productPrice
    }

    override fun getLayout(): Int = R.layout.fragment_product_checkout

    override fun getViewModel(): ProductoCheckoutViewModel {
     return ViewModelProviders.of(requireActivity()).get(ProductoCheckoutViewModel::class.java)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupProduct()
        submitCheckout()
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

    override fun setupCheckout(public_key:String, preference_id:String) {
        checkout = MercadoPagoCheckout.Builder(public_key, preference_id)
            .build()
    }

    override fun submitCheckout() {
        btnPagar.setOnClickListener {
            val data = HashMap<String,Any>()
            data["title"] = productTitle
            data["description"] = productDesc
            data["quantity"] = 1
            data["currency_id"] = "ARS"
            data["unit_price"] = productPrice
            getViewModel().saveProduct(data)
        }
    }

    override fun startCheckoutProcess() {
        checkout?.startPayment(requireContext(), CHECKOUT_REQUEST_CODE)
    }
}
