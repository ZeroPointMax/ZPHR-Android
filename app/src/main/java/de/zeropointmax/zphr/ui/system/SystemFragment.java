package de.zeropointmax.zphr.ui.system;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import de.zeropointmax.zphr.R;

public class SystemFragment extends Fragment {

    String backendUri;
    SharedPreferences connectionSettings;
    EditText inputUri;
    ImageButton uriRefreshButton;
    ImageButton uriSetterButton;

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

        loadBackendUri();

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