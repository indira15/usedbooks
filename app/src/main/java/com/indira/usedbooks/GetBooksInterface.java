package com.indira.usedbooks;

import com.indira.usedbooks.entity.Books;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Manish on 09-04-2017.
 */

public interface GetBooksInterface {

    @GET("getallbook.php")
    Call<Books> getBooks();
}
