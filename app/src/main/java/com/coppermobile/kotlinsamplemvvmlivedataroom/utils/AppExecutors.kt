package com.coppermobile.kotlinsamplemvvmlivedataroom.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors private constructor(val diskIO: Executor, val networkIO: Executor, val mainThread: Executor) {

    companion object {
        private const val THREAD_COUNT = 3
    }

    constructor() : this(DiskIOThreadExecutor(), Executors.newFixedThreadPool(THREAD_COUNT), MainThreadExecutor())

    class DiskIOThreadExecutor : Executor {

        private val mDiskIO: Executor

        init {
            mDiskIO = Executors.newSingleThreadExecutor()
        }

        override fun execute(command: Runnable) {
            mDiskIO.execute(command)
        }
    }

    private class MainThreadExecutor : Executor {

        private var mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable?) {
            mainThreadHandler.post(command)
        }
    }
}