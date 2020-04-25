package com.creations.inception.network;

import com.creations.condition.Info;
import com.creations.inception.model.APIResponse;
import com.creations.inception.model.Request;
import com.creations.tools.network.ListResponseCallback;
import com.creations.tools.network.ObjectResponseCallback;

public interface IAPIChat {

    void getChatReply(Request request, ObjectResponseCallback<APIResponse> responseCallback);

    void getAirspaces(ListResponseCallback<Info> responseCallback);

}
