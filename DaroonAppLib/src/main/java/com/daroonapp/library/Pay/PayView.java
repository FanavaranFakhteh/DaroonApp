package com.daroonapp.library.Pay;

import android.content.Intent;

public interface PayView {
    public void setUi(Pay pay);
    public void setCustomChanges();
    public void setStatusBarColor();
    public void setActionBarColor(Integer integer);
    public void setProgressBarColor();
}
