package com.indira.usedbooks;


import com.indira.usedbooks.entity.Feedbacks;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Manish on 27-05-2017.
 */

public interface GetFeedbacksInterface {


    @GET("getallfeedback.php")
    Call<Feedbacks> getFeedbacks();
}
