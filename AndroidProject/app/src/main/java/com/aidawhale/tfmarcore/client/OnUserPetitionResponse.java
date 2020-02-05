package com.aidawhale.tfmarcore.client;

import com.aidawhale.tfmarcore.room.User;

public interface OnUserPetitionResponse {
    void onPetitionResponse(User remoteUser);
    void onPetitionResponseNull();
    void onPetitionError();
    void onPetitionWaiting();
    void onPetitionFailure();
}
