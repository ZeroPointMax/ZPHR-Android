package de.zeropointmax.zphr.ui.system;

import android.app.AlertDialog;
import android.content.Context;
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
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static de.zeropointmax.zphr.RetrofitUtilities.initializeRetrofit;

/**
 * Logic of the System Fragment is defined here
 */
public class SystemFragment extends Fragment {

    //TODO: restore savedInstanceState if available
    ApiService apiService;
    String backendUri;
    SharedPreferences connectionSettings;
    EditText inputUri;
    ImageButton uriRefreshButton;
    ImageButton uriSetterButton;
    Button buttonReboot;
    Button buttonShutdown;

    /**
     * Loads the backend URI from the app's private storage, but loads an empty string if it is not available.
     * (yes, this might cause a Retrofit2 IAE later)
     */
    void loadBackendUri() {
        backendUri = connectionSettings.getString(getString(R.string.pref_conn_settings), "");
        inputUri.setText(backendUri);
    }

    /**
     * Binds logic to UI elements and initializes Retrofit2
     */
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
            apiService = initializeRetrofit(connectionSettings.getString(getString(R.string.pref_conn_settings), "http://127.0.0.1/"));
        } catch (IllegalArgumentException e) {
            // TODO: handle this properly!
            // TODO: Toast error
            apiService = initializeRetrofit("http://127.0.0.1"); // connect to some bullshit to not crash the app; FIXME
            e.printStackTrace();
        }

        // prepare Alert ahead of time
        AlertDialog.Builder alarmbauerShutdown = new AlertDialog.Builder(context) // ALAAARM
                .setTitle(R.string.app_name)
                .setMessage(R.string.system_alert_shutdown)
                .setIcon(R.drawable.ic_launcher_foreground)
                // if user is sure to reboot, reboot.
                .setPositiveButton(R.string.yay, (dialog, which) -> {
                    apiService.shutdown().enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(@NotNull Call<Integer> call, @NotNull Response<Integer> response) {

                        }

                        @Override
                        public void onFailure(@NotNull Call<Integer> call, @NotNull Throwable t) {

                        }
                    });
                    dialog.dismiss();
                })
                .setNegativeButton(R.string.nay, (dialog, which) -> dialog.dismiss()); // if user is not sure, do nothing

        // prepare Alert ahead of time
        AlertDialog.Builder alarmbauerReboot = new AlertDialog.Builder(context)
                .setTitle(R.string.app_name)
                .setMessage(R.string.system_alert_reboot)
                .setIcon(R.drawable.ic_launcher_foreground)
                // if user is sure to reboot, reboot.
                .setPositiveButton(R.string.yay, (dialog, which) -> {
                    apiService.reboot().enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(@NotNull Call<Integer> call, @NotNull Response<Integer> response) {
                            //TODO: Toast success
                        }

                        @Override
                        public void onFailure(@NotNull Call<Integer> call, @NotNull Throwable t) {
                            //TODO: Toast error
                        }
                    });
                    dialog.dismiss();
                })
                .setNegativeButton(R.string.nay, (dialog, which) -> dialog.dismiss()); // if user is not sure, do nothing

        uriRefreshButton.setOnClickListener(v -> loadBackendUri());
        uriSetterButton.setOnClickListener(v -> {
            SharedPreferences.Editor connectionSettingsEditor = connectionSettings.edit();
            connectionSettingsEditor.putString(getString(R.string.pref_conn_settings),
                    inputUri.getText().toString());
            connectionSettingsEditor.apply();
        });
        buttonReboot.setOnClickListener(v -> {
            AlertDialog alaaarmReboot = alarmbauerReboot.create();
            alaaarmReboot.show();
        });

        buttonShutdown.setOnClickListener(v -> {
            AlertDialog alaaarmShutdown = alarmbauerShutdown.create();
            alaaarmShutdown.show();
        });

        return root;
    }
}