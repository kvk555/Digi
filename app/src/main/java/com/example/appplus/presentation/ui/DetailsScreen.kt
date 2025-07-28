package com.example.appplus.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.appplus.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailsScreen(
    image: String,
    text: String,
    id: String,
    padding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        GlideImage(
            model = image,
            contentDescription = null,
            failure = placeholder(R.drawable.broken_image),
            contentScale = ContentScale.Fit,
            modifier = Modifier.weight(1f).padding(8.dp)

        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
            )
            Text(
                text = "ID: $id",
                fontSize = 18.sp,
                color = Color.Gray,
            )
        }
    }


}