package com.roomdb.testproject.topics

import kotlinx.coroutines.*

/**
 * Uses of Launch ===>
 *          Use launch in cases where fire and forget events.
 * Uses of Async ==>
 *          Use when we want desired result in the output.
 */

suspend fun longRunningTask() {
    println("Coroutine Started")
    for (i in 1..5) {
        delay(1000)
        println("This is running on ${Thread.currentThread()} -> $i")
    }
    println("Coroutine Ended")
}

fun main() = runBlocking {

    println("Hello world")
    println("Main Started")

    val job = launch {
        longRunningTask()
    }
    job.join()

    /**
     * This will return the Deferred value which is the result after job is done.
     */
    async {
        longRunningTask()
    }.join()

    /**
     * These Both methods are the same we can replace async with withContext(Dispatchers.Default)
     */
    async {
        longRunningTask()
    }.await()
    withContext(Dispatchers.Default) {
        longRunningTask()
    }

    println("Main Ended")
}