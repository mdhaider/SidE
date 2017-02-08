package birdofparadise.side.fragments.baseFragment;
/*
Copyright © 2016 Ariba. All rights reserved.
*/

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.view.View;


import birdofparadise.side.UtilService.GlobalConstant;
import birdofparadise.side.fragments.fragmentHelper.FragmentMessageContainer;
import birdofparadise.side.fragments.fragmentHelper.baseDataContainer.BaseDataContainer;
import birdofparadise.side.interfaces.OnDashboardActivityToFragmentCommunication;
import birdofparadise.side.interfaces.OnFragmentToDashboardInteractionListener;


public abstract class BaseFragment extends Fragment implements OnDashboardActivityToFragmentCommunication {

    protected OnFragmentToDashboardInteractionListener mListener;
    protected Handler handler;
    protected FragmentMessageContainer fragmentMessageContainer;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListener.setOnDashboardActivityToFragmentCommunication(this);
        try {
            fragmentMessageContainer = getArguments().getParcelable(GlobalConstant.getInstance().getFragmentMessageConatinerKey());
        } catch (Exception e) {

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentToDashboardInteractionListener) {
            mListener = (OnFragmentToDashboardInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);

        if (context instanceof OnFragmentToDashboardInteractionListener) {
            mListener = (OnFragmentToDashboardInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null;
    }

    @Override
    public void toolbarTitleClicked(AppCompatCheckedTextView anchor) {

    }

    @Override
    public boolean shouldDoNormalOperationOnBackPressed() {
        return true;
    }

    @Override
    public BaseDataContainer getComingData() {
        return null;
    }

    @Override
    public void toolbar1stIconClick() {

    }

    @Override
    public void toolbar2stIconClick() {

    }
}
