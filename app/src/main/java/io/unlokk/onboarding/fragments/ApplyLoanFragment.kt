package io.unlokk.onboarding.fragments

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.example.intern.R
import com.example.intern.databinding.FragmentApplyLoanBinding
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration
import io.unlokk.onboarding.LoanIssuanceDetailsAdapter
import io.unlokk.onboarding.MainViewModel
import io.unlokk.onboarding.app
import io.unlokk.onboarding.data.LoanPaymentInfo2
import io.unlokk.onboarding.data.OptionListData
import io.unlokk.onboarding.entities.RealmLoanDetails
import io.unlokk.onboarding.entities.RealmLoanDetails4
import io.unlokk.onboarding.entities.SliderConfiguration
import io.unlokk.onboarding.setDivider
import org.bson.types.ObjectId
import org.joda.time.DateTime
import org.joda.time.LocalDate
import java.time.temporal.TemporalQueries.localDate
import java.util.*


@AndroidEntryPoint
class ApplyLoanFragment : Fragment() {

    private lateinit var realm: Realm
    private var user: User? = null
    private lateinit var recyclerAdapter: LoanIssuanceDetailsAdapter
    private lateinit var binding: FragmentApplyLoanBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentApplyLoanBinding.inflate(inflater, container, false)
        val view = binding.root
        mainViewModel.initializeLoanOfferOptions()
        recyclerAdapter = LoanIssuanceDetailsAdapter(this)
        observeChanges()
        setupRecyclerView()

        binding.confirmationButton.setOnClickListener {
            val testObj = RealmList<LoanPaymentInfo2>()
            for (i in 1..binding.termSlider.currentValue) // loop to create list of objects of loan payment end dates
                testObj.add(
                    LoanPaymentInfo2(
                        endDate = DateTime.now().plusMonths(i).toDate(),
                        paidDate = Date(),
                        /*_partition = user?.id.toString()*/
                    )
                )
            //val endDate = DateTime.now().plusMonths(binding.termSlider.currentValue).toDate()
            /*val realmLoanInfo = RealmLoanDetails(
                fullLoan = mainViewModel.fullAmount.toInt(),
                loanPaid = 200,
                nextLoanPayment = mainViewModel.monthlyPayment.toInt(),
                _partition = user?.id.toString()
            )*/
            val testTemp = RealmLoanDetails4(
                fullLoan = mainViewModel.fullAmount,
                loanTaken = binding.amountSlider.currentValue.toDouble(),
                term = binding.termSlider.currentValue,
                nextLoanPayment = mainViewModel.monthlyPayment,
                _partition = user?.id.toString(),
                endDateList = testObj
            )
            realm.executeTransactionAsync { realm ->
                realm.insert(testTemp)
            }
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        user = app.currentUser()
        val partitionValue: String = user?.id.toString()
        val config = SyncConfiguration.Builder(user, partitionValue)
            .waitForInitialRemoteData()
            .build()

        Realm.getInstanceAsync(config, object : Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                this@ApplyLoanFragment.realm = realm
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    private fun observeChanges() {
        mainViewModel.sliderConfiguration.observe(viewLifecycleOwner) {
            configureSlider(configuration = it)
            configureAmountOfferValues(configuration = it.optionList)
        }

        mainViewModel.calculatedOffer.observe(viewLifecycleOwner) {
            recyclerAdapter.updateList(items = it)
        }

        mainViewModel.annualPercentageRate.observe(viewLifecycleOwner) {
            val aprValue = String.format(
                resources.getString(R.string.repayment_apr_description_label),
                it.value
            )
            binding.aprTextView.text = aprValue

            val aprText = binding.aprTextView.text
            val spannable = SpannableString(binding.aprTextView.text)
            spannable.setSpan(
                ForegroundColorSpan(Color.BLACK),
                aprText.indexOf(it.value),
                aprText.indexOf(it.value) + it.value.length,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            spannable.setSpan(
                StyleSpan(Typeface.BOLD),
                aprText.indexOf(it.value),
                aprText.indexOf(it.value) + it.value.length,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            binding.aprTextView.text = spannable
        }
    }

    private fun configureSlider(configuration: SliderConfiguration) {
        with(binding) {
            amountSlider.minValue = configuration.minAmount
            amountSlider.maxValue = configuration.maxAmount
            configuration.optionList.firstOrNull { it.term == configuration.defaultTerm }?.let {
                amountSlider.limitValue = it.maxPrincipalAmount.amount.toInt()
                amountSlider.defaultValue = it.maxPrincipalAmount.amount.toInt()
            }
            termSlider.minValue = configuration.minTerm
            termSlider.maxValue = configuration.maxTerm
            termSlider.defaultValue = configuration.defaultTerm

            mainViewModel.calculateOffer(
                binding.amountSlider.defaultValue,
                binding.termSlider.defaultValue
            )

            TransitionManager.beginDelayedTransition(binding.root)
        }
    }

    private fun configureAmountOfferValues(
        configuration: List<OptionListData>
    ) {
        binding.amountSlider.onValueChanged = {
            mainViewModel.calculateOffer(amount = it, term = binding.termSlider.currentValue)
        }

        binding.termSlider.onValueChanged = {
            binding.amountSlider.limitValue =
                configuration[binding.termSlider.currentValue - 2].maxPrincipalAmount.amount.toInt()
            mainViewModel.calculateOffer(amount = binding.amountSlider.currentValue, term = it)
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.setDivider(R.drawable.recycler_view_divider)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        binding.recyclerView.adapter = recyclerAdapter
        binding.recyclerView.setHasFixedSize(true)
    }
}