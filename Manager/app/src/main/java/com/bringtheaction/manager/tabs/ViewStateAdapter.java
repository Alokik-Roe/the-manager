package com.bringtheaction.manager.tabs;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewStateAdapter  extends FragmentStateAdapter {


    public ViewStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new Today();
            case 1:
                return new Month();
        }
        return new Today();
    }

    @Override
    public int getItemCount() {
        return 2;
    }



    public void Refresh() {
        notifyDataSetChanged();
    }


}
