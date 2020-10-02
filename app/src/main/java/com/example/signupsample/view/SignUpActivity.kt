package com.example.signupsample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.signupsample.R
import com.example.signupsample.viewmodel.SignUpViewModel
import com.example.signupsample.viewmodel.ViewModelFactory

class SignUpActivity : AppCompatActivity() {
    private val viewModel : SignUpViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory()).get(SignUpViewModel::class.java)
    }

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
        // TODO: Field 값이 변할 때 ViewModel에 알려줌

        // TODO: ViewModel로 부터 값을 읽어 UI 갱신
    }

    private fun unbindData() {
        // TODO: 누수 방지를 위해 바인딩 해제
    }
}