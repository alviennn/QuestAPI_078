package com.example.remotedatabase.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remotedatabase.model.Mahasiswa
import com.example.remotedatabase.repository.MahasiswaRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class HomeUiState{
    data class Success(val mahasiswa: List<Mahasiswa>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class HomeViewModel(private val mhs: MahasiswaRepository) : ViewModel(){
    var mhsUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set
    var isRefreshing by mutableStateOf(false)
        private set
    var snackbarMessage: String? by mutableStateOf(null)
        private set
    init {
        getmhs()
    }

    fun getmhs() {
        viewModelScope.launch {
            isRefreshing = true
            mhsUiState = try {
                HomeUiState.Success(mhs.getMahasiswa())
            } catch (e: IOException) {
                HomeUiState.Error
                snackbarMessage = "Network error: ${e.message}"
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
                snackbarMessage = "HTTP error: ${e.message}"
                HomeUiState.Error
            } finally {
                isRefreshing = false
            }
        }
    }

    fun deleteMhs(nim: String){
        viewModelScope.launch {
            try {
                mhs.deleteMahasiswa(nim)
            }catch (e: IOException){
                HomeUiState.Error
            }catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }
}

