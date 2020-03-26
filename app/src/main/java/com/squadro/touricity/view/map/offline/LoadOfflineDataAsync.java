package com.squadro.touricity.view.map.offline;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.squadro.touricity.message.types.Route;
import com.squadro.touricity.view.routeList.MyPlaceSave;
import com.squadro.touricity.view.routeList.SavedRouteView;
import com.squadro.touricity.view.routeList.SavedRoutesItem;
import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoadOfflineDataAsync extends AsyncTask<Void, Void, SavedRoutesItem> {

    private final File file;
    private SavedRouteView savedRouteView;
    private XStream xStream = null;
    private boolean isDelete;
    private Route routeToBeDeleted;

    public LoadOfflineDataAsync(SavedRouteView savedRouteView, File file,boolean isDelete,Route routeToBeDeleted) {
        this.savedRouteView = savedRouteView;
        this.file = file;
        this.isDelete = isDelete;
        this.routeToBeDeleted = routeToBeDeleted;
        xStream = new XStream();
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected SavedRoutesItem doInBackground(Void ... voids) {
        List<MyPlaceSave> placesFromFile = getPlacesFromFile(file);
        List<Route> routesFromFile = getRoutesFromFile(file);
        return new SavedRoutesItem(routesFromFile,placesFromFile);
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onPostExecute(SavedRoutesItem savedRoutesItem) {
        if(isDelete){
            List<Route> collect = savedRoutesItem.getRoutes().stream()
                    .filter(route1 -> !route1.getRoute_id().equals(routeToBeDeleted.getRoute_id()))
                    .collect(Collectors.toList());
            DeleteOfflineDataAsync deleteOfflineDataAsync = new DeleteOfflineDataAsync(file,savedRouteView);
            deleteOfflineDataAsync.execute(new SavedRoutesItem(collect,savedRoutesItem.getMyPlaces()));
        }else{
            savedRouteView.setRouteList(savedRoutesItem.getRoutes(),savedRoutesItem.getMyPlaces());
        }
    }

    private List<Route> getRoutesFromFile(File file) {
        if (file.length() == 0) return null;
        else {
            try {
                return ((SavedRoutesItem) xStream.fromXML(file)).getRoutes();
            } catch (Exception e) {
                return (ArrayList<Route>) (xStream.fromXML(file));
            }
        }
    }

    private List<MyPlaceSave> getPlacesFromFile(File file) {
        if (file.length() == 0) return null;
        else {
            try {
                return ((SavedRoutesItem) xStream.fromXML(file)).getMyPlaces();
            } catch (Exception e) {
                e.printStackTrace();
                return (ArrayList<MyPlaceSave>) (xStream.fromXML(file));
            }
        }
    }


}
