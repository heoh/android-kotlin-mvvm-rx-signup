package com.example.signupsample.view

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.signupsample.R
import com.example.signupsample.api.Response
import com.example.signupsample.model.Gender
import com.example.signupsample.model.User
import com.example.signupsample.viewmodel.SignUpViewModel
import com.example.signupsample.viewmodel.ViewModelFactory
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.checkedChanges
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

class SignUpActivity : AppCompatActivity() {
    private val viewModel: SignUpViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory()).get(SignUpViewModel::class.java)
    }
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
    }

    override fun onResume() {
        super.onResume()
        bindData()
    }

    override fun onPause() {
        unbindData()
        super.onPause()
    }

    private fun bindData() {
        viewModel.bind(
            emailAddress.textChanges(),
            password.textChanges(),
            passwordRetype.textChanges(),
            nickname.textChanges(),
            birthDate.textChanges().map { s -> Optional.ofNullable(stringToDate(s)) },
            gender.checkedChanges().map { id -> Optional.ofNullable(idToGender(id)) },
            agreeTerms.checkedChanges(),
            agreeMarketingTerms.checkedChanges()
        )

        disposables.addAll(
            viewModel.isSubmittable.subscribe { signUpButton.isEnabled = it },
            viewModel.submissionResponse.subscribe { handleSubmission(it) },
            birthDate.clicks().subscribe { showBirthDatePicker() },
            signUpButton.clicks().subscribe { viewModel.submit() }
        )
    }

    private fun unbindData() {
        viewModel.unbind()

        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    private fun showBirthDatePicker() {
        val now = LocalDate.now()
        val dialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
            val text = "%d.%02d.%02d.".format(year, month + 1, dayOfMonth)
            birthDate.setText(text)
        }, now.year, now.month.value - 1, now.dayOfMonth)
        dialog.show()
    }

    private fun handleSubmission(response: Response<User>) {
        Toast.makeText(this, "Response: " + response.statusCode, Toast.LENGTH_SHORT).show()
    }

    private fun stringToDate(s: CharSequence): LocalDate? {
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd.")
        return try {
            LocalDate.parse(s, formatter)
        } catch (e: DateTimeParseException) {
            null
        }
    }

    private fun idToGender(id: Int): Gender? {
        return when (id) {
            genderMale.id -> Gender.MALE
            genderFemale.id -> Gender.FEMALE
            genderNone.id -> Gender.NONE
            else -> null
        }
    }
}