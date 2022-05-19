package io.unlokk.onboarding.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class LoanPaymentDatesFragment : Fragment() {
    private lateinit var realm: Realm
    private var user: User? = null
    private var userRealm: Realm? = null
    private var loanList: RealmLoanDetails4? = null
    private var loanListEdit: RealmLoanDetails4? = null
    private lateinit var adapter: LoanEndDatesAdapter
    private lateinit var binding: FragmentLoanPaymentDatesBinding
    private lateinit var objectId: ObjectId

    override fun onStart() {
        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val data = arguments?.getString(LOAN_INFO)
        objectId = ObjectId(data)
        binding = FragmentLoanPaymentDatesBinding.inflate(inflater, container, false)
        val view = binding.root
        loadRealmLoanEndDates()
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
        adapter = LoanEndDatesAdapter(requireContext(), endDateList,
            onClick = { data ->
                if (loanList!!.endDateList[data]!!.status == "Not paid")
                    alertDialogForPayment(data)
                else {
                    val dateTemp =
                        SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH).parse(
                            loanList!!.endDateList[data]!!.paidDate.toString()
                        )
                    val formattedDateTemp =
                        SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(dateTemp)

                    Toast.makeText(
                        context,
                        "You have paid this monthly payment: " + formattedDateTemp,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
    }

    fun alertDialogForPayment(data: Int) {
        val dialog = AlertDialog.Builder(context)
        Log.d("tag", data.toString())
        dialog.setTitle("Loan payment")
        dialog.setMessage("Do you wish to pay this monthly loan payment?")
        dialog.setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->

            val partitionValue: String = user?.id.toString()
            val config = SyncConfiguration.Builder(user, partitionValue)
                .waitForInitialRemoteData()
                .build()

            Realm.getInstanceAsync(config, object : Realm.Callback() {
                override fun onSuccess(realm: Realm) {
                    var paidAmount = 0
                    loanList =
                        realm.where<RealmLoanDetails4>().containsValue("_id", objectId).findFirst()
                    if (loanList != null) {
                        realm.beginTransaction()

                        val df = DecimalFormat("#.##")
                        df.roundingMode = RoundingMode.DOWN
                        val loanPaidToDecimal: Double =
                            loanList!!.loanPaid + loanList!!.nextLoanPayment
                        loanList!!.loanPaid = df.format(loanPaidToDecimal).toDouble()
                        loanList!!.endDateList[data]?.paidDate = Date()
                        loanList!!.endDateList[data]?.status = "Paid"
                        for (item in loanList!!.endDateList) {
                            if (item.status == "Paid")
                                paidAmount += 1
                        }
                        if (paidAmount == loanList!!.endDateList.size) {
                            loanList!!.status = "Repaid"
                            loanList!!.loanPaid = loanList!!.fullLoan
                        }
                    }
                    adapter.updateList(loanList!!.endDateList)
                    realm.commitTransaction()
                    realm.close()
                }
            })
        })
        dialog.setNeutralButton("No", DialogInterface.OnClickListener
        { _, _ ->
        })
        dialog.show()
    }

    fun loadRealmLoanEndDates() {
        user = app.currentUser()
        val partitionValue: String = user?.id.toString()
        val config = SyncConfiguration.Builder(user, partitionValue)
            .waitForInitialRemoteData()
            .build()

        Realm.getInstanceAsync(config, object : Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                loanList =
                    realm.where<RealmLoanDetails4>().containsValue("_id", objectId).findFirst()

                loanList?.let { setupRecyclerView(it.endDateList) }
            }
        })
    }
}