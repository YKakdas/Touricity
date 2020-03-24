package com.squadro.touricity.view.map;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.squadro.touricity.message.types.Path;
import com.squadro.touricity.message.types.PathVertex;
import com.squadro.touricity.message.types.Route;
import com.squadro.touricity.message.types.Stop;
import com.squadro.touricity.message.types.interfaces.IEntry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PolylineDrawer {

    private GoogleMap map;
    private MarkerOptions markerOptions;
    private PolylineOptions polylineOptions;
    private List<Polyline> polylines;
    private List<Marker> markers;

    private IEntry entry;

    public PolylineDrawer(GoogleMap map) {
        this.map = map;
        polylines = new ArrayList<>();
        markers = new ArrayList<>();
        markerOptions = new MarkerOptions();
        polylineOptions = new PolylineOptions();
    }

    public GoogleMap drawRoute(Route route) {

        clearMap();
        List<IEntry> entryList = route.getAbstractEntryList();
        Iterator iterator = entryList.iterator();

        while (iterator.hasNext()) {
            entry = (IEntry) iterator.next();

            if (entry instanceof Stop) {
                markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(((Stop) entry).getLocation().getLatitude(), ((Stop) entry).getLocation().getLongitude()));
                Marker marker = map.addMarker(markerOptions);
                marker.setZIndex(1);
                markers.add(marker);
            } else if (entry instanceof Path) {
                polylineOptions = new PolylineOptions();
                List<PathVertex> vertices = ((Path) entry).getVertices();
                for (int i = 0; i < vertices.size(); i++) {
                    polylineOptions.add(new LatLng(vertices.get(i).getLatitude(), vertices.get(i).getLongitude()));
                    Polyline polyline = map.addPolyline(polylineOptions);
                    polyline.setZIndex(1);
                    polylines.add(polyline);
                }
            }
        }
        return map;
    }

    public GoogleMap drawRoute(Route route, Stop stop) {

        clearMap();
        List<IEntry> entryList = route.getAbstractEntryList();
        Iterator iterator = entryList.iterator();

        while (iterator.hasNext()) {
            entry = (IEntry) iterator.next();

            if (entry instanceof Stop) {

                if (((Stop) entry).getLocation().getLatitude() == stop.getLocation().getLatitude() &&
                        ((Stop) entry).getLocation().getLongitude() == stop.getLocation().getLongitude()) {

                    markerOptions = new MarkerOptions();
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
                    markerOptions.position(new LatLng(((Stop) entry).getLocation().getLatitude(), ((Stop) entry).getLocation().getLongitude()));
                    Marker marker = map.addMarker(markerOptions);
                    marker.setZIndex(1);
                    markers.add(marker);
                } else {
                    markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(((Stop) entry).getLocation().getLatitude(), ((Stop) entry).getLocation().getLongitude()));
                    Marker marker = map.addMarker(markerOptions);
                    marker.setZIndex(1);
                    markers.add(marker);
                }

            } else if (entry instanceof Path) {
                polylineOptions = new PolylineOptions();
                List<PathVertex> vertices = ((Path) entry).getVertices();
                for (int i = 0; i < vertices.size(); i++) {
                    polylineOptions.add(new LatLng(vertices.get(i).getLatitude(), vertices.get(i).getLongitude()));
                    Polyline polyline = map.addPolyline(polylineOptions);
                    polyline.setZIndex(1);
                    polylines.add(polyline);
                }
            }
        }

        return map;
    }

    public GoogleMap drawRoute(Route route, Path path) {

        clearMap();

        List<IEntry> entryList = route.getAbstractEntryList();
        Iterator iterator = entryList.iterator();

        while (iterator.hasNext()) {
            entry = (IEntry) iterator.next();

            if (entry instanceof Stop) {
                markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng(((Stop) entry).getLocation().getLatitude(), ((Stop) entry).getLocation().getLongitude()));
                Marker marker = map.addMarker(markerOptions);
                marker.setZIndex(1);
                markers.add(marker);
            } else if (entry instanceof Path) {

                polylineOptions = new PolylineOptions();
                if (entry.equals(path)) {
                    List<PathVertex> vertices = ((Path) entry).getVertices();
                    for (int i = 0; i < vertices.size(); i++) {
                        polylineOptions.add(new LatLng(vertices.get(i).getLatitude(), vertices.get(i).getLongitude()));
                        polylineOptions.color(Color.BLUE);
                        Polyline polyline = map.addPolyline(polylineOptions);
                        polyline.setZIndex(1);
                        polylines.add(polyline);
                        polylineOptions.color(Color.BLACK);
                    }
                } else {
                    List<PathVertex> vertices = ((Path) entry).getVertices();
                    for (int i = 0; i < vertices.size(); i++) {
                        polylineOptions.add(new LatLng(vertices.get(i).getLatitude(), vertices.get(i).getLongitude()));
                        Polyline polyline = map.addPolyline(polylineOptions);
                        polyline.setZIndex(1);
                        polylines.add(polyline);
                    }
                }
            }
        }
        return map;
    }

    private void clearMap() {
        if (polylines.size() > 0) {
            for (Polyline polyline : polylines) {
                polyline.remove();
            }
            polylines.clear();
        }

        if (markers.size() > 0) {
            for (Marker marker : markers) {
                marker.remove();
            }
            markers.clear();
        }
    }
}