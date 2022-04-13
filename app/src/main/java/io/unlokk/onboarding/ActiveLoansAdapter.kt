package io.unlokk.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.intern.R
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import io.unlokk.onboarding.entities.RealmLoanDetails
import java.text.SimpleDateFormat
import java.util.*

class ActiveLoansAdapter(data: OrderedRealmCollection<RealmLoanDetails>) : RealmRecyclerViewAdapter<RealmLoanDetails, ActiveLoansAdapter.DetailsViewHolder>(data, true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_active_loans_item, parent, false)
        return DetailsViewHolder(view)

    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        val details = getItem(position)
        if (details != null) {
            holder.bindValues(details)
        }
    }

    inner class DetailsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var fullLoan: TextView = view.findViewById(R.id.loan_amount)
        var paidLoan: TextView = view.findViewById(R.id.repaid_loan)
        var date: TextView = view.findViewById(R.id.next_payment)
        var payment: TextView = view.findViewById(R.id.payment)

        fun bindValues(realmLoanDetails: RealmLoanDetails){
            fullLoan.text = realmLoanDetails.fullLoan.toString()
            paidLoan.text = realmLoanDetails.loanPaid.toString()
            val dateTemp = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH).parse(realmLoanDetails.date.toString())
            val formattedDateTemp = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(dateTemp)
            date.text = formattedDateTemp
            payment.text = realmLoanDetails.nextLoanPayment.toString()
        }
    }

}





