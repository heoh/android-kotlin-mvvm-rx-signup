package com.example.signupsample.api

import com.example.signupsample.model.User
import com.example.signupsample.repository.UserRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

class UserAPI {
    companion object {
        fun create(user: User): Observable<Response<User>> {
            val delay = 2000L

            return Observable.just(user)
                .delay(delay, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .map { createUser(it) }

        }

        private fun createUser(user: User): Response<User> {
            if (user.age < 14) {
                return Response<User>(
                    statusCode = HttpURLConnection.HTTP_BAD_REQUEST,
                    message = "message_sign_up_failed_by_age"
                )
            }

            if (UserRepository.containsByEmail(user.emailAddress)) {
                return Response<User>(
                    statusCode = HttpURLConnection.HTTP_UNAUTHORIZED,
                    message = "message_sign_up_already_exists_user"
                )
            }

            UserRepository.add(user)
            return Response<User>(
                statusCode = HttpURLConnection.HTTP_OK,
                body = user
            )
        }
    }
}