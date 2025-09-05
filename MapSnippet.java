package com.AppGps.AppGpsLocalization;

import android.graphics.Color;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import java.util.ArrayList;

public class MapSnippet {

    private GoogleMap mMap;

    public MapSnippet(GoogleMap map) {
        this.mMap = map;
    }

    /**
     * Agrega marcadores y una polilínea al mapa sin mostrar InfoWindows.
     * @param dataModels Arreglo con toda la información
     * @param dataLatitude latitudes en String
     * @param dataLongitude longitudes en String
     * @param polydata Polilínea
     */
    public void setMapLocation(
            ArrayList<DataListValuesModel> dataModels,
            ArrayList<DataListValuesModel> dataLatitude,
            ArrayList<DataListValuesModel> dataLongitude,
            ArrayList<LatLng> polydata
    ) {

        //Si hay un marker en el mapa se borra antes para colocar los puntos recorridos
        mMap.clear();

        //Se agrega polilínea con los puntos proporcionados
        mMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .addAll(polydata)
                .width(10)
                .color(Color.BLUE)
                .geodesic(true)
                .visible(true));

        LatLng firstLocation = null;

        //Iteramos sobre los puntos y agregamos los marcadores sin info windows
        for (int i = 0; i < dataModels.size(); i++) {
            double latitude = Double.parseDouble(dataLatitude.get(i).getValue());
            double longitude = Double.parseDouble(dataLongitude.get(i).getValue());
            LatLng location = new LatLng(latitude, longitude);

            //Iconos por defecto de Google
            BitmapDescriptor icon = (i == 0)
                    ? BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                    : BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);

            mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .icon(icon)
             //Sirve para que no se sobrepongan sobre el marker
                    .zIndex(i == 0 ? 1 : 0));

            //Guardar la primera ubicación para centrar el mapa
            if (i == 0) {
                firstLocation = location;
            }
        }

        //Centra el mapa en el primer marcador
        if (firstLocation != null) {
            mMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(firstLocation, 13.0f));
        }
    }
}