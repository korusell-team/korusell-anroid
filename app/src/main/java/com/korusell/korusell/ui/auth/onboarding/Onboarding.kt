package com.korusell.korusell.ui.auth.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.korusell.korusell.R
import com.korusell.korusell.ui.auth.AuthRoutes
import com.korusell.korusell.ui.theme.KORUSELLTheme

@Composable
fun Onboarding(authNav: NavHostController) = Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.End,
    verticalArrangement = Arrangement.SpaceBetween
){
    val goNext: () -> Unit = {
        authNav.navigate(AuthRoutes.PHONE.route)
    }
    SkipButton(
        modifier = Modifier.padding(16.dp),
        onSkip = { goNext() }
    )
    WelcomeContent()
    BottomContent(
        modifier = Modifier.padding(16.dp),
        onNext = goNext
    )

}

@Composable
private fun SkipButton(
    modifier: Modifier = Modifier,
    onSkip: (Int) -> Unit
) = ClickableText(
    modifier = modifier,
    text = AnnotatedString(stringResource(R.string.skip)),
    style = MaterialTheme.typography.bodySmall,
    onClick = onSkip
)

@Composable
private fun WelcomeContent(
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier
){
    WelcomeImage()
    WelcomeText(
        Modifier.padding(top = 16.dp)
    )
}

@Composable
private fun WelcomeImage(
    modifier: Modifier = Modifier
) = Image(
    modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 32.dp),
    painter = painterResource(R.drawable.onboarding_young_man),
    contentScale = ContentScale.FillWidth,
    contentDescription = null
)

@Composable
private fun WelcomeText(
    modifier: Modifier = Modifier
) = Text(
    modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
    text = stringResource(R.string.welcome_text),
    style = MaterialTheme.typography.bodyMedium,
    textAlign = TextAlign.Center
)

@Composable
private fun BottomContent(
    modifier: Modifier = Modifier,
    onNext: () -> Unit
) = FloatingActionButton(
    modifier = modifier,
    shape = CircleShape,
    onClick = onNext
) {
    Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
        contentDescription = null
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PreviewOnboarding() = KORUSELLTheme {
    Onboarding(authNav = rememberNavController())
}