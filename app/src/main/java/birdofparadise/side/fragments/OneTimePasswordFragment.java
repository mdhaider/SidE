package birdofparadise.side.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import birdofparadise.side.R;
import birdofparadise.side.enums.FragmentEnum;
import birdofparadise.side.fragments.baseFragment.BaseFragment;

/**
 * Created by C5248763 on 11/15/2016.
 */

public class OneTimePasswordFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_otp, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListener.setToolbarTitle(getString(R.string.one_time_password), FragmentEnum.OneTimePassword);

    }

    @Override
    public void onPause() {
        super.onPause();
        mListener.setToolbarTitle(getString(R.string.one_time_password), FragmentEnum.OneTimePassword);
    }
}
