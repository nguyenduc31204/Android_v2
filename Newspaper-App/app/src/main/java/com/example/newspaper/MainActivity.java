package com.example.newspaper;

import android.os.Bundle;
import android.widget.Adapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newspaper.views.panels.RootNavigationBarPanel;
import com.realgear.multislidinguppanel.MultiSlidingPanelAdapter;
import com.realgear.multislidinguppanel.MultiSlidingUpPanelLayout;
import com.realgear.multislidinguppanel.PanelStateListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        MultiSlidingUpPanelLayout panelLayout = findViewById(R.id.root_sliding_up_panel);

        List<Class<?>> items = new ArrayList<>();

        items.add(RootNavigationBarPanel.class);

        panelLayout.setPanelStateListener(new PanelStateListener(panelLayout) {});

        panelLayout.setAdapter(new MultiSlidingPanelAdapter(this, items));
    }
}