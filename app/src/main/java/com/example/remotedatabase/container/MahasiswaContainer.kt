package com.example.remotedatabase.container

import com.example.remotedatabase.repository.MahasiswaRepository

interface AppContainer{
    val mahasiswaRepository: MahasiswaRepository
}
