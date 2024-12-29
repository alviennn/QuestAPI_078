package com.example.remotedatabase.ui.view.mahasiswa

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.remotedatabase.ui.customwidget.CostumeTopAppBar
import com.example.remotedatabase.ui.navigation.DestinasiNavigasi
import com.example.remotedatabase.ui.view.EntryBody
import com.example.remotedatabase.ui.viewmodel.PenyediaViewModel
import com.example.remotedatabase.ui.viewmodel.UpdateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdate: DestinasiNavigasi {
    override val route = "update"
    override val titleRes = "Update Data Mahasiswa"
    const val NIM = "nim"
    val routesWithArg = "$route/{$NIM}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateMhsScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate:()-> Unit,
    viewModel: UpdateViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdate.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ){padding ->
        EntryBody(
            modifier = Modifier.padding(padding),
            insertUiState = viewModel.updateUiState,
            onSiswaValueChange = viewModel::updateInsertMhsState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateMhs()
                    delay(600)
                    withContext(Dispatchers.Main){
                        onNavigate()
                    }
                }
            }
        )
    }
}