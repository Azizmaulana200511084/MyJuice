package com.aziz.myjuice.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aziz.myjuice.R

@Composable
fun ProfileScreen() {
    LazyColumn(
        modifier = Modifier
            .background(Color.Red)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Image(
                painter = painterResource(R.drawable.profile),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
                    .clip(CutCornerShape(topStart = 90.dp, topEnd = 90.dp, bottomStart = 90.dp, bottomEnd = 90.dp))
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(text = "Aziz Maulana", color = Color.White, style = MaterialTheme.typography.h4)
            Text(text = "a246dsx3162@bangkit.academy", color = Color.White, style = MaterialTheme.typography.h6)
        }
    }
}
@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}