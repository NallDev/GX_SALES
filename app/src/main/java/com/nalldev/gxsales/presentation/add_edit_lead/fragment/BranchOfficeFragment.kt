package com.nalldev.gxsales.presentation.add_edit_lead.fragment

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.nalldev.gxsales.R
import com.nalldev.gxsales.core.base.BaseFragment
import com.nalldev.gxsales.core.util.createMultipartFromUri
import com.nalldev.gxsales.core.util.getFileExtensionFromUri
import com.nalldev.gxsales.core.util.getFileSizeDescriptionFromUri
import com.nalldev.gxsales.core.util.showErrorToast
import com.nalldev.gxsales.core.util.showImagePopUp
import com.nalldev.gxsales.core.viewmodel.FormViewModel
import com.nalldev.gxsales.databinding.FragmentBranchOfficeBinding
import com.nalldev.gxsales.presentation.add_edit_lead.viewmodel.AddEditLeadViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BranchOfficeFragment : BaseFragment<FragmentBranchOfficeBinding>() {

    private val viewModel by activityViewModels<AddEditLeadViewModel>()
    private val formViewModel by activityViewModels<FormViewModel>()

    private val branchArrayAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            arrayListOf<String>()
        )
    }

    private val countryCodeArrayAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            arrayListOf<String>()
        )
    }

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                val titleFile = String.format("File-upload.%s", requireContext().getFileExtensionFromUri(uri))
                val sizeFile = requireContext().getFileSizeDescriptionFromUri(uri)
                val imageMultipart =
                    requireContext().createMultipartFromUri(uri, requireActivity().contentResolver)

                updateFileDescription(titleFile, sizeFile)
                binding.ivFileImage.setImageURI(uri)
                stateContainerFile(true)
                viewModel.setImage(imageMultipart)
            }
        }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBranchOfficeBinding {
        return FragmentBranchOfficeBinding.inflate(inflater, container, false)
    }

    override fun setupObserver(): Unit = with(viewModel) {
        formViewModel.apply {
            listBranch.observe(viewLifecycleOwner) { data ->
                val listBranch = data.map { it.name }
                branchArrayAdapter.let {
                    it.clear()
                    it.addAll(listBranch)
                    it.notifyDataSetChanged()
                }
            }

            listCountryCode.observe(viewLifecycleOwner) { data ->
                countryCodeArrayAdapter.let {
                    it.clear()
                    it.addAll(data)
                    it.notifyDataSetChanged()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    isBranchMandatoryFilled.collect { isFilled ->
                        stateNextButton(isFilled)
                    }
                }
            }
        }
    }

    private fun stateNextButton(filled: Boolean) = with(binding.btnNext) {
        if (filled) {
            isEnabled = true
            backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.yellow_btn)
        } else {
            isEnabled = false
            backgroundTintList =
                ContextCompat.getColorStateList(requireContext(), R.color.outline_stroke)
        }
    }

    private fun updateFileDescription(titleFile: String, sizeFile: String) = with(binding) {
        tvFileName.text = titleFile
        tvFileSize.text = sizeFile
    }

    private fun stateContainerFile(isFileAvailable : Boolean) = with(binding) {
        containerFile.isGone = !isFileAvailable
        containerAddImage.isGone = isFileAvailable
    }

    private fun getLastLocation() = with(binding) {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    tvLatitude.setText(it.latitude.toString())
                    tvLongitude.setText(it.longitude.toString())
                }
            }
        }
    }

    override fun setupUI(): Unit = with(binding) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        initGender()
        if (viewModel.isUpdate) {
            initData()
        }
        tvBranch.setAdapter(branchArrayAdapter)
        tvPhoneCode.setAdapter(countryCodeArrayAdapter)
    }

    private fun initData() = with(viewModel) {
        binding.apply {
            tvName.setText(fullName.value)
            tvEmail.setText(email.value)
            tvPhone.setText(phone.value)
            tvAddress.setText(address.value)
            tvLatitude.setText(latitude.value)
            tvLongitude.setText(longitude.value)
            tvIdentity.setText(idNumber.value)

            if (gender.value.equals("male", true)) {
                binding.radioMale.setChecked(true)
                binding.radioFemale.setChecked(false)
            } else {
                binding.radioMale.setChecked(false)
                binding.radioFemale.setChecked(true)
            }

            viewLifecycleOwner.lifecycleScope.launch {
                delay(300)
                val branch = formViewModel.listBranch.value?.find { item ->
                    branchOfficeId.value.contains(item.id.toString(), true)
                }
                branch?.let {
                    tvBranch.setText(it.name)
                }
            }
        }
    }

    private fun initGender() = with(binding) {
        val id: Int = rgGender.checkedRadioButtonId
        val rb: RadioButton = rgGender.findViewById(id)
        viewModel.setGender(rb.text.toString().lowercase())
    }

    @Suppress("DEPRECATION")
    override fun setupListeners() = with(binding) {
        btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_branchOfficeFragment_to_leadOfficeFragment)
        }

        tvName.doOnTextChanged { text, _, _, _ ->
            viewModel.setFullName(text.toString())
        }

        tvEmail.doOnTextChanged { text, _, _, _ ->
            viewModel.setEmail(text.toString())
        }

        tvPhone.doOnTextChanged { text, _, _, _ ->
            viewModel.setPhone("${tvPhoneCode.text}$text")
        }

        tvAddress.doOnTextChanged { text, _, _, _ ->
            viewModel.setAddress(text.toString())
        }

        tvLatitude.doOnTextChanged { text, _, _, _ ->
            viewModel.setLatitude(text.toString())
        }

        tvLongitude.doOnTextChanged { text, _, _, _ ->
            viewModel.setLongitude(text.toString())
        }

        tvIdentity.doOnTextChanged { text, _, _, _ ->
            viewModel.setIdNumber(text.toString())
        }

        tvBranch.setOnItemClickListener { _, _, _, _ ->
            val branchId = formViewModel.listBranch.value?.find {
                it.name.contains(tvBranch.text.toString(), true)
            }
            branchId?.let { item ->
                Log.e("GENDER", item.id.toString())
                viewModel.setBranchOfficeId(item.id.toString())
            }
        }

        rgGender.setOnCheckedChangeListener { _, _ ->
            val id: Int = rgGender.checkedRadioButtonId
            val rb: RadioButton = rgGender.findViewById(id)
            viewModel.setGender(rb.text.toString().lowercase())
        }

        ivDeleteFile.setOnClickListener {
            stateContainerFile(false)
            viewModel.setImage(null)
        }

        ivShowFile.setOnClickListener {
            (requireContext() as ContextWrapper).baseContext.showImagePopUp(ivFileImage.drawable)
        }

        ivEditFile.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        btnLocation.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            } else {
                if (checkEnableGps()) return@setOnClickListener
                getLastLocation()
            }
        }

        containerAddImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun checkEnableGps(): Boolean {
        val locationManager =
            requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager?

        if (!locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            requireActivity().showErrorToast("Please enable your GPS")
            return true
        }
        return false
    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (checkEnableGps()) return
                getLastLocation()
            } else {
                requireActivity().showErrorToast("Please grant the permission")
            }
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}