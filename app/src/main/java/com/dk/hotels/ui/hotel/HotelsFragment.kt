package com.dk.hotels.ui.hotel

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.core.view.setMargins
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.dk.hotels.R
import com.dk.hotels.data.model.hotel.Hotel
import com.dk.hotels.databinding.FragmentHotelsBinding
import com.dk.hotels.ui.adapters.ImageAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.NumberFormat
import java.util.Locale

class HotelsFragment : Fragment(R.layout.fragment_hotels) {

    private var _binding: FragmentHotelsBinding? = null
    private val binding: FragmentHotelsBinding get() = _binding!!
    private val viewModel: HotelsViewModel by viewModel()
    private val adapter: ImageAdapter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHotelsBinding.bind(view)

        initViewModel()
    }

    private fun initViewModel() {
        viewModel.getHotels()
        viewModel.liveData.observe(viewLifecycleOwner) {
            showData(it)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showData(hotel: Hotel) {
        binding.apply {

            setupRecyclerView(hotel)

            tvHotelRating.text =
                StringBuilder().append(hotel.rating).append(" ").append(hotel.ratingName)
            tvHotelName.text = hotel.name
            tvHotelAddress.text = hotel.address
            tvHotelAddress.setOnClickListener {
                Snackbar.make(requireContext(), root, "Это кнопка)", Snackbar.LENGTH_SHORT).show()
            }
            tvHotelDescription.text = hotel.aboutTheHotel.description
            val numberFormat = NumberFormat.getInstance(Locale("RU"))
            tvHotelMinimalPrice.text =
                StringBuilder().append("от ").append(numberFormat.format(hotel.minimalPrice))
                    .append(" ₽")
            tvHotelPriceForIt.text = hotel.priceForIt

            setupChips(hotel)

            setupDots(hotel)

            btnChooseNumber.setOnClickListener {
                findNavController().navigate(
                    R.id.action_hotelsFragment_to_roomsFragment,
                    Bundle().apply {
                        putString("hotel", hotel.name)
                    })
            }

        }

    }

    private fun setupDots(hotel: Hotel) {
        binding.llDots.removeAllViews()
        for (i in hotel.imageUrls.indices) {
            val imageView = ImageView(requireContext())
            imageView.setBackgroundResource(R.drawable.ic_dot_grey)
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(12)
            imageView.layoutParams = params
            binding.llDots.addView(imageView)
        }
        binding.llDots[0].setBackgroundResource(R.drawable.ic_dot_black)
    }

    private fun setupChips(hotel: Hotel) {
        binding.cgHotelPeculiarities.removeAllViews()
        hotel.aboutTheHotel.peculiarities.forEach {
            val chip = Chip(requireContext())
            val chipDrawable =
                ChipDrawable.createFromAttributes(requireContext(), null, 0, R.style.My_Chips)
            chip.text = it
            chip.setChipDrawable(chipDrawable)
            chip.setTextColor(resources.getColor(R.color.grey, resources.newTheme()))
            binding.cgHotelPeculiarities.addView(chip)
        }
    }

    private fun setupRecyclerView(hotel: Hotel) {
        binding.apply {
            rvHotelImages.onFlingListener = null
            PagerSnapHelper().also {
                it.attachToRecyclerView(rvHotelImages)
            }
            adapter.setData(hotel.imageUrls)
            rvHotelImages.adapter = adapter

            rvHotelImages.addOnScrollListener(object : OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    val position =
                        (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    llDots.forEach {
                        it.setBackgroundResource(R.drawable.ic_dot_grey)
                    }
                    llDots[position].setBackgroundResource(R.drawable.ic_dot_black)
                }
            })
        }


    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}