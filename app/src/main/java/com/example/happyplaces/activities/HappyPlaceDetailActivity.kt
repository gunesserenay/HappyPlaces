package com.example.happyplaces.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.happyplaces.databinding.ActivityHappyPlaceDetailBinding

class HappyPlaceDetailActivity : AppCompatActivity() {
    private var binding:ActivityHappyPlaceDetailBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHappyPlaceDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}