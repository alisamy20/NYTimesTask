package com.example.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.ui.theme.padding_16
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import com.example.ui.R

@OptIn(FlowPreview::class)
@Composable
fun SearchBar(query: String, onQueryChanged: (String) -> Unit) {
    var currentQuery by remember { mutableStateOf(query) }

    LaunchedEffect(Unit) {
        snapshotFlow { currentQuery }
            .debounce(500)
            .distinctUntilChanged()
            .collect { onQueryChanged(it) }
    }

    TextField(
        value = currentQuery,
        onValueChange = { currentQuery = it },
        label = { Text(stringResource(R.string.search)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding_16)
    )
}