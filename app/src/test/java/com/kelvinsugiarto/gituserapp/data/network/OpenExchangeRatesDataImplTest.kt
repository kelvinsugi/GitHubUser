package com.kelvinsugiarto.gituserapp.data.network

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.GsonBuilder
import com.kelvinsugiarto.gituserapp.MockReturnValueTest
import com.kelvinsugiarto.gituserapp.MockReturnValueTest.ALL_CURRENCIES_LIST
import com.kelvinsugiarto.gituserapp.data.api.OpenExchangeRatesAPI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class OpenExchangeRatesDataImplTest{
    private val mockWebServer = MockWebServer()
    private lateinit var mockedResponse: String

    @Mock
    lateinit var openExchangeRatesUseCase : OpenExchangeRatesDataImpl


    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val gson = GsonBuilder()
        .setLenient()
        .create()


    @Before
    fun setUp() {
        mockWebServer.start(8000)


        val client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .build()

         val api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenExchangeRatesAPI::class.java)

        openExchangeRatesUseCase = OpenExchangeRatesDataImpl(api)

    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should fetch currencyList correctly given 200 response`() {
        mockedResponse = MockReturnValueTest.getResponseFromFile(ALL_CURRENCIES_LIST)
        mockWebServer.enqueue(MockResponse()
            .setResponseCode(200)
            .setBody(mockedResponse)
        )

        val response = runBlocking { openExchangeRatesUseCase.getCurrencyList() }
        val expectedresponse = MockReturnValueTest.getCurrencyListDummy()


        Assert.assertNotNull(response)
        Assert.assertEquals(expectedresponse, response)

//        Assert.assertTrue(resultResponse.equals(expectedresponse))
    }


}