package com.indira.usedbooks;

import com.indira.usedbooks.entity.Books;
import com.indira.usedbooks.entity.Response;
import com.indira.usedbooks.entity.User;

import java.util.Map;


import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by Manish on 19-04-2017.
 */

public interface GetUserInterface {

    @GET("login.php")
    Call<User> getUser();


    @POST("register.php")
    Call<Response> addUser(
            @PartMap() Map<String, RequestBody> partMap);
}



}
