package com.mira.mira.view.results

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mira.mira.databinding.FragmentResultsBinding
import com.mira.mira.view.adapter.ResultsAdapter

class ResultsFragment : Fragment() {

    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ResultsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultsBinding.inflate(inflater, container, false)

        val token = retrieveTokenFromSharedPreferences()

        viewModel = ViewModelProvider(this, ResultsViewModelFactory(token)).get(ResultsViewModel::class.java)

        val adapter = ResultsAdapter(emptyList())
        binding.rcListresult.layoutManager = LinearLayoutManager(context)
        binding.rcListresult.adapter = adapter

        viewModel.results.observe(viewLifecycleOwner) { results ->
            adapter.updateData(results)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun retrieveTokenFromSharedPreferences(): String {
        val sharedPreferences = requireContext().getSharedPreferences("user_session", MODE_PRIVATE)
        return sharedPreferences.getString("auth_token", "") ?: ""
    }

    class ResultsViewModelFactory(private val token: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ResultsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ResultsViewModel(token) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}
