package com.gaston.meliintegration.ui.checkout

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.gaston.meliintegration.R
import com.gaston.meliintegration.base.BaseCheckoutFragment
import com.gaston.meliintegration.core.exception.Failure
import com.gaston.meliintegration.utils.Constants.Companion.CHECKOUT_REQUEST_CODE
import com.gaston.meliintegration.utils.Constants.Companion.PUBLIC_KEY
import com.gaston.meliintegration.viewmodel.ProductoCheckoutViewModel
import com.mercadopago.android.px.core.MercadoPagoCheckout
import kotlinx.android.synthetic.main.fragment_product_checkout.*
import android.os.StrictMode


class ProductCheckoutFragment : BaseCheckoutFragment<ProductoCheckoutViewModel>(), CheckoutContract.CheckoutContractView {

    private lateinit var imageUri: Uri
    private lateinit var productDesc: String
    private lateinit var productTitle: String
    private var productPrice: Int = -1
    private var checkout: MercadoPagoCheckout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        imageUri = ProductCheckoutFragmentArgs.fromBundle(arguments!!).imageUri
        productDesc = ProductCheckoutFragmentArgs.fromBundle(arguments!!).productDesc
        productTitle = ProductCheckoutFragmentArgs.fromBundle(arguments!!).productTitle
        productPrice = ProductCheckoutFragmentArgs.fromBundle(arguments!!).productPrice
    }

    override fun setupProduct() {
        productInfoDesc_txtView.text = productDesc
        productInfoTitle_txtView.text = productTitle
        Glide.with(requireActivity()).load(imageUri).centerCrop().into(productInfo_imageView)
        productPriceInfo_txtView.text = "$$productPrice"
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
        applyDiscount()
    }

    override fun applyDiscount() {
        btn_aplicarDescuento.setOnClickListener {
            showProgress()
            disableWriteCupon()
            val disscountCode = cupon_etxt.text.toString().trim()
            getViewModel().applyCupon(disscountCode)
        }
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

    fun setupCheckout(public_key:String, preference_id:String) {
        checkout = MercadoPagoCheckout.Builder(public_key, preference_id)
            .build()
    }

    fun startCheckoutProcess() {
        checkout?.startPayment(requireContext(), CHECKOUT_REQUEST_CODE)
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

        val cuponCodeObserver = Observer<String> { cuponCode ->
            if(!cuponCode.isEmpty() && cuponCode!="error"){
                disableWriteCupon()
                hideProgress()
                showMessage("Cupón aplicado con éxito")
                val percentageOff = cuponCode.trim().substring(0,2).toInt()
                val finalPrice = productPrice - (productPrice * percentageOff / 100)
                productPriceInfo_txtView.text = "$"+(finalPrice).toString()
                productPrice = finalPrice
            }else{
                enablewWriteCupon()
                hideProgress()
                showMessage("Error al aplicar el cupón, es posible que no exista, vuelva a intentarlo.")
            }

        }

        //getViewModel().getFirebaseError().observe(this,errorObserver)
        getViewModel().getPreferenceIdLiveData().observe(this,preferenceObserver)
        getViewModel().getCuponCodeStatus().observe(this,cuponCodeObserver)
    }
}
