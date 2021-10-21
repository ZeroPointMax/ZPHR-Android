package de.zeropointmax.zphr.ui.system;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import de.zeropointmax.zphr.ApiService;
import de.zeropointmax.zphr.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SystemFragment extends Fragment {

    //TODO: restore saved state if available
    ApiService apiService;
    String backendUri;
    SharedPreferences connectionSettings;
    EditText inputUri;
    ImageButton uriRefreshButton;
    ImageButton uriSetterButton;
    Button buttonReboot;
    Button buttonShutdown;

    void loadBackendUri() {
        backendUri = connectionSettings.getString(getString(R.string.pref_conn_settings), "");
        inputUri.setText(backendUri);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_system, container, false);
        Context context = getActivity();
        assert context != null; // i've heard using asserts in prod is sexy; greetings to Prof. Dr. Kusche
        connectionSettings = context.getSharedPreferences(getString(R.string.pref_conn_settings), Context.MODE_PRIVATE);
        inputUri = root.findViewById(R.id.input_uri);
        uriRefreshButton = root.findViewById(R.id.button_uri_refresh);
        uriSetterButton = root.findViewById(R.id.button_uri_set);
        buttonReboot = root.findViewById(R.id.button_reboot);
        buttonShutdown = root.findViewById(R.id.button_shutdown);

        loadBackendUri();

        try {
            apiService = new Retrofit.Builder()
                    .baseUrl(connectionSettings.getString(getString(R.string.pref_conn_settings), "http://127.0.0.1/"))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService.class);
        } catch (IllegalArgumentException e) { // example: no "http://" in URL
            //TODO: handle this properly!
            ApiService apiService = new Retrofit.Builder() // connect to some bullshit to not crash the app; FIXME
                    .baseUrl("http://127.0.0.1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService.class);
            e.printStackTrace();
        }

        AlertDialog.Builder alarmbauerShutdown = new AlertDialog.Builder(context) // ALAAARM
                .setTitle(R.string.app_name)
                .setMessage(R.string.system_alert_shutdown)
                .setIcon(R.drawable.ic_launcher_foreground)
                // if user is sure to reboot, reboot.
                .setPositiveButton(R.string.yay, new DialogInterface.OnClickListener() { // now this is getting out of hand
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        apiService.reboot().enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {

                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {

                            }
                        });
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.nay, new DialogInterface.OnClickListener() { // if user is not sure, do nothing
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog.Builder alarmbauerReboot = new AlertDialog.Builder(context)
                .setTitle(R.string.app_name)
                .setMessage(R.string.system_alert_reboot)
                .setIcon(R.drawable.ic_launcher_foreground)
                // if user is sure to reboot, reboot.
                .setPositiveButton(R.string.yay, new DialogInterface.OnClickListener() { // now this is getting out of hand
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        apiService.reboot().enqueue(new Callback<Integer>() {
                            @Override
                            public void onResponse(Call<Integer> call, Response<Integer> response) {
                                //TODO: Toast success
                            }

                            @Override
                            public void onFailure(Call<Integer> call, Throwable t) {
                                //TODO: Toast error
                            }
                        });
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.nay, new DialogInterface.OnClickListener() { // if user is not sure, do nothing
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        uriRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadBackendUri();
            }
        });
        uriSetterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor connectionSettingsEditor = connectionSettings.edit();
                connectionSettingsEditor.putString(getString(R.string.pref_conn_settings),
                        inputUri.getText().toString());
                connectionSettingsEditor.apply();
            }
        });
        buttonReboot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alaaarmReboot = alarmbauerReboot.create();
                alaaarmReboot.show();
            }
        });

        buttonShutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alaaarmShutdown = alarmbauerShutdown.create();
                alaaarmShutdown.show();
            }
        });
        /*
        final TextView textView = root.findViewById(R.id.text_dashboard);
        systemViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        */
        return root;
    }
}