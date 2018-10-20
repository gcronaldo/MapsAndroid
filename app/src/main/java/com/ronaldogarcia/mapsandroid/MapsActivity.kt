package com.ronaldogarcia.mapsandroid

import android.location.Geocoder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    private fun addMarcador(latLng: LatLng, titulo: String) {
        mMap.addMarker(MarkerOptions().position(latLng).title(titulo))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val fiapPaulista = LatLng(-23.563814, -46.652442)
        val fiapAclimacao = LatLng(-23.571913, -46.623336)
        val fiapVilaOlimpia = LatLng(-23.595060, -46.685333)

        mMap.setOnMapClickListener {
            val geocoder = Geocoder(applicationContext, Locale.getDefault())
            val endereco = geocoder.getFromLocation(it.latitude, it.longitude, 1)
            addMarcador(it, endereco[0].thoroughfare)
        }

        mMap.setOnMapLongClickListener {
            addMarcador(it, getSnippet(it))
        }

        mMap.addMarker(MarkerOptions()
                .position(fiapPaulista)
                .title("Fiap Paulista")
                .snippet(getSnippet(fiapPaulista))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)))
        mMap.addMarker(MarkerOptions()
                .position(fiapAclimacao)
                .title("Fiap Aclimação")
                .snippet(getSnippet(fiapAclimacao))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.house))
        )
        mMap.addMarker(MarkerOptions()
                .position(fiapVilaOlimpia)
                .title("Fiap Vila Olimpia")
                .snippet(getSnippet(fiapVilaOlimpia)))

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fiapPaulista, 12f))



    }

    private fun getSnippet(latLng: LatLng): String {
        val geocoder = Geocoder(applicationContext, Locale.getDefault())
        val endereco = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

        return "${endereco[0].thoroughfare}, ${endereco[0].subThoroughfare} " +
                "${endereco[0].subLocality}, ${endereco[0].locality} - " +
                "${endereco[0].postalCode}"
    }
}
