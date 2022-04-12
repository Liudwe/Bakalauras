package io.unlokk.onboarding

import io.unlokk.onboarding.data.OfferOptionsData
import io.unlokk.onboarding.data.CalculateOfferRequest
import io.unlokk.onboarding.data.OfferCalculationData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIInterface {
    @GET("/unlokk/offer-options")
    fun doGetListResources(): Call<OfferOptionsData>

    @POST("/unlokk/calculate-offer")
    fun doOfferCalculations(@Body postOffer: CalculateOfferRequest): Call<OfferCalculationData>
}