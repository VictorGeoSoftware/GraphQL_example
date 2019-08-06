package com.victor.training.graphql.data

import com.victor.training.graphql.api.ApiRepository
import com.victor.training.graphql.api.getApolloClient
import com.victor.training.graphql.data.models.DomainModel
import io.reactivex.Observable

class DataManager {
    private val mApiRepository = ApiRepository(getApolloClient())

    fun getSomeDataFromApi(): Observable<DomainModel> {
        return mApiRepository.getSomeDataObs()
            .flatMap {
                println("victor - getSomeDataFromApi - error :: ${it.errors()}")
                println("victor - getSomeDataFromApi - error :: ${it.data()}")
                println("victor - getSomeDataFromApi - error :: ${it.data()?.repository()}")
                if (it.hasErrors()) {
                    Observable.error(Throwable(it.errors().first().message()))
                } else {
                    val model = DomainModel(it.data()?.repository()?.name() ?: "", it.data()?.repository()?.description() ?: "")
                    Observable.just(model)
                }
            }
    }
}