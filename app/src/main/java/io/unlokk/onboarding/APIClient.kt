package io.unlokk.onboarding

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType

object APIClient {
    fun getClient(): Retrofit? {
        val contentType = "application/json".toMediaType()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.bio-matic.com/")
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
        return retrofit
    }
}