package com.gaston.meliintegration.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.gaston.meliintegration.R
import com.gaston.meliintegration.ui.model.Products

/**
 * Created by Gastón Saillén on 05 August 2019
 */
class ListAdapter(val context: Context, val productsList: List<Products>) :
    BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val rowView: View

        if (convertView == null) {
            val inflater = LayoutInflater.from(context)
            rowView = inflater.inflate(R.layout.product_row, parent, false)
            val productObj = productsList[position]
            val productImg = rowView.findViewById<ImageView>(R.id.product_imageView)
            Glide.with(context).load(productObj.imageUri).centerCrop().into(productImg)
            val productTitle = rowView.findViewById<TextView>(R.id.product_title_txtView)
            productTitle.text = productObj.productTitle
            val productDesc = rowView.findViewById<TextView>(R.id.product_desc_txtView)
            productDesc.isSelected = true
            productDesc.text = productObj.productDesc
            val productPrice = rowView.findViewById<TextView>(R.id.product_price_txtView)
            productPrice.text = "$" + productObj.productPrice.toString()
        } else {
            rowView = convertView
        }

        return rowView
    }

    override fun getItem(position: Int): Products {
        return productsList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return if (productsList.isNotEmpty()) {
            productsList.size
        } else {
            0
        }

    }
}