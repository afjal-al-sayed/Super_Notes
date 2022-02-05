package com.jackson.supernotes.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jackson.supernotes.presentation.screens.sign_in_screen.SignInEvents
import com.jackson.supernotes.presentation.screens.sign_in_screen.SignInScreenState

@Composable
fun SignInUpButton(
    normalText: String,
    loadingText: String,
    isEnabled: Boolean,
    isLoading: Boolean,
    onClick: () -> Unit,
    cornerRoundness: Dp
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(cornerRoundness),
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            disabledBackgroundColor = MaterialTheme.colors.primary,
            disabledContentColor = contentColorFor(MaterialTheme.colors.primary)
        )
    ) {
        Text(
            text = if(isLoading) loadingText else normalText,
            modifier = Modifier
                .padding(vertical = 4.dp),
            style = TextStyle(
                fontSize = 18.sp
            ),
        )
        if(isLoading){
            Spacer(modifier = Modifier.width(8.dp))
            CircularProgressIndicator(
                color = contentColorFor(MaterialTheme.colors.primary),
                modifier = Modifier
                    .size(36.dp)
                    .padding(4.dp),
                strokeWidth = 3.dp
            )
        }
    }
}