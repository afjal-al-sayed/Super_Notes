package com.jackson.supernotes.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun LazyListScope.SignUpInputField(
    value: String,
    onValueChange: (String) -> Unit,
    fieldOrder: Int,
    label: String,
    cornerRoundness: Dp,
    imeAction: ImeAction = ImeAction.Next,
//    onImeAction: () -> Unit,
    isEnabled: Boolean,
    coroutineScope: CoroutineScope,
    listState: LazyListState,
    focusManager: FocusManager,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
) {
    item {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusEvent {
                    if (it.isFocused) {
                        coroutineScope.launch {
                            listState.animateScrollToItem(fieldOrder - 1)
                        }
                    }
                },
            label = {
                Text(text = label)
            },
            shape = RoundedCornerShape(cornerRoundness),
            //                leadingIcon = { Icon(Icons.Default.Email, "E-mail icon") },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) },
                onDone = { focusManager.clearFocus() }
            ),
            singleLine = true,
            enabled = isEnabled,
            visualTransformation = if(isPassword) PasswordVisualTransformation() else VisualTransformation.None
        )
    }
}