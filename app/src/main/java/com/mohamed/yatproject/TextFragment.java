package com.mohamed.yatproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TextFragment extends Fragment {

    private TextView helloTV;

    private static TextFragment textFragment;

    public static TextFragment getInstance(String name) {
        textFragment = new TextFragment();
        Bundle fragmentBundle = new Bundle();
        fragmentBundle.putString("name", name);
        textFragment.setArguments(fragmentBundle);
        return textFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.text_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        helloTV = view.findViewById(R.id.helloTV);
        String name = getArguments().getString("name");
        if (name != null) {
            helloTV.setText("Hello " + name);
        }

    }

    public void setHelloMessage(String message) {
        helloTV.setText("Hello " + message);
    }
}
