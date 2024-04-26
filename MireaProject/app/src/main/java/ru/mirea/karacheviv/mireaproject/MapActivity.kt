package ru.mirea.karacheviv.mireaproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.preference.PreferenceManager
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import ru.mirea.karacheviv.mireaproject.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapBinding
    private lateinit var mapView : MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        binding = ActivityMapBinding.inflate(layoutInflater)
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

        val homeMarker1 = Marker(mapView)
        homeMarker1.position = GeoPoint(50.418926 , 80.262210)
        mapView.overlays.add(homeMarker1)
        homeMarker1.icon = ResourcesCompat.getDrawable(resources, org.osmdroid.library.R.drawable.osm_ic_follow_me_on, null)
        homeMarker1.title = "Семипалатинск, 'Три Богатыря'"

        val homeMarker2 = Marker(mapView)
        homeMarker2.position = GeoPoint(54.926473, 73.471215)
        mapView.overlays.add(homeMarker2)
        homeMarker2.icon = ResourcesCompat.getDrawable(resources, org.osmdroid.library.R.drawable.osm_ic_follow_me_on, null)
        homeMarker2.title = "Омск, Старая Московка"

        val homeMarker3 = Marker(mapView)
        homeMarker3.position = GeoPoint(55.197217,  37.902075)
        mapView.overlays.add(homeMarker3)
        homeMarker3.icon = ResourcesCompat.getDrawable(resources, org.osmdroid.library.R.drawable.osm_ic_center_map, null)
        homeMarker3.title = "Вельяминово, кп. Ртищево"

        val homeMarker4 = Marker(mapView)
        homeMarker4.position = GeoPoint(55.701991,37.904591)
        mapView.overlays.add(homeMarker4)
        homeMarker4.icon = ResourcesCompat.getDrawable(resources, org.osmdroid.library.R.drawable.osm_ic_follow_me_on, null)
        homeMarker4.title = "Люберцы, Лухмановская"

        val homeMarker5 = Marker(mapView)
        homeMarker5.position = GeoPoint(55.801061,  37.805677)

        mapView.overlays.add(homeMarker5)
        homeMarker5.icon = ResourcesCompat.getDrawable(resources, org.osmdroid.library.R.drawable.osm_ic_follow_me_on, null)
        homeMarker5.title = "Измайлово, Первомайская"
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