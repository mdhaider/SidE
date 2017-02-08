package birdofparadise.side.fragments;

import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import birdofparadise.side.R;
import birdofparadise.side.fragments.baseFragment.BaseFragment;


public class DashboardFragment extends BaseFragment  {


    @Override
    public void toolbarTitleClicked(final AppCompatCheckedTextView anchor) {

    }

    @Override
    public boolean shouldDoNormalOperationOnBackPressed() {
        return true;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);






    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onDetach() {
        super.onDetach();

    }
}
