package com.example.signupsample.model

import java.time.LocalDate

data class User(
    val emailAddress: String,
    val password: String,
    val nickname: String,
    val birthDate: LocalDate,
    val gender: Gender,
    val hasAgreedToTerms: Boolean,
    val hasAgreedToMarketingTerms: Boolean
) {
}