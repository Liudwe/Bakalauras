/*
package io.unlokk.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.intern.databinding.AdapterActiveLoansItemBinding
import io.unlokk.onboarding.entities.LoanIssuanceDetailsAdapterItem
import com.example.intern.databinding.AdapterLoanIssuanceDetailsItemBinding
import io.unlokk.onboarding.entities.ActiveLoanDetailsAdapterItem
import io.unlokk.onboarding.fragments.DashboardFragment
class ActiveLoansAdapter(
    private val context: DashboardFragment,
    private var activeLoanList: List<ActiveLoanDetailsAdapterItem> = emptyList()
) : RecyclerView.Adapter<ActiveLoansAdapter.ViewHolder>() {
    inner class ViewHolder(bind: AdapterActiveLoansItemBinding) :
        RecyclerView.ViewHolder(bind.root) {
        var binding: AdapterActiveLoansItemBinding = bind
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(AdapterActiveLoansItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = activeLoanList[position]
        with(holder.binding) {
            loanAmount.text = item.fullPayment
            repaidLoan.text = item.repaidLoan
            nextPayment.text = item.nextPaymentData
            payment.text = item.nextPaymentValue
        }
    }

    override fun getItemCount(): Int {
        return activeLoanList.size
    }

    fun updateList(
        items: List<ActiveLoanDetailsAdapterItem>
    ) {
        activeLoanList = items
        notifyDataSetChanged()
    }
}
*/
