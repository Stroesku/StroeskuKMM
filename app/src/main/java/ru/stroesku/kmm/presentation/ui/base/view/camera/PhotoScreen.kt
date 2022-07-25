package ru.stroesku.kmm.presentation.ui.base.view.camera

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import ru.stroesku.kmm.R
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import ru.stroesku.kmm.presentation.ui.base.view.camera.CameraState
import ru.stroesku.kmm.presentation.ui.extension.getOutputDirectory
import ru.stroesku.kmm.presentation.ui.theme.BaseTheme
import java.io.File
import java.util.concurrent.Executors

@Composable
fun PhotoScreen(onMakePhoto: (File) -> Unit) {
    val context = LocalContext.current
    val outputDirectory = remember { getOutputDirectory(context) }
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    var cameraState by remember { mutableStateOf(CameraState.Camera) }
    var photoFile: File? by remember { mutableStateOf(null) }

    Surface(color = BaseTheme.baseColors.thirtyBackground, modifier = Modifier.fillMaxSize()) {
        when (cameraState) {
            CameraState.Camera -> {
                CameraView(
                    outputDirectory = outputDirectory,
                    executor = cameraExecutor,
                    onImageCaptured = {
                        cameraState = CameraState.PhotoPreview
                        photoFile = it
                    },
                    onError = { }
                )
            }
            CameraState.PhotoPreview -> {

                Box {
                    Image(
                        painter = rememberImagePainter(Uri.fromFile(photoFile)),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )

                    IconButton(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        onClick = {
                            photoFile?.let { onMakePhoto(it) }
                        }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_success),
                            contentDescription = "Apply picture",
                            tint = Color.White,
                            modifier = Modifier
                                .size(80.dp)
                                .padding(bottom = 24.dp)
                        )
                    }

                    IconButton(
                        modifier = Modifier.align(Alignment.BottomStart),
                        onClick = {
                            photoFile?.delete()
                            cameraState = CameraState.Camera
                        }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_repeat),
                            contentDescription = "Apply picture",
                            tint = Color.White,
                            modifier = Modifier
                                .size(80.dp)
                                .padding(bottom = 24.dp)
                        )
                    }
                }
            }

        }
    }

    DisposableEffect(key1 = true, effect = {
        onDispose { cameraExecutor.shutdown() }
    })
}