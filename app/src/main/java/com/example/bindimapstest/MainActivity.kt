package com.example.bindimapstest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bindimapstest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private lateinit var venueAdapter: VenueDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Create the observer which updates the UI.
        val venueDwellTimeObserver = Observer<List<VenueDwellInfoData>> { venuesDwellTime ->

            // pass it to recyclerview layoutManager
            binding.recyclerView.layoutManager = LinearLayoutManager(this)

            // initialize the adapter
            venueAdapter = VenueDataAdapter(venuesDwellTime)

            //bind the adapter
            binding.recyclerView.adapter = venueAdapter
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewModel.venueDwellInfoLiveData.observe(this, venueDwellTimeObserver)
    }
}