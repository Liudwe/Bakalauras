package io.unlokk.onboarding.data

import kotlinx.serialization.Serializable

@Serializable
data class OfferCalculationData(
    val annualInterestRate: Double,
    val annualPercentageRate: Double,
    val monthlyPaymentAmount: Money,
    val fees: Money,
    val totalAmount: Money
)