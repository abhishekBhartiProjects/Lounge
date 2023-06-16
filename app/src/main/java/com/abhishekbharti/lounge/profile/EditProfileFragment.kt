package com.abhishekbharti.lounge.profile

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.abhishekbharti.lounge.R
import com.abhishekbharti.lounge.common.CommonUtil
import com.abhishekbharti.lounge.common.DexterUtil
import com.abhishekbharti.lounge.common.FileUtils
import com.abhishekbharti.lounge.community.CommunityActivity
import com.abhishekbharti.lounge.databinding.EditProfileFragmentBinding.inflate
import com.abhishekbharti.lounge.databinding.EditProfileFragmentBinding
import com.abhishekbharti.lounge.network.RequestResult
import com.abhishekbharti.lounge.response.FeedPostResponse
import com.abhishekbharti.lounge.response.ProfileResponse
import com.karumi.dexter.PermissionToken
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class EditProfileFragment: Fragment() {
    var mBinding: EditProfileFragmentBinding? = null
    private val viewModel: EditProfileViewModel by viewModels()
    private var floatingBottomSheetDialog: FloatingBottomSheetDialog? = null

    private var imageUri: Uri? = null
    private var bitmap: Bitmap? = null
    private var imageFromGallery = false
    private var mSource: String = ""

    companion object {
        fun newInstance(bundle: Bundle): EditProfileFragment = EditProfileFragment().apply {
            arguments = bundle
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBundle()
        initViewModel()
        initView()
    }

    private fun initBundle(){
        arguments?.let {
            if(it.containsKey("source")){
                mSource = it.getString("source") ?:""
            }
        }
    }

    private fun initViewModel(){
        viewModel.updateProfileResponseMLD.observe(viewLifecycleOwner){
            when(it){
                is RequestResult.Loading -> {}
                is RequestResult.Success -> {
                    onUpdateProfileSuccess(it.data as ProfileResponse)
                }
                else -> {}
            }
        }
    }

    private fun initView(){
        mBinding?.apply {
            addPhoto.setOnClickListener {
                showChooser()
            }

            nextBtn.setOnClickListener {
                if(!nameEt.getText().isNullOrEmpty()){
                    var imageBitmap: File? = null
                    if (imageUri != null) {
                        imageBitmap = File(imageUri?.path)
                    } else {
                        bitmap?.let {
                            imageBitmap = getFromBitmap(it, "avatar")
                        }
                    }

                    updateProfile(nameEt.getText()!!, imageBitmap)
                } else {
                    Toast.makeText(requireContext(), "Enter mandatory details", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showChooser() {
        if (activity != null && isAdded) {
            val pictureDialogItems =
                arrayListOf<Any>(
                    "Select from Gallery",
                    "Select from Camera"
                )
            showFloatingBottomSheetDialog(
                R.layout.bs_dialog_media,
                pictureDialogItems,
                layoutInflater,
                requireActivity()
            ) { it, p ->
                if (activity != null && isAdded) {
                    when (p) {
                        0 -> {
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                                DexterUtil.with(
                                    requireActivity(),
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                                )
                                    .setListener(object : DexterUtil.DexterUtilListener {
                                        override fun permissionGranted() {
                                            choosePhotoFromGallery()
                                        }

                                        override fun permissionDenied(
                                            token: PermissionToken?,
                                            isPermissionDeniedPermanently: Boolean
                                        ) {
                                            Toast.makeText(requireContext(),"Please provide permission to access files", Toast.LENGTH_SHORT).show()
                                        }
                                    }).check()
                            }else {
                                openPhotoPicker()
                            }
                        }
                        1 -> {
                            takePhotoFromCamera()
                        }
                    }
                }
                dismissFloatingBottomSheetDialog()
            }
        }
    }

    private fun openPhotoPicker() {
        val intent = Intent(MediaStore.ACTION_PICK_IMAGES).apply {
            type = "image/*"
        }
        photoPickerResultData.launch(intent)
    }

    @Throws(NullPointerException::class)
    private fun getFromBitmap(bitmap: Bitmap, filename: String): File {
        //create a file to write bitmap data
        val f = File(FileUtils.getAppsFileDirectory(requireContext()), "\$filename.jpeg")
        try {
            f.createNewFile()
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos)
            val bitmapData = bos.toByteArray()

            val fos = FileOutputStream(f)
            fos.write(bitmapData)
            fos.flush()
            fos.close()

        } catch (e: IOException) {
//            DebugLogger.e(TAG, "$e Error creating file")
        }

        return f
    }

    private fun updateProfile(name: String, profileImage: File?){
        var imageBody: MultipartBody.Part? = null

        profileImage?.let {
            val reqFile = profileImage.asRequestBody("image/*".toMediaTypeOrNull())
            imageBody = MultipartBody.Part.createFormData("avatar", profileImage.name, reqFile)
        }
        val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
        viewModel.updateProfile(nameBody, imageBody)
    }

    private fun onUpdateProfileSuccess(profileResponse: ProfileResponse){
        Toast.makeText(requireContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show()

        if(mSource.equals("onboarding")){
            startActivity(Intent(requireContext(), CommunityActivity::class.java))
            activity?.finish()
        }
    }

    fun showFloatingBottomSheetDialog(
        layout: Int,
        pictureDialogItems: ArrayList<Any>,
        layoutInflater: LayoutInflater,
        context: Context,
        listener: (Any, Int) -> Unit): FloatingBottomSheetDialog? {
        floatingBottomSheetDialog = FloatingBottomSheetDialog(layout, pictureDialogItems, layoutInflater, context) { item, position ->
            listener(item, position)
        }
        floatingBottomSheetDialog?.show()
        return floatingBottomSheetDialog
    }

    fun dismissFloatingBottomSheetDialog() {
        if (floatingBottomSheetDialog != null) {
            floatingBottomSheetDialog?.dismiss()
            floatingBottomSheetDialog = null
        }
    }

    private val photoResultData = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        if (it.resultCode == Activity.RESULT_OK) {
            val data = it.data
            if (data != null) {
                imageUri = data.data
                imageFromGallery = true
                if (imageUri == null || imageUri.toString().isEmpty()) {
                    return@registerForActivityResult
                }
//                try {
//                    CropImage.activity(imageUri).setFixAspectRatio(true)
//                        .setBackgroundColor(R.attr.colorPrimary)
//                        .setMaxZoom(4).setGuidelines(CropImageView.Guidelines.OFF)
//                        .setAutoZoomEnabled(true)
//                        .setAllowFlipping(false).start(activity)
//                } catch (e: Exception) {
//                    Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
//                }
            }
        }
    }

    private val photoPickerResultData = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        var mActivity: Activity? = null

        if (it.resultCode == Activity.RESULT_OK) {
            val data = it.data
            if (data != null) {
                imageUri = data.data
                imageFromGallery = true
                if (imageUri == null || imageUri.toString().isEmpty()) {
                    return@registerForActivityResult
                }
//                try {
//                    CropImage.activity(imageUri).setFixAspectRatio(true)
//                        .setBackgroundColor(R.attr.colorPrimary)
//                        .setMaxZoom(4).setGuidelines(CropImageView.Guidelines.OFF)
//                        .setAutoZoomEnabled(true)
//                        .setAllowFlipping(false).start(mActivity!!)
//                    sendEvent(EventConstants.COMPLETE_PROFILE_IMAGE_CROP_VIEWED)
//                } catch (e: Exception) {
//                    showToast(getString(R.string.something_went_wrong), Toast.LENGTH_SHORT)
//                }
            }
        }
    }

    fun choosePhotoFromGallery() {
        val galleryIntent = Intent(
            MediaStore.ACTION_PICK_IMAGES
        ).apply {
            type = "image/*"
        }

        try {
            photoResultData.launch(galleryIntent)
        } catch (e: ActivityNotFoundException) {
            val getIntent = Intent(Intent.ACTION_GET_CONTENT)
            getIntent.type = "image/*"
            val chooserIntent = Intent.createChooser(getIntent, "Select Image")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(galleryIntent))

            photoResultData.launch(chooserIntent)
        }
    }

    private fun takePhotoFromCamera() {
        DexterUtil.with(requireActivity(), Manifest.permission.CAMERA)
            .setListener(object : DexterUtil.DexterUtilListener {
                override fun permissionGranted() {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    // Ensure that there's a camera activity to handle the intent
                    if (activity != null && intent.resolveActivity(requireActivity().packageManager) != null) {
                        // Create the File where the photo should go
                        var photoFile: File? = null
                        try {
                            photoFile = CommonUtil.createImageFile(requireActivity())
                        } catch (ex: IOException) {
                            // Error occurred while creating the File
                            if (activity != null && isAdded) {
                                Toast.makeText(requireContext(), "Error occurred while creating camera file", Toast.LENGTH_SHORT).show()
                            }
                            return
                        }

                        // Continue only if the File was successfully created
                        if (photoFile != null) {
                            imageUri = FileProvider.getUriForFile(
                                requireActivity(),
                                "com.abhishekbharti.lounge.provider",
                                photoFile
                            )
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                            cameraResultData.launch(intent)
                        } else {
                            if (activity != null && isAdded) {
                                Toast.makeText(requireContext(),"Camera picture not available", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        if (activity != null && isAdded) {
                            Toast.makeText(requireContext(),"Camera picture not available", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun permissionDenied(token: PermissionToken?, isPermissionDeniedPermanently: Boolean) {
                    Toast.makeText(requireContext(),"Please provide permission to take photos", Toast.LENGTH_SHORT).show()
                }
            }).check()
    }

    private val cameraResultData = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//        var mActivity: Activity? = null

        if (it.resultCode == Activity.RESULT_OK) {
            val data = it.data
            if (imageUri == null || imageUri.toString().isEmpty()) {
                return@registerForActivityResult
            }

            imageFromGallery = false
//            try {
//                CropImage.activity(imageUri).setFixAspectRatio(true).setMaxZoom(4)
//                    .setGuidelines(CropImageView.Guidelines.OFF)
//                    .setAutoZoomEnabled(true).setAllowFlipping(false).start(mActivity!!)
//                imageFromGallery = false
//                sendEvent(EventConstants.COMPLETE_PROFILE_IMAGE_CROP_VIEWED)
//            } catch (e: Exception) {
//                showToast(getString(R.string.something_went_wrong), Toast.LENGTH_SHORT)
//            }
        }
    }


}