package birdofparadise.side.fragments.fragmentHelper.baseDataContainer;
/*
Copyright © 2016 Ariba. All rights reserved.
*/

import android.os.Parcel;
import android.os.Parcelable;

public abstract class BaseDataContainer implements Parcelable {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public BaseDataContainer() {
    }

    protected BaseDataContainer(Parcel in) {
    }

}
