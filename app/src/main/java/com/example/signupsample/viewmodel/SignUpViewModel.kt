package com.example.signupsample.viewmodel

import androidx.lifecycle.ViewModel
import com.example.signupsample.model.Gender
import com.example.signupsample.util.SignUpFormValidator
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.*

class SignUpViewModel : ViewModel() {
    val isSubmittable: Observable<Boolean> = BehaviorSubject.createDefault(false)

    private val validator = SignUpFormValidator()
    private val disposables = CompositeDisposable()

    fun bind(
        emailAddress: Observable<CharSequence>,
        password: Observable<CharSequence>,
        passwordRetype: Observable<CharSequence>,
        nickname: Observable<CharSequence>,
        birthDate: Observable<Optional<Date>>,
        gender: Observable<Optional<Gender>>,
        agreeTerms: Observable<Boolean>,
        agreeMarketingTerms: Observable<Boolean>
    ) {
        disposables.addAll(
            emailAddress.subscribe { validator.emailAddress = it; update() },
            password.subscribe { validator.password = it; update() },
            passwordRetype.subscribe { validator.passwordRetype = it; update() },
            nickname.subscribe { validator.nickname = it; update() },
            birthDate.subscribe { validator.birthDate = it.orElse(null); update() },
            gender.subscribe { validator.gender = it.orElse(null); update() },
            agreeTerms.subscribe { validator.agreeTerms = it; update() },
            agreeMarketingTerms.subscribe { validator.agreeMarketingTerms = it; update() }
        )
    }

    fun unbind() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    private fun update() {
        if (isSubmittable is BehaviorSubject<Boolean>) {
            isSubmittable.onNext(validator.validate())
        }
    }
}