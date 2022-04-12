package io.unlokk.onboarding.data

import kotlinx.serialization.Serializable

@Serializable
data class OptionListData(
    val id: Int,
    val term: Int,
    val minPrincipalAmount: Money,
    val maxPrincipalAmount: Money
)