package com.gaston.meliintegration.ui

/**
 * Created by Gastón Saillén on 05 August 2019
 */
interface MainView {

    fun updateStatusText(text:String)
    fun enablePayButton()
    fun showProgress()
    fun hideProgress()
    fun showError()
    fun disablePayButton()
    fun inflateDialog()

}