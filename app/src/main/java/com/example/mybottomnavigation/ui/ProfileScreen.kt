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
fun ProfileScreen(
    onEvent: (ProfileEvent) -> Unit = {}
) {
    Surface {
        Column(
            modifier = Modifier
                .padding(LocalEntryPadding.current)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Profile Screen")

            Button(
                onClick = { onEvent(ProfileEvent.Logout) }
            ) {
                Text(text = "Logout")
            }
        }
    }
}

sealed interface ProfileEvent {
    object Logout : ProfileEvent
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen()
    }
}