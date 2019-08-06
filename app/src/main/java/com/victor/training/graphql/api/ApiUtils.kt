package com.victor.training.graphql.api

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.response.CustomTypeAdapter
import com.apollographql.apollo.response.CustomTypeValue
import com.victor.training.graphql.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import type.CustomType

fun getApolloClient(): ApolloClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY

    val okHttp = OkHttpClient
        .Builder()
        .addInterceptor(interceptor)
        .addInterceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder().method(original.method(),
                original.body())
            builder.addHeader("Authorization", "Bearer ${BuildConfig.GITHUB_TOKEN}")
            chain.proceed(builder.build())
        }
        .build()

    return ApolloClient.builder()
        .serverUrl(BuildConfig.GITHUB_URL)
        .okHttpClient(okHttp)
        .addCustomTypeAdapter(CustomType.URI, object : CustomTypeAdapter<String> {
            override fun encode(value: String): CustomTypeValue<*> {
                return CustomTypeValue.fromRawValue(value)
            }

            override fun decode(value: CustomTypeValue<*>): String {
                try {
                    return value.value.toString()
                } catch (e: Exception) {
                    throw RuntimeException()
                }
            }
        })
        .build()
}