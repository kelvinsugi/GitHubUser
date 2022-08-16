package com.kelvinsugiarto.gituserapp

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description as Description1

@ExperimentalCoroutinesApi
class CoroutineTestRule(private val dispatcher: CoroutineDispatcher = TestCoroutineDispatcher()) : TestWatcher() {

    override fun starting(description: Description1?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description1?) {
        super.finished(description)
        Dispatchers.resetMain()
    }

}