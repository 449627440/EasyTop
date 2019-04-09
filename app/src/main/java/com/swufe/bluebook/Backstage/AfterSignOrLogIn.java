package com.swufe.bluebook.Backstage;

public interface AfterSignOrLogIn extends Callback {
    void onSuccess(User currentUser);
}
