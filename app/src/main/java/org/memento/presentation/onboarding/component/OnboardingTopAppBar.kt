package org.memento.presentation.onboarding.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.memento.R
import org.memento.presentation.type.OnboardingTopType
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.defaultMementoTypography

@Composable
fun OnboardingTopAppBar(
    type: OnboardingTopType,
    onBackClick: () -> Unit = {},
    onSkipClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
        modifier
            .then(modifier)
            .fillMaxWidth(),
    ) {
        Box(
            modifier =
            Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        ) {
            if (type.isBack) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.btn_back),
                    contentDescription = stringResource(id = R.string.onboarding_back),
                    modifier =
                    Modifier
                        .align(Alignment.CenterStart)
                        .padding(vertical = 16.dp)
                        .noRippleClickable {
                            onBackClick()
                        },
                )
            }

            if (type.isSkip) {
                Text(
                    text = stringResource(id = R.string.onboarding_skip),
                    style = defaultMementoTypography.body_b_14,
                    color = darkModeColors.gray06,
                    modifier =
                    Modifier
                        .align(Alignment.CenterEnd)
                        .padding(top = 4.dp)
                        .noRippleClickable {
                            onSkipClick()
                        },
                )
            }
        }
        Spacer(Modifier.height(10.dp))
        OnboardingProgressBar(
            pageNum = type.pageNum,
            modifier = Modifier,
        )
        Spacer(Modifier.height(29.dp))
        Column(
            modifier = Modifier
                .padding(start = 4.dp),
        ) {
            Text(
                text = type.pageNum.toString(),
                style = defaultMementoTypography.head_b_40,
                color = darkModeColors.gray07,
            )
            Spacer(Modifier.height(18.dp))
            Text(
                text = stringResource(id = type.title),
                style = defaultMementoTypography.title_b_24,
                color = darkModeColors.white,
            )
        }
    }
}

@Preview
@Composable
fun OnboardingTopAppBarPreview1() {
    OnboardingTopAppBar(
        type = OnboardingTopType.PAGE1,
        onBackClick = { println("Back clicked") },
        onSkipClick = { println("Skip clicked") },
    )
}

@Preview
@Composable
fun OnboardingTopAppBarPreview2() {
    OnboardingTopAppBar(
        type = OnboardingTopType.PAGE2,
        onBackClick = { println("Back clicked") },
        onSkipClick = { println("Skip clicked") },
    )
}

@Preview
@Composable
fun OnboardingTopAppBarPreview3() {
    OnboardingTopAppBar(
        type = OnboardingTopType.PAGE3,
        onBackClick = { println("Back clicked") },
        onSkipClick = { println("Skip clicked") },
    )
}
