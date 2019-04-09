package com.swufe.bluebook.Backstage;

import java.util.List;

public interface QueryListener extends Callback {
    void onSuccess(List resultList);
}
