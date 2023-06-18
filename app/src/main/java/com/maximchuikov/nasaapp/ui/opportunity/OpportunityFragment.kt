package com.maximchuikov.nasaapp.ui.opportunity

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.maximchuikov.nasaapp.R
import com.maximchuikov.nasaapp.adapter.BaseAdapter
import com.maximchuikov.nasaapp.data.model.Photo
import com.maximchuikov.nasaapp.databinding.FragmentOpportunityBinding
import com.maximchuikov.nasaapp.ui.PhotoInfoDialogFragment
import com.maximchuikov.nasaapp.ui.curiosity.CuriosityFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OpportunityFragment : Fragment(), MenuProvider{
    private lateinit var binding: FragmentOpportunityBinding
    private val opportunityViewModel: OpportunityViewModel by viewModels()
    private lateinit var photoList: List<Photo>
    private var cameraList: HashSet<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOpportunityBinding.inflate(inflater)
        updateOpportunityState()
        return binding.root
    }

    private fun updateOpportunityState() {
        viewLifecycleOwner.lifecycleScope.launch {
            opportunityViewModel.getOpportunity()
            opportunityViewModel.opportunityState.collect {
                if (it.isLoading) {
                    binding.progressOpportunity.visibility = View.VISIBLE
                    binding.rvOpportunity.visibility = View.GONE
                } else if (it.photoList?.photos?.isNotEmpty() == true) {
                    binding.progressOpportunity.visibility = View.GONE
                    photoList = it.photoList.photos
                    initRecycler(it.photoList.photos)
                    cameraList = it.cameraList!!
                } else {
                    Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initRecycler(list: List<Photo>) {
        binding.rvOpportunity.apply {
            adapter = BaseAdapter(list, BaseAdapter.OnClickListener {
                val action = OpportunityFragmentDirections.actionOpportunityFragmentToPhotoInfoFragment(it)
                findNavController().navigate(action)
            })
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onPrepareMenu(menu: Menu) {
        super.onPrepareMenu(menu)
        menu.clear()
        if (cameraList.isNullOrEmpty()) {
            menu.add("all")
        } else {
            for (camera in cameraList!!) {
                menu.add(camera)
            }
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.cam_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        // Handle the menu selection
        val filter = menuItem.title
        val filterList = photoList.filter {
            it.camera.name == filter
        }
        initRecycler(filterList)
        return true
    }
}