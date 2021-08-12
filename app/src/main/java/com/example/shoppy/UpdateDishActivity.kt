package com.example.shoppy

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppy.databinding.ActivityUpdateDishBinding
import com.example.shoppy.databinding.CustomDialogBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.util.*


class UpdateDishActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mBinding: ActivityUpdateDishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityUpdateDishBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupActionBar()

        mBinding.ivAddDishImage.setOnClickListener(this@UpdateDishActivity)
    }

    override fun onClick(v: View) {

        when (v.id) {

            R.id.iv_add_dish_image -> {

                // TODO Step 6: Replace the Toast Message with the custom dialog.
                // START
                customImageSelectionDialog()


                // END
                return
            }
        }
    }


    /**
     * A function for ActionBar setup.
     */
    private fun setupActionBar() {
        setSupportActionBar(mBinding.toolbarAddDishActivity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        mBinding.toolbarAddDishActivity?.setNavigationOnClickListener { onBackPressed() }
    }


    // TODO Step 5: Create a function to launch the custom dialog.
    // START
    /**
     * A function to launch the custom image selection dialog.
     */
    private fun customImageSelectionDialog() {
        val dialog = Dialog(this@UpdateDishActivity)

        val binding: CustomDialogBinding = CustomDialogBinding.inflate(layoutInflater
        )

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        dialog.setContentView(binding.root)

        // TODO Step 7: Assign the click for Camera and Gallery. Show the Toast message for now.
        // START
        binding.tvCamera.setOnClickListener {
            Toast.makeText(this@UpdateDishActivity, "You have clicked on the Camera.", Toast.LENGTH_SHORT).show()
            Dexter.withContext(this@UpdateDishActivity)
                    .withPermission(Manifest.permission.CAMERA)
                    .withListener(object : PermissionListener {
                        override fun onPermissionGranted(response: PermissionGrantedResponse) { /* ... */
                            val intent =Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                            @Suppress("DEPRECATION")
                            startActivityForResult(intent,1)
                        }

                        override fun onPermissionDenied(response: PermissionDeniedResponse) { /* ... */
                        }

                        override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) { /* ... */
                        }
                    }).check()
            dialog.dismiss()

        }

        binding.tvGallery.setOnClickListener {
            Toast.makeText(this@UpdateDishActivity, "You have clicked on the Gallery.", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        // END

        //Start the dialog and display it on screen.
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        @Suppress("DEPRECATION")
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==Activity.RESULT_OK)
            if(requestCode==1){
                val thumbnail :Bitmap=data?.extras?.get("data") as Bitmap
                mBinding.ivDishImage.setImageBitmap(thumbnail)
                mBinding.ivAddDishImage.setImageResource(R.drawable.ic_edit)

            }
    }
    // END
}