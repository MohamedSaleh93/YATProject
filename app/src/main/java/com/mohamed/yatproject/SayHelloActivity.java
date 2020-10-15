package com.mohamed.yatproject;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class SayHelloActivity extends AppCompatActivity implements ITakeName {

    private TextFragment textFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.say_hello_activity);

        ButtonFragment buttonFragment = new ButtonFragment();
        textFragment = TextFragment.getInstance("Mohamed");

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment1, buttonFragment);
        fragmentTransaction.add(R.id.fragment2, textFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void takeNameToSayHello(String name) {
        textFragment.setHelloMessage(name);
    }
}
