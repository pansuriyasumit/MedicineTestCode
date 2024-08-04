package com.fifteen11.checkappversion.screens.login

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AccountCircle
import androidx.compose.material.icons.twotone.Lock
import androidx.compose.material.icons.twotone.Visibility
import androidx.compose.material.icons.twotone.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fifteen11.checkappversion.R
import com.fifteen11.checkappversion.screens.component.GradientButtonComponent
import com.fifteen11.checkappversion.screens.component.LockScreenOrientation
import com.fifteen11.checkappversion.screens.component.OutlinedTextFieldComponent
import com.fifteen11.checkappversion.screens.component.SecondaryTextButton
import com.fifteen11.checkappversion.ui.theme.AppColor
import com.fifteen11.checkappversion.ui.theme.SecondaryColor
import com.fifteen11.checkappversion.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    onLogin: () -> Unit
) {

    LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    val keyboardController = LocalSoftwareKeyboardController.current

    var emailAddress by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }

    Box(
        modifier = Modifier.run {
            fillMaxWidth()
                .fillMaxHeight()
                .background(
                    color = MaterialTheme.colorScheme.onPrimary,
                )
        }
    ) {

        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(25.dp, 5.dp, 25.dp, 5.dp)
                )
                .align(Alignment.BottomCenter),
        ) {
            Image(
                imageVector = Icons.TwoTone.AccountCircle,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(AppColor),
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(50.dp))

                Text(
                    text = "Login",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 130.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.headlineSmall,
                    fontFamily = FontFamily(Font(R.font.lato_bold)),
                    color = SecondaryColor,
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextFieldComponent(
                    labelText = "Username",
                    placeholderText = "Enter Username",
                    leadingIcon = Icons.TwoTone.AccountCircle,
                    trailingIcon = null,
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email,
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                        //TODO: do something here
                    }),
                    fontFamily = FontFamily(Font(R.font.lato_regular)),
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    onValueChange = {
                        emailAddress = it
                        loginViewModel.onUsernameChanged(it)
                    }
                )

                Spacer(modifier = Modifier.padding(3.dp))

                OutlinedTextFieldComponent(
                    labelText = "Password",
                    placeholderText = "Enter Password",
                    leadingIcon = Icons.TwoTone.Lock,
                    trailingIcon = {
                        IconButton(onClick = { passwordHidden = !passwordHidden }) {
                            val visibilityIcon =
                                if (passwordHidden) Icons.TwoTone.Visibility else Icons.TwoTone.VisibilityOff
                            val description =
                                if (passwordHidden) "Show password" else "Hide password"
                            Icon(imageVector = visibilityIcon, contentDescription = description)
                        }
                    },
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password,
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                        //TODO: do something here
                    }),
                    fontFamily = FontFamily(Font(R.font.lato_regular)),
                    singleLine = true,
                    visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                    modifier = Modifier.fillMaxWidth(0.8f),
                    onValueChange = { password = it }
                )

                val gradientColor = listOf(AppColor, AppColor)
                val cornerRadius = 16.dp

                Spacer(modifier = Modifier.padding(10.dp))

                GradientButtonComponent(
                    gradientColors = gradientColor,
                    cornerRadius = cornerRadius,
                    nameButton = "Login",
                    roundedCornerShape = RoundedCornerShape(30.dp),
                    onClick = {
                        loginViewModel.insertLoginHistory()
                        loginViewModel.saveUserLogin()
                        onLogin()
                    }
                )

                Spacer(modifier = Modifier.padding(10.dp))

                SecondaryTextButton(text = "Create An Account", onClick = {
                    //TODO: Coming Soon
                })

                Spacer(modifier = Modifier.padding(5.dp))

                SecondaryTextButton(text = "Forgot Password?", onClick = {
                    //TODO: Coming Soon
                })

                Spacer(modifier = Modifier.padding(20.dp))
            }
        }
    }

    val context = LocalContext.current
    BackHandler { (context as? Activity)?.finish() }
}



