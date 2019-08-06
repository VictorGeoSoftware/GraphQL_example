package com.victor.training.graphql.api

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx2.Rx2Apollo
import io.reactivex.Observable


class ApiRepository(apolloClient: ApolloClient) {

    private val mQuery = FindQuery.builder()
        .name("Marvel")
        .owner("VictorGeoSoftware")
        .build()

    private val apolloCall = apolloClient.query(mQuery)


    fun getSomeDataObs(): Observable<Response<FindQuery.Data>> = Rx2Apollo.from(apolloCall)

}