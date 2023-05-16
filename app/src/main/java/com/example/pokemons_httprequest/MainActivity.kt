package com.example.pokemons_httprequest

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Connect to an api
        // 2. Receive and send a data
        // 3. Print it

        // httpsUrlConnection, Volley, Retrofit


        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(PokemonService::class.java)

        apiService.getPokemonFromServer().enqueue(object : Callback<PokemonResponse> {

            override fun onResponse(
                call: Call<PokemonResponse>,
                response: Response<PokemonResponse>
            ) {
                val pokemonList = response.body()?.pokemons
                pokemonList?.forEach{
                    Toast.makeText(this@MainActivity, it.name, Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }
}

interface PokemonService {

    @GET("api/v2/pokemon")
    fun getPokemonFromServer(): Call<PokemonResponse>
}

//data class Pokemon(val name: String)

data class PokemonResponse(
    @SerializedName("count") val count: Int?,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val prev: String?,
    @SerializedName("results") val pokemons: List<Pokemon>?
)

data class Pokemon(
    @SerializedName("name") val name: String?,
    @SerializedName("url") val url: String?
)