package ru.stroesku.kmm.presentation.ui.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import ru.stroesku.kmm.R
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme

@Composable
fun BaseAsyncImage(
    model: Any?,
    size: Dp = 88.dp,
    defaultIcon: Int = R.drawable.ic_empty_user_photo
) {
    SubcomposeAsyncImage(
        model = model,
        modifier = Modifier
            .size(size)
            .clip(BaseTheme.baseShapes.roundDefault),
        contentDescription = null,
        contentScale = ContentScale.Crop
    ) {
        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                Box(modifier = Modifier.size(80.dp)) {
                    CircularProgressIndicator(
                        Modifier
                            .size(35.dp)
                            .align(Alignment.Center),
                        color = BaseTheme.baseColors.secondaryTextColor
                    )
                }
            }
            is AsyncImagePainter.State.Error -> {
                Image(
                    painter = painterResource(id = defaultIcon),
                    modifier = Modifier
                        .size(80.dp)
                        .clip(BaseTheme.baseShapes.roundDefault),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            else -> SubcomposeAsyncImageContent()
        }
    }
}