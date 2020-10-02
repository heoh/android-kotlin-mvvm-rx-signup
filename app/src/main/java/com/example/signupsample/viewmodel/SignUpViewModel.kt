package com.example.signupsample.viewmodel

import androidx.lifecycle.ViewModel
import com.example.signupsample.model.Gender
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.*

class SignUpViewModel : ViewModel() {
    val isSubmittable: Observable<Boolean> = BehaviorSubject.createDefault(false)

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
        // TODO: 입력 이벤트 처리
    }

    fun unbind() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }
}