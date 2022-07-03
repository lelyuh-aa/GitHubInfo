package com.lelyuh.githubinfo.domain.rx

import io.reactivex.rxjava3.core.Scheduler

/**
 * Interface for different Rx-schedulers for async work
 *
 * @author Leliukh Aleksandr
 */
interface RxSchedulers {

    /**
     * Scheduler for launch in main thread
     */
    fun ui(): Scheduler

    /**
     * Scheduler for launch in worker thread
     */
    fun io(): Scheduler
}