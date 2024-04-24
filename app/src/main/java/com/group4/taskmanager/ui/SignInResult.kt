package com.group4.taskmanager.ui

import com.group4.taskmanager.data.UserData

data class SignInResult (
    val data: UserData?,
    val errorMessage: String?
)