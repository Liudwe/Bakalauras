package io.unlokk.onboarding.factories

import io.unlokk.onboarding.data.OfferOptionsData
import io.unlokk.onboarding.entities.SliderConfiguration
import javax.inject.Inject

class SliderConfigurationFactory @Inject constructor() {
    fun getConfiguration(loanOffer: OfferOptionsData): SliderConfiguration {
        return SliderConfiguration(
            minAmount = loanOffer.minAbsolutePrincipalAmount.amount.toInt(),
            maxAmount = loanOffer.maxAbsolutePrincipalAmount.amount.toInt(),
            minTerm = loanOffer.minAbsoluteTerm,
            maxTerm = loanOffer.maxAbsoluteTerm,
            defaultTerm = (loanOffer.maxAbsoluteTerm + loanOffer.minAbsoluteTerm) / 2,
            optionList = loanOffer.options
        )
    }
}