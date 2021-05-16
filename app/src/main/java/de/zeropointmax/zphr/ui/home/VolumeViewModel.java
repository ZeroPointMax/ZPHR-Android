package de.zeropointmax.zphr.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VolumeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public VolumeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("???");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setmText(String s) {
        mText.setValue(s);
    }
}