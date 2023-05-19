package com.example.waifupicsapp.overview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.example.waifupicsapp.R
import com.example.waifupicsapp.databinding.FragmentOverviewBinding

public const val TAG = "OverviewFragment"

class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OverviewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe image view
        viewModel.waifuPic.observe(viewLifecycleOwner) {
            it.url?.let { url ->
                val imgUri = url.toUri().buildUpon().scheme("https").build()
                binding.waifuImageView.load(imgUri) {
                    placeholder(R.drawable.loading_animation)
                    error(R.drawable.ic_connection_error)
                }
            }
        }

        // Observe status
        viewModel.status.observe(viewLifecycleOwner) {
            when (it) {
                WaifuPicStatus.LOADING -> {
                    binding.statusImageView.visibility = View.VISIBLE
                    binding.statusImageView.setImageResource(R.drawable.loading_animation)
                }

                WaifuPicStatus.ERROR -> {
                    binding.statusImageView.visibility = View.VISIBLE
                    binding.statusImageView.setImageResource(R.drawable.ic_connection_error)
                }

                WaifuPicStatus.LOADING -> binding.statusImageView.visibility = View.GONE

                else -> Log.d(TAG, "Error: No status")
            }
        }

        // Generate new image
        binding.waifuButton.setOnClickListener { viewModel.generateAgain() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}