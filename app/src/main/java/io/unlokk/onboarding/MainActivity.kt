package io.unlokk.onboarding

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.example.intern.R
import com.example.intern.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration
import io.unlokk.onboarding.data.OptionListData
import io.unlokk.onboarding.entities.SliderConfiguration
import io.unlokk.onboarding.fragments.ApplyLoanFragment
import io.unlokk.onboarding.fragments.DashboardFragment
import io.unlokk.onboarding.fragments.ProfileFragment
import org.bson.types.ObjectId
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val dashboardFragment = DashboardFragment()
    private val applyLoanFragment = ApplyLoanFragment()
    private val profileFragment = ProfileFragment()

    private lateinit var recyclerAdapter: LoanIssuanceDetailsAdapter
    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(dashboardFragment)

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_dashboard -> replaceFragment(dashboardFragment)
                R.id.ic_apply -> replaceFragment(applyLoanFragment)
                R.id.ic_profile -> replaceFragment(profileFragment)
            }
            true
        }

        /*recyclerAdapter = LoanIssuanceDetailsAdapter(this)*/
        mainViewModel.initializeLoanOfferOptions()
        /*observeChanges()
        setupRecyclerView()*/
    }


    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }

    /*private fun observeChanges() {
        mainViewModel.sliderConfiguration.observe(this) {
            configureSlider(configuration = it)
            configureAmountOfferValues(configuration = it.optionList)
        }

        mainViewModel.calculatedOffer.observe(this) {
            recyclerAdapter.updateList(items = it)
        }

        mainViewModel.annualPercentageRate.observe(this) {
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
            containerView.visibility = View.VISIBLE
            *//*progress.visibility = View.GONE*//*
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

    fun setupRecyclerView() {
        binding.recyclerView.setDivider(R.drawable.recycler_view_divider)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = recyclerAdapter
        binding.recyclerView.setHasFixedSize(true)
    }*/
}