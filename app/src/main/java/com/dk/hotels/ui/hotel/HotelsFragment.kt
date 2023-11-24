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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.dk.hotels.R
import com.dk.hotels.data.model.Hotel
import com.dk.hotels.databinding.FragmentHotelsBinding
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
                StringBuilder().append(hotel.rating).append(" ").append(hotel.rating_name)
            tvHotelName.text = hotel.name
            tvHotelAddress.text = hotel.adress
            tvHotelAddress.setOnClickListener {
                Snackbar.make(requireContext(), root, "Это кнопка)", Snackbar.LENGTH_SHORT).show()
            }
            tvHotelDescription.text = hotel.about_the_hotel.description
            val numberFormat = NumberFormat.getInstance(Locale("RU"))
            tvHotelMinimalPrice.text =
                StringBuilder().append("от ").append(numberFormat.format(hotel.minimal_price))
                    .append(" ₽")
            tvHotelPriceForIt.text = hotel.price_for_it

            setupChips(hotel)

            setupDots(hotel)

        }

    }

    private fun setupDots(hotel: Hotel) {

        for (i in hotel.image_urls.indices) {
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
        hotel.about_the_hotel.peculiarities.forEach {
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
            PagerSnapHelper().also {
                it.attachToRecyclerView(rvHotelImages)
            }
            adapter.setData(hotel.image_urls)
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