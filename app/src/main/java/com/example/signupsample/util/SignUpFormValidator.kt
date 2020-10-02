package com.example.signupsample.util

import android.util.Patterns
import com.example.signupsample.model.Gender
import java.util.*

class SignUpFormValidator(
    var emailAddress: CharSequence = "",
    var password: CharSequence = "",
    var passwordRetype: CharSequence = "",
    var nickname: CharSequence = "",
    var birthDate: Date? = null,
    var gender: Gender? = null,
    var agreeTerms: Boolean = false,
    var agreeMarketingTerms: Boolean = false
) {
    fun validateEmailAddress(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()
    }

    fun validatePassword(): Boolean {
        val pattern = """^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{8,}).*$""".toRegex()
        return pattern.matches(password)
    }

    fun validatePasswordRetype(): Boolean {
        return password.toString().contentEquals(passwordRetype)
    }

    fun validateNickname(): Boolean {
        val pattern = """^(?=.{8,30}).*$""".toRegex()
        return pattern.matches(nickname)
    }

    fun validateBirthDate(): Boolean {
        return birthDate != null
    }

    fun validateGender(): Boolean {
        return gender != null
    }

    fun validateAgreeTerms(): Boolean {
        return agreeTerms
    }

    fun validateAgreeMarketingTerms(): Boolean {
        return true
    }

    // TODO: validate() 메서드가 무거움.
    //       항상 모든 필드에 대한 검사를 하기 때문.
    //       변경된 필드에 대해서만 재계산 하도록 경량화 필요함.
    fun validate(): Boolean {
        return (validateEmailAddress() &&
                validatePassword() &&
                validatePasswordRetype() &&
                validateNickname() &&
                validateBirthDate() &&
                validateGender() &&
                validateAgreeTerms() &&
                validateAgreeMarketingTerms())
    }
}