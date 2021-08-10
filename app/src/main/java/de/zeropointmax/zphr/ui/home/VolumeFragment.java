package de.zeropointmax.zphr.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import de.zeropointmax.zphr.ApiService;
import de.zeropointmax.zphr.R;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VolumeFragment extends Fragment {

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://zpm-canor.fritz.box:5001/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //VolumeViewModel volumeViewModel = new ViewModelProvider(this).get(VolumeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_volume, container, false);
        final TextView textViewVolHdph = root.findViewById(R.id.text_vol_hdph);
        final TextView textViewVolDigital = root.findViewById(R.id.text_vol_digital);
        final TextView hdphSeekBarText = root.findViewById(R.id.hdphSeekBarText);
        final TextView digitalSeekBarText = root.findViewById(R.id.digitalSeekBarText);
        final ImageButton refreshButton = root.findViewById(R.id.refreshButton);
        final ImageButton hdphSendButton = root.findViewById(R.id.hdphSendButton);
        final ImageButton digitalSendButton = root.findViewById(R.id.digitalSendButton);
        final ToggleButton muteToggleButton = root.findViewById(R.id.muteToggleButton);
        final SeekBar hdphSeekBar = root.findViewById(R.id.hdphSeekbar);
        final SeekBar digitalSeekBar = root.findViewById(R.id.digitalSeekBar);
        final CheckBox ab1CheckBox = root.findViewById(R.id.ab1CheckBox);
        final CheckBox ab2CheckBox = root.findViewById(R.id.ab2CheckBox);
        /*volumeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });*/
        refreshButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                apiService.getVolumeHeadphone().enqueue(new Callback<Integer>() {
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
                    public void onFailure(@NotNull Call<Integer> call, Throwable t) {
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
                    public void onFailure(Call<Integer> call, Throwable t) {

                    }
                });

                apiService.getMute().enqueue(new Callback<Short>() {
                    @Override
                    public void onResponse(@NotNull Call<Short> call, @NotNull Response<Short> response) {
                        if (response.isSuccessful()) {
                            muteToggleButton.setChecked(response.body() > 0);
                        }
                    }

                    @Override
                    public void onFailure(Call<Short> call, Throwable t) {

                    }
                });

                apiService.getAB1().enqueue(new Callback<Short>() {
                    @Override
                    public void onResponse(@NotNull Call<Short> call, @NotNull Response<Short> response) {
                        if (response.isSuccessful()) {
                            ab1CheckBox.setChecked(response.body() > 0);
                        }
                    }

                    @Override
                    public void onFailure(Call<Short> call, Throwable t) {

                    }
                });

                apiService.getAB2().enqueue(new Callback<Short>() {
                    @Override
                    public void onResponse(@NotNull Call<Short> call, @NotNull Response<Short> response) {
                        if (response.isSuccessful()) {
                            ab2CheckBox.setChecked(response.body() > 0);
                        }
                    }

                    @Override
                    public void onFailure(Call<Short> call, Throwable t) {

                    }
                });
            }
        });

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

        hdphSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //apiService.setVolumeHeadphone(hdphSeekBar.getProgress());
                apiService.setVolumeHeadphone(hdphSeekBar.getProgress()).enqueue(new Callback<Integer>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(@NotNull Call<Integer> call, @NotNull Response<Integer> response) {
                        if (response.isSuccessful()) {
                            textViewVolHdph.setText(response.body().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {

                    }
                });
            }
        });

        digitalSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiService.setVolumeDigital(digitalSeekBar.getProgress()).enqueue(new Callback<Integer>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(@NotNull Call<Integer> call, @NotNull Response<Integer> response) {
                        if (response.isSuccessful()) {
                            textViewVolDigital.setText(response.body().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {

                    }
                });
            }
        });

        return root;
    }
}