package com.indira.usedbooks;

import com.indira.usedbooks.entity.Books;
import com.indira.usedbooks.entity.Response;

import java.util.List;
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
 * Created by Manish on 09-04-2017.
 */

public interface GetBooksInterface {

    @GET("getallbook.php")
    Call<Books> getBooks();


    @POST("book.php")
    @Multipart
    Call<Response> addBook(
            @PartMap() Map<String, RequestBody> partMap,
            @Part MultipartBody.Part image);
}
