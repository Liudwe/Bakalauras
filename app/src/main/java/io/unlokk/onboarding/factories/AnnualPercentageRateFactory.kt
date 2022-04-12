package io.unlokk.onboarding.factories

import io.unlokk.onboarding.data.OfferCalculationData
import io.unlokk.onboarding.entities.AnnualPercentageValue
import javax.inject.Inject

class AnnualPercentageRateFactory @Inject constructor() {
    fun getAPR(getPercentage: OfferCalculationData): AnnualPercentageValue {
        return AnnualPercentageValue(
            value = "${getPercentage.annualPercentageRate}%"
        )
    }
}