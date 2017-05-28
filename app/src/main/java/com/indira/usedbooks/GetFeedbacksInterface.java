package com.indira.usedbooks;


import com.indira.usedbooks.entity.Feedbacks;
import com.indira.usedbooks.entity.Response;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * Created by Manish on 27-05-2017.
 */

public interface GetFeedbacksInterface {


    @GET("getallfeedback.php")
    Call<Feedbacks> getFeedbacks();

    @POST("feedback.php")
    Call<Response> addFeedback(
            @PartMap() Map<String, RequestBody> partMap);

}
