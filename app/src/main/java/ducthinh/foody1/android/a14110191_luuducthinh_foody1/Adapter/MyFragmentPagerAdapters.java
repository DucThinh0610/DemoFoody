package ducthinh.foody1.android.a14110191_luuducthinh_foody1.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Test on 3/23/2017.
 */

public class MyFragmentPagerAdapters extends FragmentPagerAdapter {//tạo 1 adapter để có thể add các fragment lại để có giao diện và chức năng tương tự tabhost
    List<Fragment> listFragments;
    public MyFragmentPagerAdapters(FragmentManager fm, List<Fragment> listFragments) {
        super(fm);
        this.listFragments=listFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return listFragments.get(position);
    }

    @Override
    public int getCount() {
        return listFragments.size();
    }
}
