package ru.stroesku.kmm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import ru.stroesku.kmm.presentation.ui.extension.setupRootNavigation
import ru.stroesku.kmm.presentation.ui.navigation.RootNavTree
import ru.stroesku.kmm.presentation.ui.navigation.generateRootGraph
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.core.component.KoinComponent

@FlowPreview
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
class RootActivity : AppCompatActivity(), KoinComponent {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupRootNavigation(RootNavTree.SelectAuth.name) {
            generateRootGraph()
        }
    }
}