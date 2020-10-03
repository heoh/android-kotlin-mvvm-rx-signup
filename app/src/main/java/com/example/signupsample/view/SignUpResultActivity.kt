package com.example.signupsample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.signupsample.R
import com.example.signupsample.model.Gender
import com.example.signupsample.model.User
import kotlinx.android.synthetic.main.activity_sign_up_result.*
import java.time.format.DateTimeFormatter

class SignUpResultActivity : AppCompatActivity() {
    companion object {
        const val USER = "user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_result)

        val user = intent.getSerializableExtra(USER) as User
        updateFields(user)
    }

    private fun updateFields(user: User) {
        emailAddress.text = user.emailAddress
        password.text = user.password
        nickname.text = user.nickname
        birthDate.text = DateTimeFormatter.ofPattern("yyyy.MM.dd.").format(user.birthDate)
        gender.check(genderToId(user.gender))
        agreeTerms.isChecked = user.hasAgreedToTerms
        agreeMarketingTerms.isChecked = user.hasAgreedToMarketingTerms
    }

    private fun genderToId(gender: Gender): Int {
        return when (gender) {
            Gender.MALE -> genderMale.id
            Gender.FEMALE -> genderFemale.id
            Gender.NONE -> genderNone.id
        }
    }
}