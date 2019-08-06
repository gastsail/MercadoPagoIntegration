package com.gaston.meliintegration.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide

import com.gaston.meliintegration.R
import kotlinx.android.synthetic.main.fragment_product_checkout.*
import kotlinx.android.synthetic.main.product_row.*


class ProductCheckoutFragment : Fragment() {

    private lateinit var imageUri:Uri
    private lateinit var productDesc:String
    private lateinit var productTitle:String
    private var productPrice:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         imageUri = ProductCheckoutFragmentArgs.fromBundle(arguments!!).imageUri
         productDesc = ProductCheckoutFragmentArgs.fromBundle(arguments!!).productDesc
         productTitle = ProductCheckoutFragmentArgs.fromBundle(arguments!!).productTitle
         productPrice = ProductCheckoutFragmentArgs.fromBundle(arguments!!).productPrice
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_checkout, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productInfoDesc_txtView.text = productDesc
        productInfoTitle_txtView.text = productTitle
       // Glide.with(requireActivity()).load(imageUri).centerCrop().into(product_imageView)
        productPriceInfo_txtView.text = "$$productPrice"

    }


}
