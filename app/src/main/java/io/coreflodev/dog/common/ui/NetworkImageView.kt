package io.coreflodev.dog.common.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.rememberImagePainter

@Composable
fun LoadImage(url: String, modifier: Modifier = Modifier) {
    Image(
        painter = rememberImagePainter(url),
        contentDescription = null,
        modifier = modifier
    )
}
