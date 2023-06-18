package com.maximchuikov.nasaapp.ui.opportunity

import android.app.Application
import com.maximchuikov.nasaapp.data.model.RoverViewState
import com.maximchuikov.nasaapp.data.remote.NetworkResult
import com.maximchuikov.nasaapp.data.repository.RoverRepository
import com.maximchuikov.nasaapp.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class OpportunityViewModel @Inject constructor(
    private val roverRepository: RoverRepository,
    application: Application
) : BaseViewModel(application) {
    private val _opportunityState = MutableStateFlow(RoverViewState())
    val opportunityState: StateFlow<RoverViewState> = _opportunityState.asStateFlow()

    suspend fun getOpportunity() {
        roverRepository.fetchOpportunity().collect { result ->
            when (result) {
                is NetworkResult.Success -> {
                    val cameraList = HashSet<String>()
                    result.data?.let { roverModel ->
                        roverModel.photos.forEach {
                            cameraList.add(it.camera.name)
                        }
                        _opportunityState.update {
                            it.copy(
                                photoList = roverModel,
                                isLoading = false,
                                cameraList = cameraList
                            )
                        }
                    }
                }
                is NetworkResult.Error -> {
                    _opportunityState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }
                }
                is NetworkResult.Loading -> {
                    _opportunityState.update {
                        it.copy(isLoading = true)
                    }
                }
            }
        }
    }
}