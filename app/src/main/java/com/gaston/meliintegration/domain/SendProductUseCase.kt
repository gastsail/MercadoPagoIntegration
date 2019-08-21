package com.gaston.meliintegration.domain

import com.gaston.meliintegration.data.network.SendProductRepo

/**
 * Created by Gastón Saillén on 21 August 2019
 */
class SendProductUseCase {

    private val repo = SendProductRepo()

    fun setProductIntoFirestore(data: HashMap<String, Any>) {
        repo.setProductIntoFirestore(data)
    }

}