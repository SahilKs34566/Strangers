package com.project.strangers.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.project.strangers.Fragment.ChatsFragment;
import com.project.strangers.Fragment.EmotionsFragment;
import com.project.strangers.Fragment.WriteFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0: return new ChatsFragment();
            case 1: return new EmotionsFragment();
            case 2 : return new WriteFragment();
            default: return  new ChatsFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String title= null;
        if(position == 0)
        {
            title="Chats";
        }
        if(position == 1)
        {
            title="Emotions";
        }
        if(position == 2)
        {
            title="Write";
        }
        return title;
    }
}

