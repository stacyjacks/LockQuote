package com.kurmakaeva.anastasia.lockquote.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.kurmakaeva.anastasia.lockquote.R

@ExperimentalComposeUiApi
@Composable
fun SearchView(
    query: String,
    onQueryChanged: (String) -> Unit,
    onClearClick: () -> Unit,
    onSearchClick: (String) -> Unit
) {

    var showClearButton by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    Row(modifier = Modifier
        .background(color = colorResource(id = R.color.colorAccent))
        .fillMaxWidth()
        .onFocusChanged { focusState -> showClearButton = (focusState.isFocused) }
        .focusRequester(focusRequester)
    ) {
        TextField(
            value = query,
            onValueChange = { onQueryChanged(it) },
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(color = colorResource(id = R.color.colorPrimaryDark)),
            label = {
                Text(
                    text = stringResource(R.string.query_hint),
                    color = colorResource(id = R.color.darkGreyColor)
                )
            },
            shape = RoundedCornerShape(18.dp),
            leadingIcon = { SearchIcon() },
            trailingIcon = {
                AnimatedVisibility(visible = showClearButton, enter = fadeIn(), exit = fadeOut()) {
                    ClearIcon(onClearClick = onClearClick)
                }
            },
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClick(query)
                    keyboardController?.hide()
                }),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(id = R.color.lightGreyColor),
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun SearchIcon() {
    Icon(imageVector = Icons.Default.Search, contentDescription = "Search icon")
}

@Composable
fun ClearIcon(onClearClick: () -> Unit) {
    IconButton(onClick = { onClearClick() }) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Clear text icon"
        )
    }
}