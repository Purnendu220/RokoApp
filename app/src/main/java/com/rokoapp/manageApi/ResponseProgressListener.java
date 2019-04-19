package com.rokoapp.manageApi;


public interface ResponseProgressListener {

    void onResponseInProgress();

    void onResponseCompleted(int i);
    void onResponseCompletedWithData(int i,Object data);


    /*String  onResponsePending(int i);*/

    void onResponseFailed(FailureCodes code);

}
