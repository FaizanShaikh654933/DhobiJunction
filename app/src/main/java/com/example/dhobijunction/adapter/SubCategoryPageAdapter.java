package com.example.dhobijunction.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.dhobijunction.fragment.ProductFragment;
import com.example.dhobijunction.model.SubCategoryModel;

import java.util.List;

public class SubCategoryPageAdapter extends FragmentPagerAdapter {
    List<SubCategoryModel> list;
    public SubCategoryPageAdapter(FragmentManager childFragmentManager, List<SubCategoryModel> list) {
        super(childFragmentManager);
        this.list=list;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new ProductFragment(list.get(position));
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getTitle();
    }
}
