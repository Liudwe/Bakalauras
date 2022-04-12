package io.unlokk.onboarding.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.intern.databinding.FragmentDashboardBinding
import com.mongodb.ConnectionString
import com.mongodb.MongoClient
import com.mongodb.MongoClientSettings
import com.mongodb.MongoClientURI
import com.mongodb.client.MongoClients
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardFragment : Fragment() {

/*    private lateinit var recyclerAdapter: ActiveLoansAdapter*/
    private lateinit var binding: FragmentDashboardBinding
/*    private val activeLoansViewModel: MainViewModel by activityViewModels()*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val view = binding.root
        /*recyclerAdapter = LoanIssuanceDetailsAdapter(this)*/
        /*setupRecyclerView()*/
        return view
    }

/*    private fun setupRecyclerView() {
        binding.recyclerView.setDivider(R.drawable.recycler_view_divider)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        binding.recyclerView.adapter = recyclerAdapter
        binding.recyclerView.setHasFixedSize(true)
    }*/

}