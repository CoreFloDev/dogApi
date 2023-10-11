package io.coreflodev.dog.common.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter

@Composable
fun LoadImage(url: String, modifier: Modifier = Modifier) {
    Image(
        painter = rememberAsyncImagePainter(url),
        contentDescription = null,
        modifier = modifier
    )
}
