package com.vs.utils

import io.reactivex.subjects.BehaviorSubject

/**
 * Created by Sachin
 */
object RxBus {

    val playClicked = BehaviorSubject.create<Boolean>()
    val historyClicked = BehaviorSubject.create<Boolean>()
}