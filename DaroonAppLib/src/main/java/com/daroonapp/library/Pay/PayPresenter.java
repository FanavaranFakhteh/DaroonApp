package com.daroonapp.library.Pay;

import android.app.Activity;

public interface PayPresenter {

    public void failTransaction(Pay pay);
    public void getResponse(Pay pay);
    public void getExtras(Activity activity,Pay pay);
    public void endTransaction(Pay pay);

}
