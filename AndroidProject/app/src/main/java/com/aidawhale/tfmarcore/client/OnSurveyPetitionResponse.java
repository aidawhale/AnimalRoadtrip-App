package com.aidawhale.tfmarcore.client;

import com.aidawhale.tfmarcore.room.Survey;

public interface OnSurveyPetitionResponse {
    void onPetitionResponse(Survey survey);
    void onPetitionResponseNull();
    void onPetitionError();
    void onPetitionWaiting();
    void onPetitionFailure();
}
