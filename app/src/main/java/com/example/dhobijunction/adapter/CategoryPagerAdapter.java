package com.example.dhobijunction.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.dhobijunction.fragment.SubCategoryFragment;
import com.example.dhobijunction.model.CategoryModel;

import java.util.List;

public class CategoryPagerAdapter extends FragmentPagerAdapter {
    List<CategoryModel> list;
    public CategoryPagerAdapter(FragmentManager supportFragmentManager, List<CategoryModel> list) {
        super(supportFragmentManager);
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new SubCategoryFragment(list.get(position));

    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getTitle();
    }
}
