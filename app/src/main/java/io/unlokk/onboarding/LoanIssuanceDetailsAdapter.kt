package io.unlokk.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.unlokk.onboarding.entities.LoanIssuanceDetailsAdapterItem
import com.example.intern.databinding.AdapterLoanIssuanceDetailsItemBinding
import io.unlokk.onboarding.fragments.ApplyLoanFragment

class LoanIssuanceDetailsAdapter(
    private val context: ApplyLoanFragment, // Kuriame fragment'e/activity naudoju
    private var loanIssuanceList: List<LoanIssuanceDetailsAdapterItem> = emptyList()
) : RecyclerView.Adapter<LoanIssuanceDetailsAdapter.ViewHolder>() {
    inner class ViewHolder(bind: AdapterLoanIssuanceDetailsItemBinding) :
        RecyclerView.ViewHolder(bind.root) {
        var binding: AdapterLoanIssuanceDetailsItemBinding = bind
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(AdapterLoanIssuanceDetailsItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = loanIssuanceList[position]
        with(holder.binding) {
            titleTextView.text = item.title
            valueTextView.text = item.value
        }
    }

    override fun getItemCount(): Int {
        return loanIssuanceList.size
    }

    fun updateList(
        items: List<LoanIssuanceDetailsAdapterItem>
    ) {
        loanIssuanceList = items
        notifyDataSetChanged()
    }
}