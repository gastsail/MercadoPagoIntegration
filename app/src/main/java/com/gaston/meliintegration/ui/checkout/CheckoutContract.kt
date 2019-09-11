package com.gaston.meliintegration.ui.checkout

/**
 * Created by Gastón Saillén on 21 August 2019
 */
interface CheckoutContract {

    interface CheckoutContractView{
        fun applyDiscount()
        fun setupProduct()
        fun submitCheckout()
        fun observeLiveData()
    }
}