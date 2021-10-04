package de.zeropointmax.zphr.ui.system;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import de.zeropointmax.zphr.R;

public class SystemFragment extends Fragment {

    private SystemViewModel systemViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        systemViewModel =
                new ViewModelProvider(this).get(SystemViewModel.class);
        View root = inflater.inflate(R.layout.fragment_system, container, false);
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