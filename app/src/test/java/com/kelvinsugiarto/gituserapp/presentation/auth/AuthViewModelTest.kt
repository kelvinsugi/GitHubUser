package com.kelvinsugiarto.gituserapp.presentation.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kelvinsugiarto.gituserapp.CoroutineTestRule
import com.kelvinsugiarto.gituserapp.InstantExecutorExtension
import com.kelvinsugiarto.gituserapp.data.model.CredentialModel
import com.kelvinsugiarto.gituserapp.data.model.DataResult
import com.kelvinsugiarto.gituserapp.data.model.SuccessResponse
import com.kelvinsugiarto.gituserapp.data.model.UnauthorizedResponse
import com.kelvinsugiarto.gituserapp.domain.AkseleranAuthUseCaseImpl
import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flow
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class AuthViewModelTest {

    lateinit var authUseCaseImpl: AkseleranAuthUseCaseImpl

    private lateinit var viewModel: AuthViewModel

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = CoroutineTestRule()

    @Before
    fun setUp() {
        authUseCaseImpl = mockk()
        viewModel = AuthViewModel(authUseCaseImpl)
    }

    @Test
    fun `login Success`() {
        // Let's do an answer for the liveData
        val email = "test@gmail.com"
        val password = "T3st!T3st!"
        val successResponse = SuccessResponse()
        successResponse.access_token = "1234567"
        successResponse.expired_date = "0"
        val dataResultSuccess = DataResult.Success(successResponse)

        //1- Mock calls
        coEvery { authUseCaseImpl.login(CredentialModel(email,password)) } returns DataResult.Success(successResponse)

        //2-Call
        viewModel.login(email, password)
        //active observer for livedata
        viewModel.loginLiveData.observeForever { }

        //3-verify
        val loginSuccess = viewModel.loginLiveData.value
        assertEquals(loginSuccess, dataResultSuccess)
    }

    @Test
    fun `login with Wrong Password`() {
        // Let's do an answer for the liveData
        val email = "test@gmail.com"
        val password = "1234567"
        val unauthorizedResponse = UnauthorizedResponse()
        unauthorizedResponse.message = "1234567"
        unauthorizedResponse.path = "/login"
        unauthorizedResponse.error = "unauthorized"
        val dataResultUnauthorized = DataResult.Unauthorized(unauthorizedResponse)


        //1- Mock calls
        coEvery {  authUseCaseImpl.login(CredentialModel(email,password)) } returns dataResultUnauthorized

        //2-Call
        viewModel.login(email, password)
        //active observer for livedata
        viewModel.loginLiveData.observeForever { }

        //3-verify
        val loginFail = viewModel.loginLiveData.value
        assertEquals(loginFail, dataResultUnauthorized)
    }


}