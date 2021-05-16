package de.zeropointmax.zphr.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import de.zeropointmax.zphr.R;

public class VolumeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //VolumeViewModel volumeViewModel = new ViewModelProvider(this).get(VolumeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_volume, container, false);
        final TextView textViewVolHdph = root.findViewById(R.id.text_vol_hdph);
        final ImageButton refreshButton = root.findViewById(R.id.refreshButton);
        /*volumeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });*/
        refreshButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                textViewVolHdph.setText("Test");
            }
        });

        return root;
    }
}