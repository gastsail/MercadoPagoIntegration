package com.gaston.meliintegration.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.fragment_product_checkout.*


/**
 * Created by Gastón Saillén on 22 August 2019
 */
abstract class BaseFragment<T: ViewModel>: Fragment() {

    private lateinit var viewModel: T

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

    fun showProgress() {
        progressBar.visibility = View.VISIBLE
        btnPagar.visibility = View.GONE
    }

    fun hideProgress() {
        progressBar.visibility = View.GONE
        btnPagar.visibility = View.VISIBLE
    }

    fun showError(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}