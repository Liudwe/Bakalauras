package io.unlokk.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.intern.R
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults
import io.unlokk.onboarding.entities.RealmLoanDetails4
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


class ActiveLoansAdapter(
    data: OrderedRealmCollection<RealmLoanDetails4>,
    val context: Context,
    private val onClick: ((test: String) -> Unit)? = null
) : RealmRecyclerViewAdapter<RealmLoanDetails4, ActiveLoansAdapter.DetailsViewHolder>(data, true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_active_loans_item, parent, false)
        return DetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        val obj: RealmLoanDetails4? = getItem(position)
        val objectId = obj?._id.toString()
        val details = getItem(position)

        if (details != null) {
            holder.bindValues(details)
        }

        holder.itemView.setOnClickListener {
            if (obj != null && obj.status!= "Not approved") {
                onClick?.invoke(objectId)
            }
            else if (obj != null && obj.status == "Not approved") {
                Toast.makeText(
                    context,
                    "This loan has not yet been approved.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    inner class DetailsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var fullLoan: TextView = view.findViewById(R.id.loan_amount)
        var paidLoan: TextView = view.findViewById(R.id.repaid_loan)
        var date: TextView = view.findViewById(R.id.next_payment)
        var payment: TextView = view.findViewById(R.id.payment)
        var loanStatus: TextView = view.findViewById(R.id.loan_status)
        var loanTerm: TextView = view.findViewById(R.id.loan_term)
        var loanFullAmountPayment: TextView = view.findViewById(R.id.loan_full_amount_payment)

        fun bindValues(realmLoanDetails: RealmLoanDetails4) {
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.UP
            val toDecimal = realmLoanDetails.loanPaid

            fullLoan.text = realmLoanDetails.loanTaken.toString()
            paidLoan.text = df.format(toDecimal)
            loanStatus.text = realmLoanDetails.status
            loanTerm.text = realmLoanDetails.term.toString()
            loanFullAmountPayment.text = realmLoanDetails.fullLoan.toString()
            val dateTemp = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH).parse(
                realmLoanDetails.date.toString()
            )
            val formattedDateTemp = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(dateTemp)
            date.text = formattedDateTemp
            payment.text = realmLoanDetails.nextLoanPayment.toString()
        }
    }
}





