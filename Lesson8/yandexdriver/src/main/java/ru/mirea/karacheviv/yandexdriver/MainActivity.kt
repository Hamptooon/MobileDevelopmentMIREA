package ru.mirea.karacheviv.yandexdriver

import android.Manifest
import android.R
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType
import com.yandex.mapkit.directions.DirectionsFactory
import com.yandex.mapkit.directions.driving.DrivingOptions
import com.yandex.mapkit.directions.driving.DrivingRoute
import com.yandex.mapkit.directions.driving.DrivingRouter
import com.yandex.mapkit.directions.driving.DrivingSession
import com.yandex.mapkit.directions.driving.VehicleOptions
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CompositeIcon
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.RotationType
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.Error
import com.yandex.runtime.image.ImageProvider
import com.yandex.runtime.network.NetworkError
import com.yandex.runtime.network.RemoteError
import ru.mirea.karacheviv.yandexdriver.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), UserLocationObjectListener, DrivingSession.DrivingRouteListener {
    private lateinit var binding : ActivityMainBinding
    private lateinit var mapView: MapView
    private lateinit var userLocationLayer : UserLocationLayer
    private var isPermission = false
    private var isPathComplete = false
    private  lateinit var userPoint : Point
    private var MAPKIT_API_KEY = "829b99db-dee5-4db1-a086-7864766ae376"
    private var ROUTE_START_LOCATION = Point(55.670005, 37.479894)
    private var ROUTE_END_LOCATION = Point(55.794229, 37.700772)
    private var SCREEN_CENTER = Point(
        (ROUTE_START_LOCATION.latitude + ROUTE_END_LOCATION.latitude) / 2,
        (ROUTE_START_LOCATION.longitude + ROUTE_END_LOCATION.longitude) / 2)
    private lateinit var mapObjects : MapObjectCollection
    private lateinit var drivingRouter : DrivingRouter
    private lateinit var drivingSession : DrivingSession
    private var colors = arrayOf(-0x10000, -0xff0100, 0x00FFBBBB, -0xffff01);
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MapKitFactory.initialize(this)
        mapView = binding.mapview
        mapView.map.isRotateGesturesEnabled = false
        mapView.map.move(
            CameraPosition(SCREEN_CENTER, 10.0f, 0.0f, 0.0f))

        drivingRouter = DirectionsFactory.getInstance().createDrivingRouter()
        mapObjects = mapView.map.mapObjects.addCollection()


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

        if (isPermission) {
            loadUserLocationLayer()

        }

    }

    private fun submitRequest(){
        userPoint?.let { point ->
            val drivingOptions = DrivingOptions()
            val vehicleOptions = VehicleOptions()

            drivingOptions.routesCount = 1

            val requestPoints = mutableListOf(
                RequestPoint(point, RequestPointType.WAYPOINT, null),
                RequestPoint(ROUTE_END_LOCATION, RequestPointType.WAYPOINT, null)
            )

            drivingSession = drivingRouter.requestRoutes(requestPoints, drivingOptions, vehicleOptions, this)
        }
        mapView.map.mapObjects.addPlacemark(ROUTE_END_LOCATION).addTapListener { mapObject, point ->
            Toast.makeText(getApplication(),"РТУ МИРЭА, Стромынка 20", Toast.LENGTH_SHORT).show();
            return@addTapListener false;
        }
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

    private fun loadUserLocationLayer(){
        val mapKit = MapKitFactory.getInstance()
        mapKit.resetLocationManagerToDefault()

        userLocationLayer = mapKit.createUserLocationLayer(mapView.mapWindow)
        userLocationLayer.isVisible = true
        userLocationLayer.isHeadingEnabled = false

        userLocationLayer.setObjectListener(this)

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


        val fusedLocation = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocation.lastLocation.addOnCompleteListener {
            val location = it.result
            if(location == null){
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show();
            }
            else{
                userPoint = Point(location.latitude, location.longitude)
                submitRequest()
            }
        }

    }

    override fun onObjectRemoved(p0: UserLocationView) {
        TODO("Not yet implemented")
    }

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {


    }

    override fun onDrivingRoutes(p0: MutableList<DrivingRoute>) {
        var color: Int
        Toast.makeText(this, p0.size.toString(), Toast.LENGTH_SHORT).show()
        for (i in 0 until p0.size) {
            // настроиваем цвета для каждого маршрута
            color = colors[i]
            // добавляем маршрут на карту
            mapObjects.addPolyline(p0[i].geometry).setStrokeColor(color)
        }
    }

    override fun onDrivingRoutesError(p0: Error) {
        var errorMessage = "unknown_error_message"
        if (p0 is RemoteError) {
            errorMessage = "remote_error_message"
        } else if (p0 is NetworkError) {
            errorMessage = "network_error_message"
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }
}