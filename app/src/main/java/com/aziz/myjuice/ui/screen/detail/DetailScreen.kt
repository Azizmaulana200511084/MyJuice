package com.aziz.myjuice.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.aziz.myjuice.R
import com.aziz.myjuice.dataroom.local.MyJuiceEntity
import com.aziz.myjuice.dataroom.fakedata.UiState
import com.aziz.myjuice.ui.components.ErrorContent
import com.aziz.myjuice.ui.theme.MyJuiceTheme
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(myjuiceId: Int, navController: NavController, scaffoldState: ScaffoldState) {
    val detailViewModel = hiltViewModel<DetailViewModel>()
    detailViewModel.myjuice.collectAsState(UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> detailViewModel.getMyJuice(myjuiceId)
            is UiState.Error -> ErrorContent()
            is UiState.Success -> {
                DetailContent(
                    uiState.data,
                    navController,
                    scaffoldState,
                    detailViewModel::updateFavoriteMyJuice
                )
            }
        }
    }
}

@Composable
fun DetailContent(
    myjuice: MyJuiceEntity,
    navController: NavController,
    scaffoldState: ScaffoldState,
    onUpdateFavoriteMyJuice: (id: Int, isFavorite: Boolean) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val (id, name, description, photoUrl, isFavorite) = myjuice

    Box(modifier = Modifier
        .background(MaterialTheme.colors.onSecondary)
        .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {
            Box {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.placeholder_image),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(0.dp, 0.dp, 100.dp, 100.dp))
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = description,
                    style = MaterialTheme.typography.body1,
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                )
            }
        }

        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp)
                .align(Alignment.TopStart)
                .clip(CircleShape)
                .size(40.dp)
                .testTag("back_button")
                .background(Color.Yellow)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
            )
        }

        Icon(
            imageVector = if (isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
            tint = if (isFavorite) Color.Red else MaterialTheme.colors.onSurface,
            contentDescription = name,
            modifier = Modifier
                .size(50.dp)
                .padding(8.dp)
                .align(Alignment.TopEnd)
                .clip(CircleShape)
                .background(Color.Yellow, CircleShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onUpdateFavoriteMyJuice(id ?: 0, !isFavorite)
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "$name ${if (isFavorite) "removed from" else "added as"} favorite ",
                            actionLabel = "Dismiss",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
        )
    }
}

@Preview
@Composable
fun DetailScreenPreview() {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()

    val myJuice = MyJuiceEntity(
        id = 1,
        name = "My Juice",
        description = "This is a delicious and healthy juice.",
        photoUrl = "https://example.com/myjuice.jpg",
        isFavorite = false
    )

    MyJuiceTheme {
        DetailContent(
            myjuice = myJuice,
            navController = navController,
            scaffoldState = scaffoldState,
            onUpdateFavoriteMyJuice = { _, _ ->
                // Do nothing in preview
            }
        )
    }
}
