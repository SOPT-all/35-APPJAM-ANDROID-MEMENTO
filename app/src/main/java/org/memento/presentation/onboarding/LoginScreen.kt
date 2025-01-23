package org.memento.presentation.onboarding

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException
import org.memento.BuildConfig
import org.memento.R
import org.memento.core.util.UiState
import org.memento.data.local.TokenDataStore
import org.memento.domain.entity.UserInfo
import org.memento.presentation.main.MainScreen
import org.memento.presentation.onboarding.component.SocialLoginButton
import org.memento.presentation.onboarding.viewmodel.LoginViewModel
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.defaultMementoTypography
import timber.log.Timber

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigationToOnboardingScreen1: () -> Unit,
    navigationToMainScreen: () -> Unit,
) {
    val token by viewModel.token.collectAsState()

    LaunchedEffect(Unit) {
        Log.e("Loginscree",token.toString())
        if (!token.isNullOrEmpty()) {
            navigationToMainScreen()
        }
    }
    var webViewVisible by remember { mutableStateOf(false) }

    val user by viewModel.user.collectAsState()
    val context = LocalContext.current
    val oneTapClient = remember { Identity.getSignInClient(context) }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is UiState.Loading -> {}
        is UiState.Success -> {
            val data = (uiState as UiState.Success<UserInfo>).data
            viewModel.saveToken(
                accessToken = data.accessToken,
                refreshToken = data.refreshToken,
            )
        }

        is UiState.Failure -> {}
    }

    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult(),
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                    credential.googleIdToken?.let { idToken ->
                        viewModel.signInWithGoogle(idToken)
                        viewModel.postLogin(idToken)
                    }
                } catch (e: ApiException) {
                }
            }
        }

    val signInRequest =
        remember {
            BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(BuildConfig.CLIENT_ID)
                        .setFilterByAuthorizedAccounts(false)
                        .build(),
                )
                .build()
        }

    LaunchedEffect(user) {
        user?.let {
            navigationToOnboardingScreen1()
        }
    }

    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(top = 130.dp, bottom = 176.dp),
        verticalArrangement = Arrangement.Center,
        Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.onboarding_login_title),
            style = defaultMementoTypography.title_b_24,
            color = darkModeColors.white,
            textAlign = TextAlign.Center,
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
            onClick = {
                oneTapClient.beginSignIn(signInRequest)
                    .addOnSuccessListener { result ->
                        val intentSenderRequest = IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                        launcher.launch(intentSenderRequest)
                    }
                    .addOnFailureListener { e ->
                    }
            },
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        Spacer(Modifier.height(18.dp))
        Row {
            Text(
                text = stringResource(id = R.string.onboarding_login_contract),
                style = defaultMementoTypography.detail_r_11,
                color = darkModeColors.gray07,
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = stringResource(id = R.string.onboarding_login_contract_link),
                style = defaultMementoTypography.detail_r_11,
                color = darkModeColors.gray04,
                modifier =
                Modifier
                    .noRippleClickable {
                        webViewVisible = true
                    },
            )
        }
    }

    if (webViewVisible) {
        MementoWebView(
            url = BuildConfig.WebView_URL,
            onClose = { webViewVisible = false },
        )
    }
}
