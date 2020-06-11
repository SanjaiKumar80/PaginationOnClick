package com.app.pagination;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * CREATED BY SANJAIKUMAR On 10-06-2020
 */
public  interface MainInterface {


    @GET("v2/list")
    Call<String> STRING_CALL(@Query("page") int page,@Query("limit") int limit);
}
