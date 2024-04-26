package ru.mirea.karacheviv.yandexmaps

import android.Manifest
import android.R
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CompositeIcon
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.RotationType
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider
import ru.mirea.karacheviv.yandexmaps.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), UserLocationObjectListener {
    private lateinit var binding : ActivityMainBinding
    private lateinit var mapView: MapView
    private lateinit var userLocationLayer : UserLocationLayer
    private var isPermission = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MapKitFactory.initialize(this)
        mapView = binding.mapview

        mapView.map.move(
            CameraPosition(Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0.0f),
            null)

        val locationPermissionStatus =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)

        if (locationPermissionStatus == PackageManager.PERMISSION_GRANTED) {
            isPermission = true
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf<String>(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), 100
            )
        }

        if (isPermission) loadUserLocationLayer()

    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
    }

    override fun onPointerCaptureChanged(hasCapture: Boolean) {
        super.onPointerCaptureChanged(hasCapture)
    }
    override fun onObjectAdded(p0: UserLocationView) {
        userLocationLayer.setAnchor(
            PointF((mapView.width * 0.5).toFloat(), (mapView.height * 0.5).toFloat()),
            PointF((mapView.width * 0.5).toFloat(), (mapView.height * 0.83).toFloat())
        )


        // При определении направления движения устанавливается следующая иконка
        // При определении направления движения устанавливается следующая иконка
        p0.arrow.setIcon(
            ImageProvider.fromResource(
                this, R.drawable.arrow_up_float
            )
        )

        // При получении координат местоположения устанавливается следующая иконка

        // При получении координат местоположения устанавливается следующая иконка
        val pinIcon: CompositeIcon = p0.pin.useCompositeIcon()
        pinIcon.setIcon(
            "pin",
            ImageProvider.fromResource(this, R.drawable.ic_menu_compass),
            IconStyle().setAnchor(PointF(0.5f, 0.5f))
                .setRotationType(RotationType.ROTATE)
                .setZIndex(1f)
                .setScale(0.5f)
        )

        p0.accuracyCircle.fillColor = Color.BLUE and -0x66000001

    }

    override fun onObjectRemoved(p0: UserLocationView) {
        TODO("Not yet implemented")
    }

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {
        val point = Point(p0.arrow.geometry.latitude, p0.arrow.geometry.longitude)
        mapView.map.move(
            CameraPosition(point, 15.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0.0f),
            null
        )
    }

    private fun loadUserLocationLayer(){
        val mapKit = MapKitFactory.getInstance()
        mapKit.resetLocationManagerToDefault()

        userLocationLayer = mapKit.createUserLocationLayer(mapView.mapWindow)
        userLocationLayer.isVisible = true
        userLocationLayer.isHeadingEnabled = true
        userLocationLayer.setObjectListener(this)

    }
}