package com.indira.usedbooks;

//import com.indira.usedbooks.entity.Response;
import com.indira.usedbooks.entity.Response;
import com.indira.usedbooks.entity.User;

//import java.util.Map;


//import okhttp3.RequestBody;
import retrofit2.Call;
//import retrofit2.http.GET;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * Created by Manish on 19-04-2017.
 */

public interface GetUserInterface {

    //@GET("login.php")
    //Call<User> getUser();


    //@GET("login.php")
   // Call<User> getUser(@Field("email") String email, @Field("password") String password);


    @POST("register.php")
    @FormUrlEncoded
    Call<Response> addUser(
        @Field("name") String name,
        @Field("email") String email,
        @Field("password") String password,
        @Field("phoneno") String phoneno,
        @Field("phoneno2") String phoneno2,
        @Field("address") String address,
        @Field("city") String city,
        @Field("state") String state);
}




