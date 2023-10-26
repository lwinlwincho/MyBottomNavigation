package com.example.mybottomnavigation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DetailScreen(
    onEvent: (DetailEvent) -> Unit = {}
) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Detail Screen")

            Button(
                onClick = { onEvent(DetailEvent.BackToHome) }
            ) {
                Text(text = "Back To Home")
            }
        }
    }
}

sealed interface DetailEvent {
    object BackToHome : DetailEvent
}

@Preview
@Composable
private fun DetailScreenPreview() {
    MaterialTheme {
        DetailScreen()
    }
}