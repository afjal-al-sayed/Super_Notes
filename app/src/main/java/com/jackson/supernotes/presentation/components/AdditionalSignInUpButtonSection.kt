package com.jackson.supernotes.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jackson.supernotes.presentation.screens.sign_in_screen.SignInEvents

@Composable
fun AdditionalSignInUpButtonSection(
    message: String,
    buttonText: String,
    onClick: () -> Unit,
    isEnabled: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Text(
            text = message
        )
        TextButton(
            onClick = onClick,
            enabled = isEnabled
        ) {
            Text(
                text = buttonText
            )
        }
    }
}