package com.example.happyplaces.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.happyplaces.database.DatabaseHandler
import com.example.happyplaces.databinding.ActivityMainBinding
import com.example.happyplaces.models.HappyPlaceModel

class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.fabHappyPlace?.setOnClickListener{
            val intent=Intent(this, AddHappyPlaceActivity::class.java)
            startActivity(intent)
        }
getHappyPlacesListFromLocalDB()
    }

    private fun getHappyPlacesListFromLocalDB(){
        val dbHandler=DatabaseHandler(this)
        val getHappyPlaceList:ArrayList<HappyPlaceModel> =dbHandler.getHappyPlaces()

        for (i in getHappyPlaceList){
            Log.e("Title",i.title)
            Log.e("Title",i.description)
            Log.e("Title",i.date)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }
}