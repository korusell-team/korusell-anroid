package com.korusell.korusell.ui.auth.phone

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.korusell.korusell.R
import com.korusell.korusell.ui.auth.AuthRoutes

@Composable
fun Phone(
    authNav: NavHostController
) = Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceBetween
){
    val phoneState = remember{ mutableStateOf("") }
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
    )
    PhoneField(phoneState)
    GetCodeButton(
        modifier = Modifier.padding(16.dp)
    ) {
        authNav.navigate(AuthRoutes.CODE.route)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PhoneField(
    phoneState: MutableState<String>
) = OutlinedTextField(
    value = phoneState.value,
    onValueChange = { phoneState.value = it },
    singleLine = true,
    keyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = KeyboardType.Phone
    ),
    label = {
        Row {
            Icon(
                imageVector = Icons.Filled.Smartphone,
                contentDescription = stringResource(R.string.phone_number)
            )
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(R.string.phone_number)//TODO
            )
        }
    }
)

@Composable
private fun GetCodeButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) = Button(
    modifier = modifier,
    onClick = onClick
) {
    Text(
        text = stringResource(R.string.get_sms)//TODO
    )
}