package com.example.apollographqlmutationdemo.ui.tripAction

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.apollographql.apollo.api.Response
import com.example.apollographqldemozz.BookTripMutation
import com.example.apollographqldemozz.CancelTripMutation
import com.example.apollographqldemozz.TotalBookedTripsQuery
import com.example.apollographqlmutationdemo.data.model.state.ViewState
import com.example.apollographqlmutationdemo.databinding.FragmentTripActionBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TripActionFragment @Inject constructor() : Fragment() {
    private lateinit var binding : FragmentTripActionBinding
    private val viewModel by viewModels<TripActionViewModel>()
//    val args: TripActionFragmentArgs by navArgs()  //to receive token String from LoginFragment
//    var tokenArray: List<String> = args.token.toCharArray().map { it.toString() }.toList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentTripActionBinding.inflate(layoutInflater)

            binding.btnBookTrip.setOnClickListener {
                val userInput = binding.etInput.text.toString()
                val list : ArrayList<String> = ArrayList()
                list.add(userInput)
//                val list : List<String> = userInput.toList()
//                val userInputArray: List<String> = userInput.toCharArray().map { it.toString() }.toList()
                if (binding.etInput.text.isNullOrEmpty()){
                    Toast.makeText(requireContext(), "Please enter something first!", Toast.LENGTH_SHORT).show()
                }else {
                    viewModel.getBookTripResult(list, requireContext())
                }
            }

            binding.btnCancelTrip.setOnClickListener {
                val userInput = binding.etInput.text.toString()
                if (binding.etInput.text.isNullOrEmpty()){
                    Toast.makeText(requireContext(), "Please enter something first!", Toast.LENGTH_SHORT).show()
                }else {
                    viewModel.getCancelTripResult(userInput, requireContext())
                }
            }

            binding.tvTotalTrips.setOnClickListener {
                viewModel.getTotalBookedTripResult(requireContext())
            }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.bookTripResult.observe(viewLifecycleOwner, ::handleBookTripResult)
        viewModel.cancelTripResult.observe(viewLifecycleOwner, ::handleCancelTripResult)
        viewModel.totalBookedTripResult.observe(viewLifecycleOwner, ::handleTotalBookedTripResult)
    }

    private fun handleBookTripResult(viewState: ViewState<Response<BookTripMutation.Data>>?) {
        Toast.makeText(requireContext(), viewState?.value?.data?.bookTrips?.message, Toast.LENGTH_SHORT).show()
    }

    private fun handleCancelTripResult(viewState: ViewState<Response<CancelTripMutation.Data>>?) {
        Toast.makeText(requireContext(), viewState?.value?.data?.cancelTrip?.message, Toast.LENGTH_SHORT).show()
    }

    private fun handleTotalBookedTripResult(viewState: ViewState<Response<TotalBookedTripsQuery.Data>>?) {
        Toast.makeText(requireContext(), viewState?.value?.data?.totalTripsBooked?.toString(), Toast.LENGTH_SHORT).show()
    }
}