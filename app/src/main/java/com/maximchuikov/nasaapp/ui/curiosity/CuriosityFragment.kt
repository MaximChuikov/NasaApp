package com.maximchuikov.nasaapp.ui.curiosity

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
import com.maximchuikov.nasaapp.databinding.FragmentCuriosityBinding
import com.maximchuikov.nasaapp.ui.PhotoInfoDialogFragment
import com.maximchuikov.nasaapp.ui.spirit.SpiritFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CuriosityFragment : Fragment(), MenuProvider {
    private lateinit var binding: FragmentCuriosityBinding
    private val curiosityViewModel: CuriosityViewModel by viewModels()
    private lateinit var photoList: List<Photo>
    private var cameraList: HashSet<String>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCuriosityBinding.inflate(inflater)
        updateCuriosityState()
        return binding.root
    }

    private fun updateCuriosityState() {
        viewLifecycleOwner.lifecycleScope.launch {
            curiosityViewModel.getCuriosity()
            curiosityViewModel.curiosityState.collect { roverViewState ->
                if (roverViewState.isLoading) {
                    binding.progressCuriosity.visibility = View.VISIBLE
                    binding.rvCuriosity.visibility = View.GONE
                } else if (roverViewState.photoList?.photos?.isNotEmpty() == true) {
                    binding.progressCuriosity.visibility = View.GONE
                    photoList = roverViewState.photoList.photos
                    initRecycler(photoList)
                    cameraList = roverViewState.cameraList!!
                } else {
                    Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initRecycler(list: List<Photo>) {
        binding.rvCuriosity.apply {
            adapter = BaseAdapter(list, BaseAdapter.OnClickListener {
                val action = CuriosityFragmentDirections.actionCuriosityFragmentToPhotoInfoFragment(it)
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