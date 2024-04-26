package ru.mirea.karacheviv.osmmaps


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.preference.PreferenceManager
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import ru.mirea.karacheviv.osmmaps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mapView : MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mapView = binding.mapView
        Configuration.getInstance().load(applicationContext, PreferenceManager.getDefaultSharedPreferences(applicationContext))

        mapView.setZoomRounding(true)
        mapView.setMultiTouchControls(true)

        val mapController = mapView.controller
        mapController.setZoom(12)
        val startPoint = GeoPoint(55.670005, 37.479894)
        mapController.setCenter(startPoint)

        val locationNewOverlay = MyLocationNewOverlay(GpsMyLocationProvider(applicationContext), mapView)
        locationNewOverlay.enableMyLocation()
        mapView.overlays.add(locationNewOverlay)

        val compassOverlay = CompassOverlay(applicationContext, InternalCompassOrientationProvider(applicationContext), mapView)
        compassOverlay.enableCompass()
        mapView.overlays.add(compassOverlay)


        var dm = applicationContext.resources.displayMetrics

        val scaleBarOverLay = ScaleBarOverlay(mapView)
        scaleBarOverLay.setCentred(true)
        scaleBarOverLay.setScaleBarOffset(dm.widthPixels / 2, 10)
        mapView.overlays.add(scaleBarOverLay)

        val marker1 = Marker(mapView)
        marker1.position = GeoPoint(55.670005, 37.479894)
        mapView.overlays.add(marker1)
        marker1.icon = ResourcesCompat.getDrawable(resources, org.osmdroid.library.R.drawable.osm_ic_follow_me_on, null)
        marker1.title = "Проспект Вернадского"

        val marker2 = Marker(mapView)
        marker2.position = GeoPoint(55.197179, 37.901995)

        mapView.overlays.add(marker2)
        marker2.icon = ResourcesCompat.getDrawable(resources, org.osmdroid.library.R.drawable.osm_ic_center_map, null)
        marker2.title = "Я"


    }

    override fun onResume() {
        super.onResume()
        Configuration.getInstance().load(applicationContext, PreferenceManager.getDefaultSharedPreferences(applicationContext))

        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        Configuration.getInstance().load(applicationContext, PreferenceManager.getDefaultSharedPreferences(applicationContext))

        mapView.onPause()
    }
}