package com.korusell.korusell

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.korusell.korusell.ui.RootNavigation
import com.korusell.korusell.ui.theme.KORUSELLTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainContent() }
    }
}

@Composable
private fun MainContent() = KORUSELLTheme {
    RootNavigation()
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KORUSELLTheme {

    }
}