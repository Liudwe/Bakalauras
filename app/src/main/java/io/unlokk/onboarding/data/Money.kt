package io.unlokk.onboarding.data

import kotlinx.serialization.Serializable

@Serializable
data class Money(
    val amount: Double,
    val currency: String
)