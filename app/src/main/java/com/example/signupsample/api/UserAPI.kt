package com.example.signupsample.api

import com.example.signupsample.model.User
import io.reactivex.rxjava3.core.Observable
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

class UserAPI {
    companion object {
        fun create(user: User): Observable<Response<User>> {
            val delay = 2000L

            return Observable.create<Response<User>> {
                it.onNext(Response<User>(statusCode = HttpURLConnection.HTTP_OK, body = user))
                it.onComplete()
            }.delay(delay, TimeUnit.MILLISECONDS)
        }
    }
}