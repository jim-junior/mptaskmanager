package com.group4.taskmanager.ui

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.provider.Settings.Global.getString
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.group4.taskmanager.R
import com.group4.taskmanager.data.UserData
import kotlinx.coroutines.tasks.await

class GoogleAuthUIClient(
    private val context: Context,
    private val oneTapClient: SignInClient

) {
    private val auth = Firebase.auth

    suspend fun signIn(): IntentSender? {
        val request = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(context.getString(R.string.clientid))
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            .build()

        return oneTapClient.beginSignIn(request).await().pendingIntent.intentSender
    }

    suspend fun signInWithIntent(data: Intent): SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(data)
        val idToken = credential.googleIdToken
        val googleCredential = GoogleAuthProvider.getCredential(idToken, null)

        return try {
            val authResult = auth.signInWithCredential(googleCredential).await()
            val user = authResult.user!!
            SignInResult(
                UserData(user.uid, user.displayName!!, user.photoUrl.toString()),
                null
            )
        } catch (e: Exception) {
            SignInResult(null, e.message)
        }

    }

    suspend fun signOut() {
        oneTapClient.signOut().await()
        auth.signOut()
    }

    fun getSignInedUser(): UserData? {
        val user = auth.currentUser ?: return null
        return UserData(user.uid, user.displayName!!, user.photoUrl.toString())
    }


}