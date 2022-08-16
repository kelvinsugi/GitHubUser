package com.kelvinsugiarto.gituserapp.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kelvinsugiarto.gituserapp.CoroutineTestRule
import com.kelvinsugiarto.gituserapp.data.api.AkseleranLoginApi
import com.kelvinsugiarto.gituserapp.data.model.CredentialModel
import com.kelvinsugiarto.gituserapp.data.network.FakeAkseleranAuthDataRepository
import com.kelvinsugiarto.gituserapp.data.network.BaseRepository
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class AkseleranAuthUseCaseImplTest : BaseRepository() {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val coroutinesTestRule: CoroutineTestRule = CoroutineTestRule()
    private val akseleranLoginApiService = Mockito.mock(AkseleranLoginApi::class.java)

    private val repositoryTest = FakeAkseleranAuthDataRepository(akseleranLoginApiService)

    /* Remote */
    @Test
    fun login(): Unit = runBlocking {
        repositoryTest.login(CredentialModel("",""))
        verify(akseleranLoginApiService).login("","")
    }

}