package com.korusell.korusell.ui.auth.code

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.korusell.korusell.R

@Composable
fun Code(
    authNav: NavHostController,
    onResult: () -> Unit
) = Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceBetween
){
    val codeState = remember{ mutableStateOf("") }
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
    )
    CodeField(codeState)
    LoginButton(
        modifier = Modifier.padding(16.dp)
    ) {
        onResult()
    }

}

@Composable
private fun CodeField(
    codeState: MutableState<String>
) = CodeField(
    modifier = Modifier.fillMaxWidth(0.8f),
    length = 6,
    codeState = codeState
)

@Composable
private fun LoginButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) = Button(
    modifier = modifier,
    onClick = onClick
) {
    Text(
        text = stringResource(R.string.login)//TODO
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewCode(){
    Code(authNav = rememberNavController()) {
        
    }
}