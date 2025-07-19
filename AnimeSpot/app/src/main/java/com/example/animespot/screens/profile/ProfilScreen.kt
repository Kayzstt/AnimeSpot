package com.example.animespot.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animespot.R
import com.example.animespot.screens.login.AuthViewModel
import com.example.animespot.ui.theme.luckyfontfamily


@Composable
fun ProfileScreen(authViewModel: AuthViewModel, navigateToLogin: () -> Unit, profileViewModel: ProfileViewModel) {
    val showDialog = remember { mutableStateOf(false) }
    val image by profileViewModel.selectedImage.observeAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        ProfileContent(showDialog, image, navigateToLogin, authViewModel, profileViewModel)
    }
}

@Composable
fun ProfileContent(
    showDialog: MutableState<Boolean>,
    image: Int?,
    navigateToLogin: () -> Unit,
    authViewModel: AuthViewModel,
    profileViewModel: ProfileViewModel
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileHeader()
        Spacer(modifier = Modifier.padding(vertical = 10.dp))
        ProfileImage(image, showDialog, profileViewModel)
        LogoutButton(navigateToLogin, authViewModel)
    }
}

@Composable
fun ProfileHeader() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.animeimage),
            contentDescription = "Icon",
            modifier = Modifier.size(70.dp),
            alignment = Alignment.TopCenter
        )
    }
    Spacer(modifier = Modifier.padding(vertical = 1.dp))

    Text(
        text = stringResource(id = R.string.my_profil),
        fontFamily = luckyfontfamily,
        fontSize = 20.sp
    )
}

@Composable
fun ProfileImage(image: Int?, showDialog: MutableState<Boolean>, profileViewModel: ProfileViewModel) {
    image?.let {
        Image(
            painter = painterResource(id = it),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
        )
        EditImageButton(showDialog)
    } ?: Text("No image selected", modifier = Modifier.padding(top = 8.dp))
    if (showDialog.value) {
        ImagePickerDialog(showDialog, profileViewModel)
    }
}

@Composable
fun EditImageButton(showDialog: MutableState<Boolean>) {
    IconButton(onClick = { showDialog.value = true }) {
        Icon(imageVector = Icons.Filled.Create, contentDescription = "Edit")
    }
}

@Composable
fun LogoutButton(navigateToLogin: () -> Unit, authViewModel: AuthViewModel) {
    Button(
        onClick = {
            authViewModel.signOutUser()
            navigateToLogin()
        },
        modifier = Modifier
            .size(180.dp, 60.dp)
            .padding(top = 10.dp),
        colors = ButtonDefaults.buttonColors(Color.Black),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text("Logout", fontSize = 18.sp)
    }
}
@Composable
fun ImagePickerDialog(showDialog: MutableState<Boolean>,profileViewModel: ProfileViewModel) {
    AlertDialog(
        onDismissRequest = { showDialog.value = false },
        title = { Text(stringResource(id = R.string.select_profil)) },
        text = {
            Row {
                Column {
                    Image(
                        painter = painterResource(id = R.drawable.avatar6),
                        contentDescription = "Option 1",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .clickable {
                                profileViewModel.selectImage(R.drawable.avatar6)
                                showDialog.value = false
                            }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Image(
                        painter = painterResource(id = R.drawable.avatar1),
                        contentDescription = "Option 2",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .clickable {
                                profileViewModel.selectImage(R.drawable.avatar1)
                                showDialog.value = false
                            }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Image(
                        painter = painterResource(id = R.drawable.avatar2),
                        contentDescription = "Option 3",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .clickable {
                                profileViewModel.selectImage(R.drawable.avatar2)
                                showDialog.value = false
                            }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Image(
                        painter = painterResource(id = R.drawable.avatar3),
                        contentDescription = "Option 4",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .clickable {
                                profileViewModel.selectImage(R.drawable.avatar3)
                                showDialog.value = false
                            }
                    )


                }
                Spacer(modifier = Modifier.width(20.dp))
                Column {

                    Image(
                        painter = painterResource(id = R.drawable.avatar4),
                        contentDescription = "Option 5",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .clickable {
                                profileViewModel.selectImage(R.drawable.avatar4)
                                showDialog.value = false
                            }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Image(
                        painter = painterResource(id = R.drawable.avatar5),
                        contentDescription = "Option 6",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .clickable {
                                profileViewModel.selectImage(R.drawable.avatar5)
                                showDialog.value = false
                            }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Image(
                        painter = painterResource(id = R.drawable.avatar7),
                        contentDescription = "Option 7",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .clickable {
                                profileViewModel.selectImage(R.drawable.avatar7)
                                showDialog.value = false
                            }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Image(
                        painter = painterResource(id = R.drawable.avatar8),
                        contentDescription = "Option 8",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .clickable {
                                profileViewModel.selectImage(R.drawable.avatar8)
                                showDialog.value = false
                            }
                    )
                }
            }

        },
        confirmButton = {
            TextButton(onClick = { showDialog.value = false }) {
                Text("Close")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {


}