package io.unlokk.onboarding.data

import kotlinx.serialization.Serializable

@Serializable
data class CalculateOfferRequest(
    val principal: Money,
    val term: Int
)