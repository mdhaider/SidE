package birdofparadise.side.interfaces;

import birdofparadise.side.enums.FragmentEnum;
import birdofparadise.side.fragments.fragmentHelper.FragmentMessageContainer;

/**
 * Created by haider on 08-02-2017.
 */

public interface OnFragmentToDashboardInteractionListener {
    void setToolbarTitle(String s, FragmentEnum comingFrom);

    void setOnDashboardActivityToFragmentCommunication(OnDashboardActivityToFragmentCommunication onDashboardActivityToFragmentCommunication);

    void closeCustomerMenu();

    void openDesiredFragment(FragmentMessageContainer fragmentMessageContainer);

    void setToolbarInvisible();

    void backButtonClicked();

    void setToolbar1stRight(int icon);

    void setToolbar2ndRight(int icon);

    void setToolbarVisible();
}
