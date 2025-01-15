package org.memento.presentation.onboarding.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.defaultMementoTypography

@Composable
fun SocialLoginButton(
    @DrawableRes icon: Int,
    content: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth()
            .background(color = darkModeColors.gray10)
            .noRippleClickable {
                onClick()
            },
        Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 11.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = stringResource(id = R.string.onboarding_google_login),
                Modifier.padding(end = 8.dp)
            )
            Text(
                text = content,
                style = defaultMementoTypography.body_r_16,
                color = darkModeColors.white
            )
        }
    }
}