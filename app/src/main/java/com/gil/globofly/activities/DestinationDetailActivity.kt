package com.smartherd.globofly.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.gil.globofly.services.DestinationService
import com.gil.globofly.services.ServiceBuilder
import com.smartherd.globofly.R
import com.smartherd.globofly.helpers.SampleData
import com.smartherd.globofly.models.Destination
import kotlinx.android.synthetic.main.activity_destiny_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DestinationDetailActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_destiny_detail)

		setSupportActionBar(detail_toolbar)
		// Show the Up button in the action bar.
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		val bundle: Bundle? = intent.extras

		if (bundle?.containsKey(ARG_ITEM_ID)!!) {

			val id = intent.getIntExtra(ARG_ITEM_ID, 0)

			loadDetails(id)

			initUpdateButton(id)

			initDeleteButton(id)
		}
	}

	private fun loadDetails(id: Int) {

		// To be replaced by retrofit code
//		val destination = SampleData.getDestinationById(id)
//


		val destinationService : DestinationService = ServiceBuilder.buildService(DestinationService::class.java)
		val requestCall : Call<Destination> = destinationService.getDestination(id)

		requestCall.enqueue(object : Callback<Destination> {
			override fun onResponse(call: Call<Destination>, response: Response<Destination>) {

				if (response.isSuccessful){
					val destination : Destination? = response.body()
					destination?.let {
						et_city.setText(destination.city)
						et_description.setText(destination.description)
						et_country.setText(destination.country)

						collapsing_toolbar.title = destination.city
					}
				} else{
					Toast.makeText(this@DestinationDetailActivity , "Failed to retrive details" , Toast.LENGTH_SHORT).show()
				}
			}

			override fun onFailure(call: Call<Destination>, t: Throwable) {

			}
		})
	}

	private fun initUpdateButton(id: Int) {

		btn_update.setOnClickListener {

			val city = et_city.text.toString()
			val description = et_description.text.toString()
			val country = et_country.text.toString()

			val destinationService : DestinationService = ServiceBuilder.buildService(DestinationService::class.java)
			val requestCall : Call<Destination> = destinationService.updateDestination(id , city , description , country)

			requestCall.enqueue(object : Callback<Destination> {

				override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
					if (response.isSuccessful){

						finish()
						var updateDestination : Destination? = response.body()
						Toast.makeText(this@DestinationDetailActivity , "Item update succesfully" , Toast.LENGTH_SHORT).show()

					} else{
						Toast.makeText(this@DestinationDetailActivity , "Failed to update details" , Toast.LENGTH_SHORT).show()

					}
				}

				override fun onFailure(call: Call<Destination>, t: Throwable) {
					Toast.makeText(this@DestinationDetailActivity , "Failed to update details" , Toast.LENGTH_SHORT).show()

				}
			})

		}
	}

	private fun initDeleteButton(id: Int) {

		btn_delete.setOnClickListener {

			val destinationService : DestinationService = ServiceBuilder.buildService(DestinationService::class.java)
			val requestCall : Call<Unit> = destinationService.deleteDestination(id)

			requestCall.enqueue(object : Callback<Unit> {

				override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
					if (response.isSuccessful){
						finish()
						Toast.makeText(this@DestinationDetailActivity , "Item deleted" , Toast.LENGTH_SHORT).show()

					} else {
						Toast.makeText(this@DestinationDetailActivity , "Failed to delete item" , Toast.LENGTH_SHORT).show()

					}
				}

				override fun onFailure(call: Call<Unit>, t: Throwable) {
					Toast.makeText(this@DestinationDetailActivity , "Failed to delete item" , Toast.LENGTH_SHORT).show()

				}
			})


		}
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		val id = item.itemId
		if (id == android.R.id.home) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				navigateUpTo(Intent(this, DestinationListActivity::class.java))
			}
			return true
		}
		return super.onOptionsItemSelected(item)
	}

	companion object {

		const val ARG_ITEM_ID = "item_id"
	}
}
