package com.indira.usedbooks;

import com.indira.usedbooks.entity.Books;
import com.indira.usedbooks.entity.Feedback;
import com.indira.usedbooks.entity.Feedbacks;
import com.indira.usedbooks.entity.Response;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by Manish on 08-05-2017.
 */

public interface GetFeedbackInterface {
    @GET("getallfeedback.php")
    Call<Feedbacks> getFeedbacks();

    @POST("feedback.php")
    @Multipart
    Call<Response> addFeedback(
            @PartMap()
                    Map<String, RequestBody> partMap);


}

