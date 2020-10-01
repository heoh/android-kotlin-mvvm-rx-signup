package com.example.signupsample.model

import java.util.*

data class User(
    val email: String,
    val password: String,
    val nickname: String,
    val birthDate: Date,
    val gender: Gender,
    val hasAcceptedTerms: Boolean,
    val hasAcceptedMarketingTerms: Boolean
) {
}