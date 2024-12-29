package com.example.remotedatabase.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.remotedatabase.MahasiswaApplications

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                aplikasiMhs().container.mahasiswaRepository
            )
        }
        initializer {
            InsertViewModel(
                aplikasiMhs().container.mahasiswaRepository
            )
        }
        initializer {
            DetailViewModel(
                aplikasiMhs().container.mahasiswaRepository
            )
        }
        initializer {
            UpdateViewModel(
                createSavedStateHandle(),
                aplikasiMhs().container.mahasiswaRepository
            )
        }
    }
}

fun CreationExtras.aplikasiMhs(): MahasiswaApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MahasiswaApplications)