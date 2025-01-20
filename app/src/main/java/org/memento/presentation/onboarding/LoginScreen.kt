package org.memento.presentation.onboarding

import android.app.Activity
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
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import org.memento.BuildConfig
import org.memento.R
import org.memento.presentation.onboarding.component.SocialLoginButton
import org.memento.presentation.util.noRippleClickable
import org.memento.ui.theme.darkModeColors
import org.memento.ui.theme.defaultMementoTypography

@Composable
fun LoginScreen(navigationToOnboardingScreen1: () -> Unit) {
    val context = LocalContext.current
    val oneTapClient = Identity.getSignInClient(context)
    val auth: FirebaseAuth = Firebase.auth
    var showOneTapUI by remember { mutableStateOf(false) }

    val signInRequest =
        BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId((BuildConfig.CLIENT_ID))
                    .setFilterByAuthorizedAccounts(false)
                    .build(),
            )
            .build()

    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult(),
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                    val idToken = credential.googleIdToken
                    when {
                        idToken != null -> {
                            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                            auth.signInWithCredential(firebaseCredential)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                    } else {
                                    }
                                }
                        }

                        else -> {
                        }
                    }
                } catch (e: ApiException) {
                }
            }
        }

    LaunchedEffect(showOneTapUI) {
        if (showOneTapUI) {
            oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener { result ->
                    try {
                        val intentSender = result.pendingIntent.intentSender
                        val intentSenderRequest = IntentSenderRequest.Builder(intentSender).build()
                        launcher.launch(intentSenderRequest)
                    } catch (e: Exception) {
                    }
                }
                .addOnFailureListener { e ->
                }
            showOneTapUI = false
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
                showOneTapUI = true
                val user = Firebase.auth.currentUser
                user?.let {
                    val name = it.displayName
                    val email = it.email
                    val photoUrl = it.photoUrl
                    val emailVerified = it.isEmailVerified
                    val uid = it.uid
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
                        .noRippleClickable { },
            )
        }
    }
}
