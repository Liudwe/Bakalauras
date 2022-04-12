package io.unlokk.onboarding.factories

import io.unlokk.onboarding.data.OfferCalculationData
import io.unlokk.onboarding.entities.LoanIssuanceDetailsAdapterItem
import javax.inject.Inject

class OfferCalculationFactory @Inject constructor() {
    fun getItems(calculationOffers: OfferCalculationData): List<LoanIssuanceDetailsAdapterItem> {
        return listOf(
            LoanIssuanceDetailsAdapterItem(
                "Monthly payment",
                "${calculationOffers.monthlyPaymentAmount.amount}€"
            ),
            LoanIssuanceDetailsAdapterItem(
                "Fee amount",
                "${calculationOffers.fees.amount}€"
            ),
            LoanIssuanceDetailsAdapterItem(
                "Annual interest rate",
                "${(calculationOffers.annualInterestRate * 100).toInt()}%"
            ),
            LoanIssuanceDetailsAdapterItem(
                "In total you'll pay",
                "${calculationOffers.totalAmount.amount}€"
            )
        )
    }
}