package io.unlokk.onboarding

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.unlokk.onboarding.data.CalculateOfferRequest
import io.unlokk.onboarding.data.Money
import io.unlokk.onboarding.data.OfferCalculationData
import io.unlokk.onboarding.data.OfferOptionsData
import io.unlokk.onboarding.entities.AnnualPercentageValue
import io.unlokk.onboarding.entities.LoanIssuanceDetailsAdapterItem
import io.unlokk.onboarding.entities.SliderConfiguration
import io.unlokk.onboarding.factories.AnnualPercentageRateFactory
import io.unlokk.onboarding.factories.OfferCalculationFactory
import io.unlokk.onboarding.factories.SliderConfigurationFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val annualPercentageRateFactory: AnnualPercentageRateFactory,
    private val offerCalculationFactory: OfferCalculationFactory,
    private val sliderConfigurationFactory: SliderConfigurationFactory
) : ViewModel() {
    val calculatedOffer = MutableLiveData<List<LoanIssuanceDetailsAdapterItem>>()
    val sliderConfiguration = MutableLiveData<SliderConfiguration>()
    val annualPercentageRate = MutableLiveData<AnnualPercentageValue>()
    var monthlyPayment: Double = 0.0
    var fullAmount: Double = 0.0
    private val service = APIClient.getClient()?.create(APIInterface::class.java)

    fun initializeLoanOfferOptions() {
        val call = service?.doGetListResources()
        call?.enqueue(object : Callback<OfferOptionsData> {
            override fun onResponse(
                call: Call<OfferOptionsData>,
                response: Response<OfferOptionsData>
            ) {
                response.body()?.let {
                    sliderConfiguration.postValue(sliderConfigurationFactory.getConfiguration(it))
                }
            }

            override fun onFailure(call: Call<OfferOptionsData>, t: Throwable) {
                call.cancel()
                Log.d("Tag", t.printStackTrace().toString())
            }
        })
    }

    fun calculateOffer(amount: Int, term: Int) {
        val postOffer = CalculateOfferRequest(Money(amount.toDouble(), "EUR"), term)
        val call = service?.doOfferCalculations(postOffer)
        call?.enqueue(object : Callback<OfferCalculationData> {
            override fun onResponse(
                call: Call<OfferCalculationData>,
                response: Response<OfferCalculationData>
            ) {
                response.body()?.let {
                    calculatedOffer.postValue(offerCalculationFactory.getItems(it))
                    annualPercentageRate.postValue(annualPercentageRateFactory.getAPR(it))
                    fullAmount = it.totalAmount.amount
                    monthlyPayment = it.monthlyPaymentAmount.amount
                }
            }

            override fun onFailure(call: Call<OfferCalculationData>, t: Throwable) {
                call.cancel()
                Log.d("Tag", "error")
            }
        })
    }
}