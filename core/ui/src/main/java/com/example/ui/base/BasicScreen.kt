package com.example.ui.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.ui.R
import kotlinx.coroutines.flow.collectLatest


@Composable
fun <State : ViewState, Event : ViewEvent> BasicScreen(
    viewModel: BaseMVIViewModel<State, Event, *>,
    toolbar: @Composable (() -> Unit)? = null,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable (state: State) -> Unit,
    loadingContent: @Composable (() -> Unit)? = null,
    errorContent: @Composable ((String, () -> Unit) -> Unit)? = null, // New slot
    onEvent: ((Event) -> Unit)? = null,
) {
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiEvent.collectLatest {
                onEvent?.invoke(it)
            }
        }
    }

    LaunchedEffect(state.error) {
        if (!state.error.isNullOrEmpty()) {
            dialogMessage = state.error
            showDialog = true
        }
    }

    Scaffold(
        topBar = { toolbar?.invoke() },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                content(state)

                if (state.isLoading) {
                    (loadingContent ?: {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    })()
                }

                if (showDialog && dialogMessage != null) {
                    (errorContent ?: { message, onDismiss ->
                        ErrorDialog(message = message, onDismiss = onDismiss)
                    })(
                        dialogMessage!!, {
                            showDialog = false
                            dialogMessage = null
                        })
                }
            }
        })
}

@Composable
fun ErrorDialog(
    message: String, onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(id = R.string.error_title)) },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(id = R.string.ok_button))
            }
        })
}
