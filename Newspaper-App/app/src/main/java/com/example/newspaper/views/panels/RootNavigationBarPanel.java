package com.example.newspaper.views.panels;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.newspaper.R;
import com.example.newspaper.ui.adapters.StateFragmentAdapter;
import com.example.newspaper.ui.fragments.FragmentCategory;
import com.example.newspaper.ui.fragments.FragmentHome;
import com.example.newspaper.ui.fragments.FragmentUtil;
import com.realgear.multislidinguppanel.BasePanelView;
import com.realgear.multislidinguppanel.MultiSlidingUpPanelLayout;
import com.realgear.readable_bottom_bar.ReadableBottomBar;

import org.jetbrains.annotations.NotNull;

public class RootNavigationBarPanel extends BasePanelView {

    private ViewPager2 view;
    private ReadableBottomBar readable;

    public RootNavigationBarPanel(@NotNull Context context, MultiSlidingUpPanelLayout panelLayout) {
        super(context, panelLayout);

        getContext().setTheme(R.style.Theme_Newspaper);
        LayoutInflater.from(getContext()).inflate(R.layout.layout_root_navigation_bar, this, true);
    }

    @Override
    public void onCreateView() {
        this.setPanelState(MultiSlidingUpPanelLayout.COLLAPSED);

        this.setSlideDirection(MultiSlidingUpPanelLayout.SLIDE_VERTICAL);

        this.setPeakHeight(getResources().getDimensionPixelOffset(R.dimen.margin_bottom_bar));
    }

    @Override
    public void onBindView() {
        this.view = getMultiSlidingUpPanel().findViewById(R.id.root_view_pager);
        this.readable = findViewById(R.id.root_navigation_bar);

        StateFragmentAdapter adapter = new StateFragmentAdapter(getSupportFragmentManager(), getLifecycle());

        adapter.addFragment(new FragmentHome());
        adapter.addFragment(new FragmentCategory());
        adapter.addFragment(new FragmentUtil());

        this.view.setAdapter(adapter);
        this.readable.setupWithViewPager2(view);
    }

    @Override
    public void onPanelStateChanged(int panelSate) {

    }
}
