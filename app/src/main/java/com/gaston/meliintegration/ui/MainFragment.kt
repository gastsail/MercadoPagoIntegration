package com.gaston.meliintegration.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import com.gaston.meliintegration.R
import com.gaston.meliintegration.domain.RequestListDataUseCase
import com.gaston.meliintegration.data.model.Products
import com.gaston.meliintegration.viewmodel.MainViewModel
import com.gaston.meliintegration.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.main.*

/**
 * Created by Gastón Saillén on 05 August 2019
 */
class MainFragment : Fragment(),MainViewContract.MainView {

    private lateinit var productAdapter:ListAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = requireActivity().run {
            ViewModelProviders.of(this,ViewModelFactory(RequestListDataUseCase()
            )).get(MainViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModelObserver()
        products_listView.setOnItemClickListener { parent, view, position, id ->
            val selectedProduct = productAdapter.getItem(position)
            val action = MainFragmentDirections.nextAction(selectedProduct.productTitle,selectedProduct.productDesc,selectedProduct.productPrice,selectedProduct.imageUri)
            findNavController().navigate(action)
        }
    }

    override fun setupViewModelObserver() {
        val productObserver = Observer<List<Products>> { prodList ->
            productAdapter = ListAdapter(requireActivity(), prodList)
            products_listView.adapter = productAdapter
        }
        mainViewModel.getProductListLiveData().observe(this,productObserver)
    }
}
