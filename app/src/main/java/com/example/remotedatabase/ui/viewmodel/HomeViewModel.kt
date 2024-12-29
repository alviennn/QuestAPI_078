package com.example.remotedatabase.ui.viewmodel

import com.example.remotedatabase.model.Mahasiswa

sealed class HomeUiState{
    data class Success(val mahasiswa: List<Mahasiswa>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}