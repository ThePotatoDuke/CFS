package com.example.cfs.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cfs.R
import com.example.compose.CFSTheme

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

    Surface(
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(mediumPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Card(
                Modifier,
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary,
                )

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
                        isLoginError = isLoginError,
                        isPassword = false
                    )
                    EditTextField(
                        label = R.string.password,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Password
                        ),
                        value = password,
                        onValueChange = onPasswordEdit,
                        isLoginError = isLoginError,
                        isPassword = true
                    )
                    Button(
                        onClick = { // THIS WILL TRIGGER WHEN LOGIN IS CLICKED AND CHECK FOR LOGIN, IF LOGIN IS CORRECT GO TO NEXT SCREEN
                            onLoginButtonClicked()
                        },
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Text(
                            text = stringResource(R.string.login), fontSize = 16.sp
                        )
                    }
                }
            }

        }
    } // surface
}

@Composable
fun EditTextField(
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChange: (String) -> Unit,
    isLoginError: Boolean = false,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false
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
        modifier = modifier,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
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