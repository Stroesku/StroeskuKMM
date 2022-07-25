package ru.stroesku.kmm.presentation.ui.extension

import android.content.Context
import ru.stroesku.kmm.R
import java.io.File

fun getOutputDirectory(context: Context): File {
    val mediaDir = context.cacheDir?.let {
        File(it, context.resources.getString(R.string.photo_dir)).apply { mkdirs() }
    }

    return if (mediaDir != null && mediaDir.exists()) mediaDir else context.filesDir
}