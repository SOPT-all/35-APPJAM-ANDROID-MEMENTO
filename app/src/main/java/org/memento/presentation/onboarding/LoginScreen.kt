package org.memento.presentation.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.onboarding.component.SocialLoginButton
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.defaultMementoTypography

@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 130.dp, bottom = 176.dp),
        verticalArrangement = Arrangement.Center,
        Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.onboarding_login_title),
            style = defaultMementoTypography.title_b_24,
            color = darkModeColors.white,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(70.dp))
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.logo_memento_white),
            contentDescription = stringResource(id = R.string.onboarding_google_login),
        )
        Spacer(Modifier.height(74.dp))
        SocialLoginButton(
            icon = R.drawable.img_google,
            content = stringResource(id = R.string.onboarding_google_login),
            onClick = {},
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(18.dp))
        Row {
            Text(
                text = stringResource(id = R.string.onboarding_login_contract),
                style = defaultMementoTypography.detail_r_11,
                color = darkModeColors.gray07
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = stringResource(id = R.string.onboarding_login_contract_link),
                style = defaultMementoTypography.detail_r_11,
                color = darkModeColors.gray04,
                modifier = Modifier
                    .noRippleClickable { }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}