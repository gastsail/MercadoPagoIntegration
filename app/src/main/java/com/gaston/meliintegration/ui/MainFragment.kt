package com.gaston.meliintegration.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.gaston.meliintegration.R
import com.gaston.meliintegration.data.ProductsDataSet
import kotlinx.android.synthetic.main.main.*

/**
 * Created by Gastón Saillén on 05 August 2019
 */
class MainFragment : Fragment() {

    private lateinit var productAdapter:ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Just for testing purposes, this data should come from the viewmodel -> Domain -> Local Repo
        productAdapter = ListAdapter(requireActivity(), ProductsDataSet().createProducts())
        products_listView.adapter = productAdapter
        products_listView.setOnItemClickListener { parent, view, position, id ->

            val selectedProduct = productAdapter.getItem(position)
            val action = MainFragmentDirections.nextAction(selectedProduct.productTitle,selectedProduct.productDesc,selectedProduct.productPrice,selectedProduct.imageUri)
            findNavController().navigate(action)
        }
    }


}
