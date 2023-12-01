package com.dk.hotels.ui.rooms

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.dk.hotels.R
import com.dk.hotels.data.model.room.Rooms
import com.dk.hotels.databinding.FragmentRoomsBinding
import com.dk.hotels.ui.adapters.RoomsAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class RoomsFragment : Fragment(R.layout.fragment_rooms) {

    private var _binding: FragmentRoomsBinding? = null
    private val binding: FragmentRoomsBinding get() = _binding!!

    private val viewModel: RoomsViewModel by viewModel()
    private val adapter: RoomsAdapter by inject()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRoomsBinding.bind(view)

        initViewModel()
    }

    private fun initViewModel() {
        viewModel.getRooms()
        viewModel.liveData.observe(viewLifecycleOwner) {
            showData(it)
        }
    }

    private fun showData(rooms: Rooms) {
        binding.apply {
            adapter.setData(rooms)
            rvRooms.adapter = adapter
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}