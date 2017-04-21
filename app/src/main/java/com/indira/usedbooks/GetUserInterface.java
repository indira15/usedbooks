package com.indira.usedbooks;

//import com.indira.usedbooks.entity.Response;
import com.indira.usedbooks.entity.Response;
import com.indira.usedbooks.entity.User;

//import java.util.Map;


//import okhttp3.RequestBody;
import retrofit2.Call;
//import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


/**
 * Created by Manish on 19-04-2017.
 */

public interface GetUserInterface {

    //@GET("login.php")
    //Call<User> getUser();


    //@GET("login.php")
   // Call<User> getUser(@Path("email") String email, @Path("password") String password);


    @POST("register.php")
    Call<Response> addUser(@Path("name") String name, @Path("email") String email, @Path("password") String password, @Path("phoneno") String phoneno, @Path("phoneno2") String phoneno2, @Path("address") String address, @Path("city") String city, @Path("state") String state);
}




