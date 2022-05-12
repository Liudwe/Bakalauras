package io.unlokk.onboarding

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.intern.databinding.AdapterLoanPaymentEndDatesItemBinding
import io.realm.RealmList
import io.unlokk.onboarding.data.LoanPaymentInfo2
import io.unlokk.onboarding.entities.LoanIssuanceDetailsAdapterItem
import io.unlokk.onboarding.fragments.LoanPaymentDatesFragment
import java.text.SimpleDateFormat
import java.util.*

class LoanEndDatesAdapter(
    private val context: Context, // Kuriame fragment'e/activity naudoju
    private var loanPaymentInfo: List<LoanPaymentInfo2> = emptyList(),
    private val onClick: ((position: Int) -> Unit)? = null
) : RecyclerView.Adapter<LoanEndDatesAdapter.ViewHolder>() {

    inner class ViewHolder(bind: AdapterLoanPaymentEndDatesItemBinding) :
        RecyclerView.ViewHolder(bind.root) {
        var binding: AdapterLoanPaymentEndDatesItemBinding = bind

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(AdapterLoanPaymentEndDatesItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = loanPaymentInfo[position]

        val dateTemp = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH).parse(
            loanPaymentInfo[position].endDate.toString()
        )
        val formattedDateTemp = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(dateTemp)

        with(holder.binding) {
            loanPaymentEndDate.text = formattedDateTemp.toString()
            loanPaymentEndDateStatus.text = item.status
        }
        if(item.status == "Paid")
            holder.itemView.setBackgroundColor(Color.GREEN)
        else if(item.status == "Not paid" && Date().after(item.endDate))
            holder.itemView.setBackgroundColor(Color.RED)
        else
            holder.itemView.setBackgroundColor(Color.YELLOW)

        holder.itemView.setOnClickListener {
            onClick?.invoke(position)
        }
    }

    override fun getItemCount(): Int {
        return loanPaymentInfo.size
    }

    fun updateList(
        items: List<LoanPaymentInfo2>
    ) {
        loanPaymentInfo = items
        notifyDataSetChanged()
    }
}