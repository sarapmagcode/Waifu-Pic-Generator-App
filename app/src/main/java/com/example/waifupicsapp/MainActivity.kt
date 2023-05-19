package com.example.waifupicsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.waifupicsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View binding in activity
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}