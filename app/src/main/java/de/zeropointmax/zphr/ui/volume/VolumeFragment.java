package de.zeropointmax.zphr.ui.volume;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;

import de.zeropointmax.zphr.ApiService;
import de.zeropointmax.zphr.R;
import de.zeropointmax.zphr.RetrofitUtilities;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Logic of the Volume Fragment is defined here
 */
public class VolumeFragment extends Fragment {

    ApiService apiService;
    SharedPreferences connectionSettings;
    TextView textViewVolHdph;
    TextView textViewVolDigital;
    TextView hdphSeekBarText;
    TextView digitalSeekBarText;
    ImageButton refreshButton;
    ImageButton hdphSendButton;
    ImageButton digitalSendButton;
    ToggleButton muteToggleButton;
    SeekBar hdphSeekBar;
    SeekBar digitalSeekBar;
    CheckBox ab1CheckBox;
    CheckBox ab2CheckBox;

    /**
     * Update all volume UI components from the backend and silently fail on errors
     * TODO: De-duplicate code
     * TODO: do not silently fail on errors
     */
    void refreshAll() {
        apiService.getVolumeHeadphone().enqueue(new Callback<Integer>() {
            @SuppressWarnings("Duplicates") // not sure how i am supposed to de-duplicate this
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<Integer> call, @NotNull Response<Integer> response) {
                if (response.isSuccessful()) {
                    Integer volume = response.body();
                    assert volume != null;
                    textViewVolHdph.setText(volume.toString());
                    hdphSeekBar.setProgress(volume);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Integer> call, @NotNull Throwable t) {
                Log.e("ERROR: ", t.getMessage());
                t.printStackTrace();
            }
        });

        apiService.getVolumeDigital().enqueue(new Callback<Integer>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<Integer> call, @NotNull Response<Integer> response) {
                if (response.isSuccessful()){
                    Integer volume = response.body();
                    assert volume != null;
                    textViewVolDigital.setText(volume.toString());
                    digitalSeekBar.setProgress(volume);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Integer> call, @NotNull Throwable t) {

            }
        });

        apiService.getMute().enqueue(new Callback<Short>() {
            @Override
            public void onResponse(@NotNull Call<Short> call, @NotNull Response<Short> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    muteToggleButton.setChecked(response.body() > 0);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Short> call, @NotNull Throwable t) {

            }
        });

        apiService.getAB1().enqueue(new Callback<Short>() {
            @Override
            public void onResponse(@NotNull Call<Short> call, @NotNull Response<Short> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    ab1CheckBox.setChecked(response.body() > 0);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Short> call, @NotNull Throwable t) {

            }
        });

        apiService.getAB2().enqueue(new Callback<Short>() {
            @Override
            public void onResponse(@NotNull Call<Short> call, @NotNull Response<Short> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    ab2CheckBox.setChecked(response.body() > 0);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Short> call, @NotNull Throwable t) {

            }
        });
    }

    /**
     * Binds logic to UI elements and initializes Retrofit2
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Context context = getActivity();
        assert context != null; // i've heard using asserts in prod is sexy; greetings to Prof. Dr. Kusche
        // get handle to saved app preferences
        connectionSettings = context.getSharedPreferences(getString(R.string.pref_conn_settings), Context.MODE_PRIVATE);
        // initialize API connection with saved URI (as definded by user in System Fragment)
        // for now, localhost is used if there is no backend saved yet
        //TODO: investigate how to do this properly and not use a known-failing IP address
        try {
            apiService = RetrofitUtilities.initializeRetrofit(connectionSettings.getString(getString(R.string.pref_conn_settings), "http://127.0.0.1/"));
        } catch (IllegalArgumentException e) {
            apiService = RetrofitUtilities.initializeRetrofit("http://127.0.0.1/"); // connect to some bullshit to not crash the app; FIXME
            e.printStackTrace();
        }

        //VolumeViewModel volumeViewModel = new ViewModelProvider(this).get(VolumeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_volume, container, false);
        textViewVolHdph = root.findViewById(R.id.text_vol_hdph);
        textViewVolDigital = root.findViewById(R.id.text_vol_digital);
        hdphSeekBarText = root.findViewById(R.id.hdphSeekBarText);
        digitalSeekBarText = root.findViewById(R.id.digitalSeekBarText);
        refreshButton = root.findViewById(R.id.refreshButton);
        hdphSendButton = root.findViewById(R.id.hdphSendButton);
        digitalSendButton = root.findViewById(R.id.digitalSendButton);
        muteToggleButton = root.findViewById(R.id.muteToggleButton);
        hdphSeekBar = root.findViewById(R.id.hdphSeekbar);
        digitalSeekBar = root.findViewById(R.id.digitalSeekBar);
        ab1CheckBox = root.findViewById(R.id.ab1CheckBox);
        ab2CheckBox = root.findViewById(R.id.ab2CheckBox);

        refreshAll();

        refreshButton.setOnClickListener(v -> refreshAll());

        hdphSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                hdphSeekBarText.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        digitalSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                digitalSeekBarText.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        hdphSendButton.setOnClickListener(v -> apiService.setVolumeHeadphone(hdphSeekBar.getProgress()).enqueue(new Callback<Integer>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<Integer> call, @NotNull Response<Integer> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    textViewVolHdph.setText(response.body().toString());
                }
            }

            @Override
            public void onFailure(@NotNull Call<Integer> call, @NotNull Throwable t) {

            }
        }));

        digitalSendButton.setOnClickListener(v -> apiService.setVolumeDigital(digitalSeekBar.getProgress()).enqueue(new Callback<Integer>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NotNull Call<Integer> call, @NotNull Response<Integer> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    textViewVolDigital.setText(response.body().toString());
                }
            }

            @Override
            public void onFailure(@NotNull Call<Integer> call, @NotNull Throwable t) {

            }
        }));

        muteToggleButton.setOnClickListener(v -> apiService.setMute((short) (muteToggleButton.isChecked() ? 1 : 0)).enqueue(new Callback<Short>() {
            @Override
            public void onResponse(@NotNull Call<Short> call, @NotNull Response<Short> response) {
                if (response.isSuccessful()) { // check again anyways because who knows? It's ALSA after all
                    assert response.body() != null;
                    muteToggleButton.setChecked(response.body() > 0);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Short> call, @NotNull Throwable t) {

            }
        }));

        ab1CheckBox.setOnClickListener(v -> apiService.setAB1((short) (ab1CheckBox.isChecked() ? 1 : 0)).enqueue(new Callback<Short>() {
            @Override
            public void onResponse(@NotNull Call<Short> call, @NotNull Response<Short> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    ab1CheckBox.setChecked(response.body() > 0);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Short> call, @NotNull Throwable t) {

            }
        }));

        ab2CheckBox.setOnClickListener(v -> apiService.setAB2((short) (ab2CheckBox.isChecked() ? 1 : 0)).enqueue(new Callback<Short>() {
            @Override
            public void onResponse(@NotNull Call<Short> call, @NotNull Response<Short> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    ab2CheckBox.setChecked(response.body() > 0);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Short> call, @NotNull Throwable t) {

            }
        }));

        return root;
    }
}