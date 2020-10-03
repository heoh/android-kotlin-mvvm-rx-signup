package com.example.signupsample.model

import java.time.LocalDate
import java.time.Period

data class User(
    val emailAddress: String,
    val password: String,
    val nickname: String,
    val birthDate: LocalDate,
    val gender: Gender,
    val hasAgreedToTerms: Boolean,
    val hasAgreedToMarketingTerms: Boolean
) {
    val age: Int
        get() {
            val period = Period.between(birthDate, LocalDate.now())
            return period.years
        }
}