package com.example.weeklytodolist.ui.authenticationScreen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.weeklytodolist.R
import com.example.weeklytodolist.data.firebase.Result
import com.example.weeklytodolist.navigation.NavigationDestination

object LoginScreenDestination: NavigationDestination {
    override val route = "login"
    override val titleRes: Int  = R.string.login_screen
}

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    onNavigateToSignup: () -> Unit,
    onLoginAccept: () -> Unit
) {
    var isPasswordShowed by remember {
        mutableStateOf(false)
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            visualTransformation = if (isPasswordShowed) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                lateinit var icon: ImageVector
                lateinit var description: String

                when (isPasswordShowed) {
                    true -> {
                        icon = Icons.Filled.Visibility
                        description = "Show password"
                    }
                    false -> {
                        icon = Icons.Filled.VisibilityOff
                        description = "Hide password"
                    }
                }

                IconButton(onClick = { isPasswordShowed = !isPasswordShowed }) {
                    Icon(imageVector = icon, contentDescription = description)
                }
            }
        )
        Button(
            onClick = {
                authViewModel.login(email, password)

                if (authViewModel.result.value == Result.Success(true)) {
                    onLoginAccept()
                    email = ""
                    password = ""
                } else {
                    Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Log in")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Does not have an account? Sign up.",
            modifier = Modifier.clickable {
                onNavigateToSignup()
            }
        )
    }
}