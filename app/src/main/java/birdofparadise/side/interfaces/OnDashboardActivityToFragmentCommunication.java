package birdofparadise.side.interfaces;

import android.support.v7.widget.AppCompatCheckedTextView;

import birdofparadise.side.fragments.fragmentHelper.baseDataContainer.BaseDataContainer;

public interface OnDashboardActivityToFragmentCommunication {
    void toolbarTitleClicked(AppCompatCheckedTextView anchor);

    boolean shouldDoNormalOperationOnBackPressed();

    BaseDataContainer getComingData();

    void toolbar1stIconClick();

    void toolbar2stIconClick();
}
