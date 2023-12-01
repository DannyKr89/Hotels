package com.dk.hotels.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dk.hotels.R
import com.dk.hotels.data.model.room.Room
import com.dk.hotels.data.model.room.Rooms
import com.dk.hotels.databinding.ItemRoomBinding

class RoomsAdapter(private val adapter: ImageAdapter) :
    RecyclerView.Adapter<RoomsAdapter.RoomsViewHolder>() {


    private val rooms = mutableListOf<Room>()

    fun setData(list: Rooms) {
        rooms.clear()
        rooms.addAll(list.rooms)
        notifyDataSetChanged()
    }

    inner class RoomsViewHolder(private val binding: ItemRoomBinding) : ViewHolder(binding.root) {
        fun bind(room: Room) {
            binding.apply {
                setupRecyclerView(room)
                tvRoomName.text = room.name
                tvHotelMinimalPrice.text = room.price.toString()
                tvHotelPriceForIt.text = room.pricePer
            }
        }

        private fun setupRecyclerView(room: Room) {
            binding.apply {
                rvRoomImages.onFlingListener = null
                PagerSnapHelper().also {
                    it.attachToRecyclerView(rvRoomImages)
                }
                adapter.setData(room.imageUrls)
                rvRoomImages.adapter = adapter

                rvRoomImages.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsViewHolder {
        val binding = ItemRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoomsViewHolder(binding)
    }

    override fun getItemCount() = rooms.size

    override fun onBindViewHolder(holder: RoomsViewHolder, position: Int) {
        holder.bind(rooms[position])
    }

}