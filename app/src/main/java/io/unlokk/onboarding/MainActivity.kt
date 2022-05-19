package io.unlokk.onboarding

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.intern.R
import com.example.intern.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import io.unlokk.onboarding.fragments.ApplyLoanFragment
import io.unlokk.onboarding.fragments.DashboardFragment
import io.unlokk.onboarding.fragments.ProfileFragment

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
        navigateFragments()
        mainViewModel.initializeLoanOfferOptions()
    }


    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }

    private fun navigateFragments() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_dashboard -> replaceFragment(dashboardFragment)
                R.id.ic_apply -> replaceFragment(applyLoanFragment)
                R.id.ic_profile -> replaceFragment(profileFragment)
            }
            true
        }
    }
}