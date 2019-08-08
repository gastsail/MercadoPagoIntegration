package com.gaston.meliintegration.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gaston.meliintegration.domain.RequestListDataUseCase
import com.gaston.meliintegration.ui.model.Products

/**
 * Created by Gastón Saillén on 05 August 2019
 */
class MainViewModel(private val productsUseCase: RequestListDataUseCase):ViewModel() {

    private val listProducts = MutableLiveData<List<Products>>()

    init {
        getProductList()
    }

    fun setProducts(products: List<Products>){
        listProducts.value = products
    }

    //Aca se podria reemplazar por corutinas si fuera asyncrono.
    fun getProductList(){
        setProducts(productsUseCase.getProductsList())
    }

    fun getProductListLiveData():LiveData<List<Products>>{
        return listProducts
    }


}