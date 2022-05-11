package io.unlokk.onboarding.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intern.R
import com.example.intern.databinding.FragmentLoanPaymentDatesBinding
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.RealmList
import io.realm.kotlin.where
import io.realm.mongodb.User
import io.realm.mongodb.sync.SyncConfiguration
import io.unlokk.onboarding.LoanEndDatesAdapter
import io.unlokk.onboarding.app
import io.unlokk.onboarding.data.LoanPaymentInfo2
import io.unlokk.onboarding.entities.RealmLoanDetails4
import io.unlokk.onboarding.setDivider
import org.bson.types.ObjectId


@AndroidEntryPoint
class LoanPaymentDatesFragment : Fragment(){
    private lateinit var realm: Realm
    private var user: User? = null
    private var userRealm: Realm? = null
    private var loanList: RealmLoanDetails4? = null
    private lateinit var adapter: LoanEndDatesAdapter
    private lateinit var binding: FragmentLoanPaymentDatesBinding

    override fun onStart() {
        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val data = arguments?.getString(LOAN_INFO)
        val objectId: ObjectId = ObjectId(data)

        binding = FragmentLoanPaymentDatesBinding.inflate(inflater, container, false)
        val view = binding.root

        user = app.currentUser()
        val partitionValue: String = user?.id.toString()
        val config = SyncConfiguration.Builder(user, partitionValue)
            .waitForInitialRemoteData()
            .build()

        Realm.getInstanceAsync(config, object : Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                loanList =
                    realm.where<RealmLoanDetails4>().containsValue("_id", objectId).findFirst()
/*                Log.d("loanlistas", loanList.toString())
                Log.d("specifinė reiškmė", loanList?.endDateList?.get(1).toString())
                Log.d("visas listas", loanList?.endDateList.toString())*/

                loanList?.let { setupRecyclerView(it.endDateList) }
            }
        })

        return view
    }

    companion object {
        private const val LOAN_INFO = "LOAN_INFO"
        fun newInstance(data: String): LoanPaymentDatesFragment =
            LoanPaymentDatesFragment().apply {
                val bundle = bundleOf(LOAN_INFO to data)
                arguments = bundle
            }
    }

    private fun setupRecyclerView(endDateList: RealmList<LoanPaymentInfo2>) {
        binding.recyclerView.setDivider(R.drawable.recycler_view_divider)
        adapter = LoanEndDatesAdapter(requireContext(), endDateList)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
    }
}