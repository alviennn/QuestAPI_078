package com.example.remotedatabase.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.remotedatabase.ui.navigation.DestinasiNavigasi
import com.example.remotedatabase.ui.viewmodel.DetailViewModel
import com.example.remotedatabase.ui.viewmodel.PenyediaViewModel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail/{nim}"
    override val titleRes = "Detail Mahasiswa"
    const val NIM = "nim"
    val routeWithArgs = "$route/{$NIM}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    nim: String,
    onEditClick: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val mahasiswa = viewModel.uiState.detailUiEvent

    LaunchedEffect(nim) {
        viewModel.fetchDetailMahasiswa(nim)
    }

    val isLoading = viewModel.uiState.isLoading
    val isError = viewModel.uiState.isError
    val errorMessage = viewModel.uiState.errorMessage

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(DestinasiDetail.titleRes) },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else if (isError) {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else if (viewModel.uiState.isUiEventNotEmpty) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(text = "NIM: ${mahasiswa.nim}", style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Nama: ${mahasiswa.nama}", style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Alamat: ${mahasiswa.alamat}", style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Jenis Kelamin: ${mahasiswa.jenisKelamin}", style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Kelas: ${mahasiswa.kelas}", style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Angkatan: ${mahasiswa.angkatan}", style = MaterialTheme.typography.bodyLarge)
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(onClick = { onEditClick(mahasiswa.nim) }) {
                                Text("Edit Data")
                            }
                        }
                    }
                }
            }
        }
    )
}