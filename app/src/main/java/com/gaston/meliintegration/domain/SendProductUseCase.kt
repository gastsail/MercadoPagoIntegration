package com.gaston.meliintegration.domain

import com.gaston.meliintegration.core.exception.Failure
import com.gaston.meliintegration.core.functional.Either
import com.gaston.meliintegration.core.interactor.UseCase
import com.gaston.meliintegration.data.remote.SendProductRepo

/**
 * Created by Gastón Saillén on 21 August 2019
 */
class SendProductUseCase: UseCase<String, HashMap<String, Any>>() {

    private val repo = SendProductRepo()

    override suspend fun run(params: HashMap<String, Any>): Either<Failure, String> {
        //TODO FIX THIS
        return repo.returnTestPref()
    }

}