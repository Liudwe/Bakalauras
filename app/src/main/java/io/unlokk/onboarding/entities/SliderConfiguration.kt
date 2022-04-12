package io.unlokk.onboarding.entities

import io.unlokk.onboarding.data.OptionListData

data class SliderConfiguration(
    val minAmount: Int,
    val maxAmount: Int,
    val minTerm: Int,
    val maxTerm: Int,
    val defaultTerm: Int,
    val optionList: List<OptionListData>
)