package io.unlokk.onboarding.data

import kotlinx.serialization.Serializable

@Serializable
data class OfferOptionsData(
    val id: Int,
    val minAbsoluteTerm: Int,
    val maxAbsoluteTerm: Int,
    val minAbsolutePrincipalAmount: Money,
    val maxAbsolutePrincipalAmount: Money,
    val options: List<OptionListData>
)