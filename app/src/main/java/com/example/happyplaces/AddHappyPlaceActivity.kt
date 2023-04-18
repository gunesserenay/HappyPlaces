package com.example.happyplaces

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.UiAutomation.OnAccessibilityEventListener
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.Gallery
import android.widget.Toast
import com.example.happyplaces.databinding.ActivityAddHappyPlaceBinding
import com.example.happyplaces.databinding.ActivityMainBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.sql.RowSetListener

class AddHappyPlaceActivity : AppCompatActivity(), View.OnClickListener {
    private var binding: ActivityAddHappyPlaceBinding? = null
    companion object{
        private const val GALLERY=1
    }

    private var cal = Calendar.getInstance()
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddHappyPlaceBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarAddPlace)
        //for back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.toolbarAddPlace?.setNavigationOnClickListener {
            onBackPressed()
        }

        dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateInView()
        }
        binding?.etDate?.setOnClickListener(this)
        binding?.tvAddImage?.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.et_date -> {
                DatePickerDialog(
                    this@AddHappyPlaceActivity, dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            R.id.tv_add_image -> {
                val pictureDialog = AlertDialog.Builder(this)
                pictureDialog.setTitle("Select Action")
                val pictureDiaologItems =
                    arrayOf("Select from gallery", "Capture photo from gallery")
                pictureDialog.setItems(pictureDiaologItems) { diaolog, which ->
                    when (which) {
                        0 -> choosePhotoFromGallery()
                        1 -> Toast.makeText(
                            this@AddHappyPlaceActivity,
                            "Camera selection coming soon...",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                pictureDialog.show()
            }
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode== Activity.RESULT_OK){
            if (requestCode== GALLERY){
                if (data!=null){
                    val contentURI=data.data
                    try {
                        val selectedImageBitmap=MediaStore.Images.Media.getBitmap(this.contentResolver,contentURI)
                        binding?.ivPlaceImage?.setImageBitmap(selectedImageBitmap)
                    }catch (e:IOException){
                        e.printStackTrace()
                        Toast.makeText(this@AddHappyPlaceActivity,"failed to load bitmap image from the gallery",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    private fun choosePhotoFromGallery() {

        Dexter.withActivity(this).withPermissions(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                if (report!!.areAllPermissionsGranted()) {
                    val galleryIntent=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(galleryIntent,GALLERY)
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>?,
                token: PermissionToken?
            ) {
                showRationalDiaologForPermission()
            }
        }).onSameThread().check()
    }

    private fun showRationalDiaologForPermission() {
        AlertDialog.Builder(this).setMessage("It looks like you have turned off permission")
            .setPositiveButton("Go to setting") { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun updateDateInView() {
        val myFormat = "dd.MM.yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        binding?.etDate?.setText(sdf.format(cal.time).toString())
    }

}