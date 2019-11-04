package mm.com.fairwaytech.Navigation.ui.registered;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegisteredViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RegisteredViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}