package com.example.newspaper.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class StateFragmentAdapter extends FragmentStateAdapter {
    private final List<Fragment> items;
    private final FragmentManager manager;

    public StateFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);

        this.items = new ArrayList<>();
        this.manager = fragmentManager;
    }

    public void addFragment(Fragment fragment){
        this.items.add(fragment);
    }

    public Fragment getFragment(int position) {
        return this.items.get(position);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return this.items.get(position);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
