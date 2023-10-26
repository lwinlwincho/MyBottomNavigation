package com.example.mybottomnavigation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mybottomnavigation.LocalEntryPadding

@Composable
fun HomeScreen(
    onEvent: (HomeEvent) -> Unit = {}
) {
    Surface {
        Column(
            modifier = Modifier
                .padding(LocalEntryPadding.current)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Home Screen")
            Button(
                onClick = { onEvent(HomeEvent.GoToDetail) }
            ) {
                Text(text = "Go To Detail")
            }
        }
    }
}

sealed interface HomeEvent {
    object GoToDetail : HomeEvent
}


@Preview
@Composable
private fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen()
    }
}