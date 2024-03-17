package com.nalldev.gxsales.presentation.add_edit_lead.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.nalldev.gxsales.R
import com.nalldev.gxsales.core.base.BaseFragment
import com.nalldev.gxsales.core.viewmodel.FormViewModel
import com.nalldev.gxsales.databinding.FragmentLeadOfficeBinding
import com.nalldev.gxsales.presentation.add_edit_lead.viewmodel.AddEditLeadViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LeadOfficeFragment : BaseFragment<FragmentLeadOfficeBinding>() {

    private val viewModel by activityViewModels<AddEditLeadViewModel>()
    private val formViewModel by activityViewModels<FormViewModel>()

    private val typeArrayAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            arrayListOf<String>()
        )
    }

    private val channelArrayAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            arrayListOf<String>()
        )
    }

    private val mediaArrayAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            arrayListOf<String>()
        )
    }

    private val sourceArrayAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            arrayListOf<String>()
        )
    }

    private val statusArrayAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            arrayListOf<String>()
        )
    }

    private val probabilityArrayAdapter by lazy {
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            arrayListOf<String>()
        )
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLeadOfficeBinding {
        return FragmentLeadOfficeBinding.inflate(inflater, container, false)
    }

    override fun setupObserver() : Unit = with(viewModel) {
        formViewModel.apply {
            listTypes.observe(viewLifecycleOwner) { data ->
                val listType = data.map{ it.name }
                typeArrayAdapter.let {
                    it.clear()
                    it.addAll(listType)
                    it.notifyDataSetChanged()
                }
            }

            listChannel.observe(viewLifecycleOwner) { data ->
                val listChannel = data.map{ it.name }
                channelArrayAdapter.let {
                    it.clear()
                    it.addAll(listChannel)
                    it.notifyDataSetChanged()
                }
            }

            listMedia.observe(viewLifecycleOwner) { data ->
                val listMedia = data.map{ it.name }
                mediaArrayAdapter.let {
                    it.clear()
                    it.addAll(listMedia)
                    it.notifyDataSetChanged()
                }
            }

            listSource.observe(viewLifecycleOwner) { data ->
                val listSource = data.map{ it.name }
                sourceArrayAdapter.let {
                    it.clear()
                    it.addAll(listSource)
                    it.notifyDataSetChanged()
                }
            }

            listStatus.observe(viewLifecycleOwner) { data ->
                val listStatus = data.map{ it.name }
                statusArrayAdapter.let {
                    it.clear()
                    it.addAll(listStatus)
                    it.notifyDataSetChanged()
                }
            }

            listProbabilities.observe(viewLifecycleOwner) { data ->
                val listProbability = data.map{ it.name }
                probabilityArrayAdapter.let {
                    it.clear()
                    it.addAll(listProbability)
                    it.notifyDataSetChanged()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    isLeadMandatoryFilled.collect{ isFilled ->
                        stateSubmitButton(isFilled)
                    }
                }
            }
        }
    }

    private fun stateSubmitButton(filled: Boolean) = with(binding.btnSubmit) {
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

    override fun setupUI() = with(binding) {
        tvType.setAdapter(typeArrayAdapter)
        tvChannel.setAdapter(channelArrayAdapter)
        tvMedia.setAdapter(mediaArrayAdapter)
        tvSource.setAdapter(sourceArrayAdapter)
        tvStatus.setAdapter(statusArrayAdapter)
        tvProbability.setAdapter(probabilityArrayAdapter)
    }

    override fun setupListeners() = with(binding) {
        tvType.setOnItemClickListener { _, _, _, _ ->
            val typeId = formViewModel.listTypes.value?.find {
                it.name.contains(tvType.text.toString(), true)
            }

            typeId?.let { item ->
                viewModel.setTypeId(item.id.toString())
            }
        }

        tvChannel.setOnItemClickListener { _, _, _, _ ->
            val channelId = formViewModel.listChannel.value?.find {
                it.name.contains(tvChannel.text.toString(), true)
            }

            channelId?.let { item ->
                viewModel.setChannelId(item.id.toString())
            }
        }

        tvMedia.setOnItemClickListener { _, _, _, _ ->
            val mediaId = formViewModel.listMedia.value?.find {
                it.name.contains(tvMedia.text.toString(), true)
            }

            mediaId?.let { item ->
                viewModel.setMediaId(item.id.toString())
            }
        }

        tvSource.setOnItemClickListener { _, _, _, _ ->
            val sourceId = formViewModel.listSource.value?.find {
                it.name.contains(tvSource.text.toString(), true)
            }

            sourceId?.let { item ->
                viewModel.setSourceId(item.id.toString())
            }
        }

        tvStatus.setOnItemClickListener { _, _, _, _ ->
            val statusId = formViewModel.listStatus.value?.find {
                it.name.contains(tvStatus.text.toString(), true)
            }

            statusId?.let { item ->
                viewModel.setStatusId(item.id.toString())
            }
        }

        tvProbability.setOnItemClickListener { _, _, _, _ ->
            val probabilityId = formViewModel.listProbabilities.value?.find {
                it.name.contains(tvProbability.text.toString(), true)
            }

            probabilityId?.let { item ->
                viewModel.setProbabilityId(item.id.toString())
            }
        }

        tvNotes.doOnTextChanged { text, _, _, _ ->
            viewModel.setGeneralNotes(text.toString())
        }

        btnPrev.setOnClickListener {
            findNavController().popBackStack()
        }

        btnSubmit.setOnClickListener {
            viewModel.createLead()
        }
    }

}