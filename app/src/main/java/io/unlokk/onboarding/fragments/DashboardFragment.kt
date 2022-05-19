package io.unlokk.onboarding.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intern.R
import com.example.intern.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import io.realm.kotlin.where
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration
import io.unlokk.onboarding.*
import io.unlokk.onboarding.entities.RealmLoanDetails4


@AndroidEntryPoint
class DashboardFragment : Fragment()/*, ClickEventHandler*/ {
    private var user: User? = null
    private lateinit var realm: Realm
    private var userRealm: Realm? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ActiveLoansAdapter
    private lateinit var binding: FragmentDashboardBinding
    var loanList: RealmResults<RealmLoanDetails4>? = null
    private val activeLoansViewModel: MainViewModel by activityViewModels()

    override fun onStart() {
        super.onStart()
        user = app.currentUser()
        val partitionValue: String = user?.id.toString()
        val config = SyncConfiguration.Builder(user, partitionValue)
            .waitForInitialRemoteData()
            .build()

        loadLoans(config)
        loadAllLoansButton(config)
        loadActiveLoansButton(config)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    private fun setUpRecyclerView(projectsList: RealmResults<RealmLoanDetails4>) {
        binding.recyclerView.setDivider(R.drawable.recycler_view_divider)
        adapter = ActiveLoansAdapter(
            projectsList,
            requireContext(),
            onClick = { test ->
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, LoanPaymentDatesFragment.newInstance(test))
                    .commit()
            })
        binding.recyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
        binding.containerView.visibility = View.VISIBLE
        binding.progress.visibility = View.GONE
    }

    private fun loadActiveLoansButton(config: SyncConfiguration) {
        binding.activeLoans.setOnClickListener {
            Realm.getInstanceAsync(config, object : Realm.Callback() {
                override fun onSuccess(realm: Realm) {
                    loanList =
                        realm.where<RealmLoanDetails4>().containsValue("status", "Approved")
                            .findAll().sort("date", Sort.DESCENDING)
                    Log.d("tag", loanList.toString())
                    adapter.updateData(loanList)
                }
            })
        }
    }

    private fun loadAllLoansButton(config: SyncConfiguration) {
        binding.allLoans.setOnClickListener {
            Realm.getInstanceAsync(config, object : Realm.Callback() {
                override fun onSuccess(realm: Realm) {
                    loanList =
                        realm.where<RealmLoanDetails4>().findAll().sort("date", Sort.DESCENDING)
                    Log.d("tag", loanList.toString())
                    adapter.updateData(loanList)
                }
            })
        }
    }

    private fun loadLoans(config: SyncConfiguration) {
        Realm.getInstanceAsync(config, object : Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                loanList =
                    realm.where<RealmLoanDetails4>().findAll().sort("date", Sort.DESCENDING)
                (loanList)?.let { setUpRecyclerView(it) }
            }
        })
    }
}