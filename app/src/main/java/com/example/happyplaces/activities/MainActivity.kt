package com.example.happyplaces.activities

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.happyplaces.adapters.HappyPlacesAdapter
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

    private fun setupHappyPlacesRecyclerView(happyPlacesList:ArrayList<HappyPlaceModel>){
    binding?.rvHappyPlacesList?.layoutManager=LinearLayoutManager(this)
        binding?.rvHappyPlacesList?.setHasFixedSize(true)
        val placesAdapter=HappyPlacesAdapter(this,happyPlacesList)
        binding?.rvHappyPlacesList?.adapter=placesAdapter
    }
    private fun getHappyPlacesListFromLocalDB(){
        val dbHandler=DatabaseHandler(this)
        val getHappyPlaceList:ArrayList<HappyPlaceModel> =dbHandler.getHappyPlaces()

//        for (i in getHappyPlaceList){
//            Log.e("Title",i.title)
//            Log.e("Title",i.description)
//            Log.e("Title",i.date)
//        }

        if (getHappyPlaceList.size>0){
            binding?.rvHappyPlacesList?.visibility=View.VISIBLE
            binding?.tvNoRecords?.visibility=View.GONE
            setupHappyPlacesRecyclerView(getHappyPlaceList)
        }else{
            binding?.rvHappyPlacesList?.visibility=View.GONE
            binding?.tvNoRecords?.visibility=View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }
}