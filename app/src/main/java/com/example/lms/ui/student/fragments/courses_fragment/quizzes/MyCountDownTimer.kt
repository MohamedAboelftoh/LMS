package com.example.lms.ui.student.fragments.courses_fragment.quizzes

import android.os.CountDownTimer
object MyCountDownTimer {
    private var instance: CountDownTimer? = null
    fun getInstance(
        millisInFuture: Long,
        countDownInterval: Long,
        onTick: (Long) -> Unit,
        onFinish: () -> Unit
    ): CountDownTimer {
        synchronized(this) {
            if (instance == null) {
                instance = object : CountDownTimer(millisInFuture, countDownInterval) {
                    override fun onTick(millisUntilFinished: Long) {
                        onTick.invoke(millisUntilFinished)
                    }

                    override fun onFinish() {
                        onFinish.invoke()
                        instance = null // Reset the instance after finish
                    }
                }
            }
            return instance!!
        }
    }
    fun cancelTimer() {
        instance?.cancel()
        instance = null // Reset the instance after cancel
    }
}