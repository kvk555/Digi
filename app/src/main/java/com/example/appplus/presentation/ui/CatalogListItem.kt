package com.example.appplus.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.appplus.R
import com.example.appplus.domain.entities.ItemEntity

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CatalogListItem(
    item: ItemEntity,
    onItemClick: (ItemEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = { onItemClick(item) }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            model = item.image,
            contentDescription = null,
            failure = placeholder(R.drawable.broken_image),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(68.dp)
                .padding(end = 8.dp),
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = item.text,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )
            Text(
                text = "ID: ${item.id}",
                fontSize = 14.sp,
                color = Color.Gray,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        SecretRevealTextOnClick(
            text = item.confidence.toString(),
        )
    }
}

@Composable
private fun SecretRevealTextOnClick(text: String) {
    var isTextRevealed by remember { mutableStateOf(false) }

    Box(modifier = Modifier.padding(8.dp)) {
        if (isTextRevealed) {
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Gray,
                )
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.secret),
                contentDescription = null,
                modifier = Modifier
                    .size(34.dp)
                    .clickable {
                        isTextRevealed = true
                    }
            )
        }
    }
}