package com.example.animespot.screens.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animespot.R


@Composable
fun AboutScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id = R.drawable.animeimage), contentDescription = "icon")
            Text(
                text = stringResource(id = R.string.my_name),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = stringResource(id = R.string.my_id),
                modifier = Modifier.padding(top = 20.dp),
                style = TextStyle(
                    fontSize = 16.sp
                )
            )
            Text(
                text = stringResource(id = R.string.my_class),
                style = TextStyle(
                    fontSize = 16.sp
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AboutScreen()

}