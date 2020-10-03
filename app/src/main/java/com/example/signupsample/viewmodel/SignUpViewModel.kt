package com.example.signupsample.viewmodel

import androidx.lifecycle.ViewModel
import com.example.signupsample.api.Response
import com.example.signupsample.api.UserAPI
import com.example.signupsample.model.Gender
import com.example.signupsample.model.User
import com.example.signupsample.util.SignUpFormValidator
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import java.time.LocalDate
import java.util.*

class SignUpViewModel : ViewModel() {
    val isSubmittable: Observable<Boolean> = BehaviorSubject.createDefault(false)
    val submissionResponse: Observable<Response<User>> = PublishSubject.create<Response<User>>()

    private val fields = SignUpFormValidator()
    private val disposables = CompositeDisposable()

    fun bind(
        emailAddress: Observable<CharSequence>,
        password: Observable<CharSequence>,
        passwordRetype: Observable<CharSequence>,
        nickname: Observable<CharSequence>,
        birthDate: Observable<Optional<LocalDate>>,
        gender: Observable<Optional<Gender>>,
        agreeTerms: Observable<Boolean>,
        agreeMarketingTerms: Observable<Boolean>
    ) {
        disposables.addAll(
            emailAddress.subscribe { fields.emailAddress = it; update() },
            password.subscribe { fields.password = it; update() },
            passwordRetype.subscribe { fields.passwordRetype = it; update() },
            nickname.subscribe { fields.nickname = it; update() },
            birthDate.subscribe { fields.birthDate = it.orElse(null); update() },
            gender.subscribe { fields.gender = it.orElse(null); update() },
            agreeTerms.subscribe { fields.agreeTerms = it; update() },
            agreeMarketingTerms.subscribe { fields.agreeMarketingTerms = it; update() }
        )
    }

    fun unbind() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    fun submit() {
        check(fields.validate())
        check(fields.birthDate != null)
        check(fields.gender != null)

        val user = User(
            emailAddress = fields.emailAddress.toString(),
            password = fields.password.toString(),
            nickname = fields.toString(),
            birthDate = fields.birthDate!!,
            gender = fields.gender!!,
            hasAgreedToTerms = fields.agreeTerms,
            hasAgreedToMarketingTerms = fields.agreeMarketingTerms
        )

        UserAPI.create(user).observeOn(AndroidSchedulers.mainThread()).subscribe {
            if (submissionResponse is PublishSubject<Response<User>>) {
                submissionResponse.onNext(it)
            }
        }
    }

    private fun update() {
        if (isSubmittable is BehaviorSubject<Boolean>) {
            isSubmittable.onNext(fields.validate())
        }
    }
}