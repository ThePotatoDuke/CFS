package com.example.cfs.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cfs.R
import com.example.cfs.ui.theme.CFSTheme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginButtonClicked: () -> Unit = { },
    onUserNameEdit: (String) -> Unit = {},
    onPasswordEdit: (String) -> Unit = {},
    isLoginError: Boolean = true,
    userName: String = "",
    password: String = ""
) {
    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(mediumPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            Modifier,
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),


            ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(mediumPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(mediumPadding)
            ) {

                Text(
                    text = stringResource(id = R.string.login),
                    style = MaterialTheme.typography.displayMedium
                )
                EditTextField(
                    label = R.string.user_name,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    value = userName,
                    onValueChange = onUserNameEdit,
                    isLoginError = isLoginError
                )
                EditTextField(
                    label = R.string.password,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    value = password,
                    onValueChange = onPasswordEdit,
                    isLoginError = isLoginError
                )
                OutlinedButton(
                    onClick = { // THIS WILL TRIGGER WHEN LOGIN IS CLICKED AND CHECK FOR LOGIN, IF LOGIN IS CORRECT GO TO NEXT SCREEN
                        onLoginButtonClicked()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.login), fontSize = 16.sp
                    )
                }
            }
        }

    }
}

@Composable
fun EditTextField(
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChange: (String) -> Unit,
    isLoginError: Boolean = false,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(label)) },
        keyboardOptions = keyboardOptions,
        singleLine = true,
        isError = isLoginError,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
        ),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {

    CFSTheme {
        CFSTheme {
            LoginScreen()
        }
    }
}