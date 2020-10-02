package com.example.signupsample.model

import java.util.*

data class User(
    val emailAddress: String,
    val password: String,
    val nickname: String,
    val birthDate: Date,
    val gender: Gender,
    val hasAgreedToTerms: Boolean,
    val hasAgreedToMarketingTerms: Boolean
) {
}