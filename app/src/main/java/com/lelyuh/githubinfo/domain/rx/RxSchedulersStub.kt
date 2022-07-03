package com.lelyuh.githubinfo.domain.rx

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Schedulers for Unit tests
 *
 * @author Leliukh Aleksandr
 */
internal class RxSchedulersStub : RxSchedulers {

    override fun ui(): Scheduler = Schedulers.trampoline()

    override fun io(): Scheduler = Schedulers.trampoline()
}