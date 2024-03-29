package com.smartherd.globofly.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.gil.globofly.services.DestinationService
import com.gil.globofly.services.ServiceBuilder
import com.smartherd.globofly.R
import com.smartherd.globofly.helpers.SampleData
import com.smartherd.globofly.models.Destination
import kotlinx.android.synthetic.main.activity_destiny_create.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationCreateActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_destiny_create)

		setSupportActionBar(toolbar)
		val context = this

		// Show the Up button in the action bar.
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		btn_add.setOnClickListener {
			val newDestination = Destination()
			newDestination.city = et_city.text.toString()
			newDestination.description = et_description.text.toString()
			newDestination.country = et_country.text.toString()

			val destinationService : DestinationService = ServiceBuilder.buildService(DestinationService::class.java)
			val requestCall : Call<Destination> = destinationService.addDestination(newDestination)

			requestCall.enqueue(object : Callback<Destination> {

				override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
					if (response.isSuccessful){

						finish() // move back to DestinationActivity
						var newlyCreatedDestination : Destination? = response.body()
						Toast.makeText(this@DestinationCreateActivity , "Succefully added" , Toast.LENGTH_SHORT).show()


					} else {
						Toast.makeText(this@DestinationCreateActivity , "Failed to add items" , Toast.LENGTH_SHORT).show()
					}
				}

				override fun onFailure(call: Call<Destination>, t: Throwable) {
					Toast.makeText(this@DestinationCreateActivity , "Failed to add items" , Toast.LENGTH_SHORT).show()
				}

			})


		}
	}
}
