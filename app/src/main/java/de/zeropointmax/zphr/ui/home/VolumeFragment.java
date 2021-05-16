package de.zeropointmax.zphr.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import de.zeropointmax.zphr.ApiService;
import de.zeropointmax.zphr.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VolumeFragment extends Fragment {

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://zpm-canor.fritz.box:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //VolumeViewModel volumeViewModel = new ViewModelProvider(this).get(VolumeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_volume, container, false);
        final TextView textViewVolHdph = root.findViewById(R.id.text_vol_hdph);
        final ImageButton refreshButton = root.findViewById(R.id.refreshButton);
        final SeekBar hdphSeekBar = root.findViewById(R.id.hdphSeekbar);
        final TextView hdphSeekBarText = root.findViewById(R.id.hdphSeekBarText);
        /*volumeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });*/
        refreshButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                apiService.getVolumeHeadphone().enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful()) {
                            textViewVolHdph.setText(response.body().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Log.e("ERROR: ", t.getMessage());
                        t.printStackTrace();
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

        return root;
    }
}