package id.co.hendevane.universitas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private GoogleMap map;
    private HendevaneApi hendevaneApi;
    private HashMap<String, University> listUniversities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hendevaneApi = HendevaneApi.service.create(HendevaneApi.class);
        listUniversities = new HashMap<>();

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                map.getUiSettings().setZoomControlsEnabled(true);

                //camera without zoom
                //CameraUpdate camera = CameraUpdateFactory.newLatLng(new LatLng(-6.175372, 106.827221));

                CameraUpdate camera = CameraUpdateFactory.newLatLngZoom(new LatLng(-6.175372, 106.827221), 11);
                map.moveCamera(camera);

                showMarkers();

                map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        University university = listUniversities.get(marker.getId());

                        Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                        intent.putExtra("data", university);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    private void showMarkers() {
        Call<JsonArray> universitiesCall = hendevaneApi.universities();
        universitiesCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                JsonArray jsonArray = response.body();
                for (JsonElement jsonElement: jsonArray) {
                    JsonObject university = jsonElement.getAsJsonObject();

                    String name = university.get("name").getAsString();
                    JsonObject location = university.get("location").getAsJsonObject();
                    double latitude = location.get("latitude").getAsDouble();
                    double longitude = location.get("longitude").getAsDouble();
                    String address = university.get("address").getAsString();
                    String web = university.get("web").getAsString();

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(latitude, longitude));
                    markerOptions.title(name);
                    Marker marker = map.addMarker(markerOptions);

                    University univ = new University(name, address, web);
                    listUniversities.put(marker.getId(), univ);
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {}
        });
    }
}
