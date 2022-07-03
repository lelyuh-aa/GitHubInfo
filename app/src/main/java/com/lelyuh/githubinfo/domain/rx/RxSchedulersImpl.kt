package com.lelyuh.githubinfo.domain.rx

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Schedulers for main work
 *
 * @author Leliukh Aleksandr
 */
internal class RxSchedulersImpl : RxSchedulers {

    override fun ui(): Scheduler = AndroidSchedulers.mainThread()

    override fun io(): Scheduler = Schedulers.io()
}