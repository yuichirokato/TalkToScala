package jp.yujiro.app.talktoscala.action

import android.content.Context
import android.location.{Criteria, Location, LocationListener, LocationManager}
import android.os.Bundle

class LocateAction(context: Context, routingItem: T2SRoutingItem) {

  def startGettingLocationWith(updateBody: Location => Unit) = {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE).asInstanceOf[LocationManager]

    val criteria = new Criteria()

    criteria.setAccuracy(Criteria.ACCURACY_FINE);

    criteria.setPowerRequirement(Criteria.POWER_LOW);

    val provider = locationManager.getBestProvider(criteria, true);

    locationManager.requestLocationUpdates(provider, 0, 0, createLocationListner(context, locationManager, updateBody));
  }

  private def createLocationListner(context: Context, locationManager: LocationManager, body: Location => Unit): LocationListener = {
    new LocationListener {
      override def onProviderEnabled(provider: String) = {
        // Nop
      }

      override def onStatusChanged(provider: String, status: Int, extras: Bundle) = {
        // Nop
      }

      override def onLocationChanged(location: Location) = {
        body(location)
        locationManager.removeUpdates(this)
      }

      override def onProviderDisabled(provider: String) = {
        // Nop
      }
    }
  }

}
