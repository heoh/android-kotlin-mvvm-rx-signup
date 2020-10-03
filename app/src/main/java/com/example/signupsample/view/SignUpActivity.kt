package com.example.signupsample.view

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.signupsample.R
import com.example.signupsample.api.Response
import com.example.signupsample.model.Gender
import com.example.signupsample.model.User
import com.example.signupsample.repository.UserRepository
import com.example.signupsample.viewmodel.SignUpViewModel
import com.example.signupsample.viewmodel.ViewModelFactory
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.checkedChanges
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.net.HttpURLConnection
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

class SignUpActivity : AppCompatActivity() {
    private val viewModel: SignUpViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory()).get(SignUpViewModel::class.java)
    }
    private var disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        UserRepository.init(this.applicationContext)
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
            confirmPassword.textChanges(),
            nickname.textChanges(),
            birthDate.textChanges().map { s -> Optional.ofNullable(stringToDate(s)) },
            gender.checkedChanges().map { id -> Optional.ofNullable(idToGender(id)) },
            agreeTerms.checkedChanges(),
            agreeMarketingTerms.checkedChanges()
        )

        disposables.addAll(
            viewModel.isSubmittable.subscribe { updateSubmittableState(it) },
            viewModel.isLoading.subscribe { updateLoadingState(it) },
            viewModel.submissionResponse.subscribe { handleSubmission(it) },
            birthDate.clicks().subscribe { showBirthDatePicker() },
            submitButton.clicks().subscribe { viewModel.submit() }
        )
    }

    private fun unbindData() {
        disposables.clear()
        viewModel.unbind()
    }

    private fun showBirthDatePicker() {
        val now = LocalDate.now()
        val dialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
            val text = "%d.%02d.%02d.".format(year, month + 1, dayOfMonth)
            birthDate.setText(text)
        }, now.year, now.month.value - 1, now.dayOfMonth)
        dialog.show()
    }

    private fun updateSubmittableState(submittable: Boolean) {
        submitButton.isEnabled = submittable
    }

    private fun updateLoadingState(loading: Boolean) {
        when (loading) {
            true -> loadingScreen.visibility = View.VISIBLE
            false -> loadingScreen.visibility = View.GONE
        }
    }

    private fun handleSubmission(response: Response<User>) {
        if (response.statusCode == HttpURLConnection.HTTP_OK) {
            response.body?.let { showUserInfo(it) }
        } else {
            response.message?.let { showMessage(getStringByName(it)) }
        }
    }

    private fun showUserInfo(user: User) {
        val intent = Intent(this, SignUpResultActivity::class.java).apply {
            putExtra(SignUpResultActivity.USER, user)
        }
        startActivity(intent)
    }

    private fun showMessage(message: CharSequence) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun getStringByName(name: CharSequence): CharSequence {
        val resId = resources.getIdentifier(name.toString(), "string", packageName)
        return getString(resId)
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