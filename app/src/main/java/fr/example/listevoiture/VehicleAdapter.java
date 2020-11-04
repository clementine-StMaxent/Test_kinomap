package fr.example.listevoiture;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class VehicleAdapter extends ArrayAdapter<Vehicle> {
    public VehicleAdapter(@NonNull Context context, int resource, @NonNull List<Vehicle> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.vehicle_cell,parent,false);
        }
        /*
         * nom et icone du vehicule
         */
        VehicleHolder vehicleHolder = new VehicleHolder();
        vehicleHolder.vehicleName = convertView.findViewById(R.id.vehicleName);
        vehicleHolder.vehicleIcon = convertView.findViewById(R.id.vehicleIcon);

        // position de l'item nom
        Vehicle currentVehicle = getItem(position);
        vehicleHolder.vehicleName.setText(currentVehicle.getName());
        Picasso.Builder builder = new Picasso.Builder(getContext());

        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                Log.e("Picasso Error", "Failed to load image: " + uri, exception);
            }
        });

        //accéder à l'URL image en remplaçant le HTTPS en HTTP
        builder.build().load(currentVehicle.getIcon().replace("https","http")).placeholder(R.drawable.ic_launcher_background).into(vehicleHolder.vehicleIcon);
        return convertView;
    }

    private class VehicleHolder {
        public TextView vehicleName;
        public ImageView vehicleIcon;
    }
}
