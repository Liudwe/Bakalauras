package io.unlokk.onboarding.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.intern.R
import com.example.intern.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmResults
import io.realm.kotlin.where
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration
import io.unlokk.onboarding.*
import io.unlokk.onboarding.entities.LoanRealmInfo
import io.unlokk.onboarding.entities.RealmLoanDetails
import org.bson.types.ObjectId
import java.util.*


@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var user: User? = null
    private var userRealm: Realm? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ActiveLoansAdapter

    private lateinit var binding: FragmentDashboardBinding
    private val activeLoansViewModel: MainViewModel by activityViewModels()

    override fun onStart() {
        super.onStart()
        user = app.currentUser()
        val partitionValue: String = user?.id.toString()
        val config = SyncConfiguration.Builder(user, partitionValue)
            .waitForInitialRemoteData()
            .build()

        Realm.getInstanceAsync(config, object : Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                val loanList: RealmResults<RealmLoanDetails> =
                    realm.where<RealmLoanDetails>().findAll()
                setUpRecyclerView(loanList)
            }
        })
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

    private fun setUpRecyclerView(projectsList: RealmResults<RealmLoanDetails>) {
        binding.recyclerView.setDivider(R.drawable.recycler_view_divider)
        adapter = ActiveLoansAdapter(projectsList)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
    }

}