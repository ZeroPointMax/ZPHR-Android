package de.zeropointmax.zphr.ui.volume;

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

    void refreshAll() {
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
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
        /*volumeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });*/

        refreshAll();

        refreshButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                refreshAll();
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

        muteToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiService.setMute((short) (muteToggleButton.isChecked() ? 1 : 0)).enqueue(new Callback<Short>() {
                    @Override
                    public void onResponse(@NotNull Call<Short> call, @NotNull Response<Short> response) {
                        if (response.isSuccessful()) { // check again anyways because who knows? It's ALSA after all
                            muteToggleButton.setChecked(response.body() > 0);
                        }
                    }

                    @Override
                    public void onFailure(Call<Short> call, Throwable t) {

                    }
                });
            }
        });

        ab1CheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiService.setAB1((short) (ab1CheckBox.isChecked() ? 1 : 0)).enqueue(new Callback<Short>() {
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
            }
        });

        ab2CheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiService.setAB2((short) (ab2CheckBox.isChecked() ? 1 : 0)).enqueue(new Callback<Short>() {
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

        return root;
    }
}