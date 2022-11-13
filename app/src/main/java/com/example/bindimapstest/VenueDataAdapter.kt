package com.example.bindimapstest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bindimapstest.databinding.VenueItemBinding

class VenueDataAdapter(private val dataSet: List<VenueDwellInfoData>) :
    RecyclerView.Adapter<VenueDataAdapter.ViewHolder>() {

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item

        val itemBinding =
            VenueItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(itemBinding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val data = dataSet[position]
        holder.bind(data)
    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    class ViewHolder(private val binding: VenueItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: VenueDwellInfoData) {
            binding.venue.text = data.venue
            binding.venueDwellTime.text = data.dwellTimeUtc
        }
    }
}