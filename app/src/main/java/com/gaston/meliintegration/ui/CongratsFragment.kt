package com.gaston.meliintegration.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gaston.meliintegration.R
import kotlinx.android.synthetic.main.fragment_congrats_screen.*

class CongratsFragment : Fragment() {

    private lateinit var orderDetails:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        orderDetails = CongratsFragmentArgs.fromBundle(arguments!!).orderType
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_congrats_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txt_pago_concretado.text = "Su pago fue completado \n \n Orden: $orderDetails"

    }



}
