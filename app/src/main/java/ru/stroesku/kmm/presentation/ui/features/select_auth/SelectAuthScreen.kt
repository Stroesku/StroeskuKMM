package ru.stroesku.kmm.presentation.ui.features.select_auth


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.stroesku.kmm.presentation.ui.base.PrimaryButton
import ru.stroesku.kmm.presentation.ui.base.SecondaryButton
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.stroesku.kmm.R
import ru.stroesku.kmm.presentation.ui.navigation.RootNavTree


@Composable
fun SelectAuthScreen() {
    val controller = LocalRootController.current
    Scaffold(bottomBar = { SignButtons(controller) }) { padding ->
        Surface(color = Color(0xFF99FF8E)) {
            SelectAuthContent(
                Modifier
                    .padding(padding)
                    .fillMaxSize()
            )
        }
    }

}

@Composable
fun SelectAuthContent(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize()) {
        Spacer(Modifier.height(56.dp))
        ImageSet(Modifier.fillMaxWidth())
    }
}

@Composable
fun ImageSet(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}

@Composable
fun SignButtons(rootController: RootController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        SecondaryButton(
            text = stringResource(id = R.string.sign_up),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            onClick = {
                rootController.launch(RootNavTree.SignUp.name)
            }
        )
        Spacer(Modifier.height(4.dp))
        PrimaryButton(
            text = stringResource(id = R.string.sign_in),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            onClick = {
                rootController.launch(RootNavTree.SignIn.name)
            }
        )

        Spacer(Modifier.height(16.dp))
    }
}