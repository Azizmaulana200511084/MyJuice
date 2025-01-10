package com.aziz.myjuice.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.aziz.myjuice.R
import com.aziz.myjuice.dataroom.local.MyJuiceEntity
import com.aziz.myjuice.ui.navigation.Screen

@Composable
fun AvailableContent(
    listMyJuice: List<MyJuiceEntity>,
    navController: NavController,
) {
    LazyColumn {
        items(listMyJuice, key = { it.name }) { myjuice ->
            MyJuiceItem(myjuice, navController)
        }
    }
}

@Preview
@Composable
fun AvailableContentPreview() {
    val fakeData = listOf(
        MyJuiceEntity(id = 1, name = "Juice 1", description = "Description 1", photoUrl = "photo_url_1"),
        MyJuiceEntity(id = 2, name = "Juice 2", description = "Description 2", photoUrl = "photo_url_2"),
        MyJuiceEntity(id = 3, name = "Juice 3", description = "Description 3", photoUrl = "photo_url_3")
    )

    AvailableContent(
        listMyJuice = fakeData,
        navController = NavController()
    )
}

@Composable
fun NavController(): NavHostController {
    return rememberNavController()
}

@Composable
fun MyJuiceItem(
    myjuice: MyJuiceEntity,
    navController: NavController,
) {
    rememberCoroutineScope()
    val (id, name, _, photoUrl) = myjuice

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(56.dp))
            .border(5.dp, Color.Red.copy(0.5f), MaterialTheme.shapes.small)
            .clickable { navController.navigate(Screen.Detail.createRoute(id ?: 0)) },
    ) {
        Column {
            Column(modifier = Modifier.padding(5.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Yellow),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }

            Box {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = name,
                    contentScale = ContentScale.FillWidth,
                    placeholder = painterResource(R.drawable.placeholder_image),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
