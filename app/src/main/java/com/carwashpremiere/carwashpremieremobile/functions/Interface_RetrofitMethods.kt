package com.carwashpremiere.carwashpremieremobile.functions

import com.carwashpremiere.carwashpremieremobile.model.Data_RawData
import com.carwashpremiere.carwashpremieremobile.model.Data_Test
import com.carwashpremiere.carwashpremieremobile.model.Model_CarTypes
import com.carwashpremiere.carwashpremieremobile.model.Model_Category
import com.carwashpremiere.carwashpremieremobile.model.Model_DetailsGeneral
import com.carwashpremiere.carwashpremieremobile.model.Model_ExtraServicesGeneral
import com.carwashpremiere.carwashpremieremobile.model.Model_Misc
import com.carwashpremiere.carwashpremieremobile.model.Model_ServicesCars
import com.carwashpremiere.carwashpremieremobile.model.Model_ServicesObjects
import com.carwashpremiere.carwashpremieremobile.model.Model_Shortcuts
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.POST


/*
id API TABLE
0: test
1: CarType
2: Category
3: Car Details
4: Object Details
5: Extra Services Cars
6: Extra Services Objects
7: Services cars list
8: Services Objects list
9: Shortcuts
10: Phone number
11: Service car description based on the service name
TODO 12: Misc
*/

interface Interface_RetrofitMethods {

    @GET("allgets.pho?opt=11")

    //CarType
        //-- CREATE --
    @POST("allsaves.php?opt=1")
    fun createCarType(
        @Field("title") title: String,

    ): Response<Model_CarTypes>

        //-- READ --
    @GET("allgets.php?opt=1")
    fun getCarType(): Call<List<Model_CarTypes>>

     //-- UPDATE --
    @POST("allputs.php?opt=1")
    fun updateCarType(
        //@Field("id") id: Int,
         @Field("title") title: String,
    ): Response<Model_CarTypes>

        //-- DELETE --
    @POST("alldeletes.php?opt=1")
    fun deleteCarType(
        @Field("id") id: Int,
    ): Response<Model_CarTypes>



    //Categories

        //-- CREATE --
    @POST("allsaves.php?opt=2")
    fun createCategory(
        //@Field("id") id: Int,
        @Field("title") title: String,
        @Field("imageUrl") imageUrl: String,
        @Field("description") description: String,
        @Field("screen_name") activity: String,
    ): Response<Model_Category>

        //-- READ --
    @GET("allgets.php?opt=2")
    fun getCategories(): Call<List<Model_Category>>

        //-- UPDATE --
    @POST("allputs.php?opt=2")
    fun updateCategory(
        //@Field("id") id: Int,
            @Field("title") title: String,
            @Field("imageUrl") imageUrl: String,
            @Field("description") description: String,
            @Field("screen_name") activity: String,
    ): Response<Model_Category>

        //-- DELETE --
    @POST("alldeletes.php?opt=2")
    fun deleteCategory(
        @Field("id") id: Int,
    ): Response<Model_Category>


    // Car Details
//-- CREATE --
    @POST("allsaves.php?opt=3")
    fun createCarDetails(
        @Field("title") name: String,

    ): Response<Model_DetailsGeneral>

    //-- READ --
    @GET("allgets.php?opt=3")
    fun getCarDetails(): Call<List<Model_DetailsGeneral>>

    //-- UPDATE --
    @POST("allputs.php?opt=3")
    fun updateCarDetails(
        @Field("title") name: String,
    ): Response<Model_DetailsGeneral>

    //-- DELETE --
    @POST("alldeletes.php?opt=3")
    fun deleteCarDetails(
        @Field("id") id: Int,
    ): Response<Model_DetailsGeneral>


    // Object Details
//-- CREATE --
    @POST("allsaves.php?opt=4")
    fun createObjectDetails(
        @Field("title") name: String,
    ): Response<Model_DetailsGeneral>

    //-- READ --
    @GET("allgets.php?opt=4")
    fun getObjectDetails(): Call<List<Model_DetailsGeneral>>

    //-- UPDATE --
    @POST("allputs.php?opt=4")
    fun updateObjectDetails(
        @Field("title") name: String,
    ): Response<Model_DetailsGeneral>

    //-- DELETE --
    @POST("alldeletes.php?opt=4")
    fun deleteObjectDetails(
        @Field("id") id: Int,
    ): Response<Model_DetailsGeneral>




    // ExtraServicesCars
//-- CREATE --
    @POST("allsaves.php?opt=5")
    fun createExtraServicesCars(
        @Field("title") name: String,
        @Field("price") price: String,
    ): Response<Model_ExtraServicesGeneral>

    //-- READ --
    @GET("allgets.php?opt=5")
    fun getExtraServicesCars(): Call<List<Model_ExtraServicesGeneral>>

    //-- UPDATE --
    @POST("allputs.php?opt=5")
    fun updateExtraServicesCars(
        @Field("title") name: String,
        @Field("price") price: String,
    ): Response<Model_ExtraServicesGeneral>

    //-- DELETE --
    @POST("alldeletes.php?opt=5")
    fun deleteExtraServicesCars(
        @Field("id") id: Int,
    ): Response<Model_ExtraServicesGeneral>




    // ExtraServicesObjects
//-- CREATE --
    @POST("allsaves.php?opt=6")
    fun createExtraServicesObjects(
        @Field("title") name: String,
        @Field("price") price: String,
    ): Response<Model_ExtraServicesGeneral>

    //-- READ --
    @GET("allgets.php?opt=6")
    fun getExtraServicesObjects(): Call<List<Model_ExtraServicesGeneral>>

    //-- UPDATE --
    @POST("allputs.php?opt=6")
    fun updateExtraServicesObjects(
        @Field("title") name: String,
        @Field("price") price: String,
    ): Response<Model_ExtraServicesGeneral>

    //-- DELETE --
    @POST("alldeletes.php?opt=6")
    fun deleteExtraServicesObjects(
        @Field("id") id: Int,
    ): Response<Model_ExtraServicesGeneral>




    // Misc
//-- CREATE --
/*    @POST("allsaves.php?opt=5")
    fun createMisc(
        @Field("name") name: String,
        @Field("image") image: String,
        @Field("description") description: String,
        @Field("activity") activity: String,
    ): Response<Model_Misc>

    //-- READ --
    @GET("allgets.php?opt=5")
    fun getMisc(): Call<List<Model_Misc>>

    //-- UPDATE --
    @POST("allputs.php?opt=5")
    fun updateMisc(
        @Field("name") name: String,
        @Field("image") image: String,
        @Field("description") description: String,
        @Field("activity") activity: String,
    ): Response<Model_Misc>

    //-- DELETE --
    @POST("alldeletes.php?opt=5")
    fun deleteMisc(
        @Field("id") id: Int,
    ): Response<Model_Misc>*/

    // ServicesCars
//-- CREATE --
    @POST("allsaves.php?opt=7")
    fun createServicesCars(
        @Field("title") name: String,
        @Field("imgUrl") icon: String,
        @Field("description") description: String,
        @Field("short_description") short_description: String,
        @Field("screen_name") activity: String,
    ): Response<Model_ServicesCars>

    //-- READ --
    @GET("allgets.php?opt=7")
    fun getServicesCars(): Call<List<Model_ServicesCars>>

    //-- UPDATE --
    @POST("allputs.php?opt=7")
    fun updateServicesCars(
        @Field("title") name: String,
        @Field("imgUrl") icon: String,
        @Field("description") description: String,
        @Field("short_description") short_description: String,
        @Field("screen_name") activity: String,
    ): Response<Model_ServicesCars>

    //-- DELETE --
    @POST("alldeletes.php?opt=7")
    fun deleteServicesCars(
        @Field("id") id: Int,
    ): Response<Model_ServicesCars>

    // ServicesObjects
//-- CREATE --
    @POST("allsaves.php?opt=8")
    fun createServicesObjects(
        @Field("title") title: String,
        @Field("imgUrl") imgUrl: String,
        @Field("description") description: String,
        @Field("screen_name") activity: String,
    ): Response<Model_ServicesObjects>

    //-- READ --
    @GET("allgets.php?opt=8")
    fun getServicesObjects(): Call<List<Model_ServicesObjects>>

    //-- UPDATE --
    @POST("allputs.php?opt=8")
    fun updateServicesObjects(
        @Field("title") title: String,
        @Field("imgUrl") imgUrl: String,
        @Field("description") description: String,
        @Field("screen_name") activity: String,
    ): Response<Model_ServicesObjects>

    //-- DELETE --
    @POST("alldeletes.php?opt=8")
    fun deleteServicesObjects(
        @Field("id") id: Int,
    ): Response<Model_ServicesObjects>

    // Shortcuts
//-- CREATE --
    @POST("allsaves.php?opt=9")
    fun createShortcuts(
        @Field("name") name: String,
        @Field("image") image: String,
        @Field("description") description: String,
        @Field("screen_name") activity: String,
    ): Response<Model_Shortcuts>

    //-- READ --
    @GET("allgets.php?opt=9")
    fun getShortcuts(): Call<List<Model_Shortcuts>>

    //-- UPDATE --
    @POST("allputs.php?opt=9")
    fun updateShortcuts(
        @Field("title") name: String,
        @Field("imgUrl") imgUrl: String,
        @Field("screen_name") activity: String,
    ): Response<Model_Shortcuts>

    //-- DELETE --
    @POST("alldeletes.php?opt=9")
    fun deleteShortcuts(
        @Field("id") id: Int,
    ): Response<Model_Shortcuts>











    @GET("ai_temp.php")
    fun getData(): Call<List<Data_RawData>>

    @GET("allgets.php?opt=0&id=1")
    fun getTest(): Call<Data_Test>

    @GET("allgets.php?opt=0&id=1")
    fun getTestString(): Call<String>

    @POST("allputs.php?opt=0")
    fun postTest(
        @Field("id") id: Int,
        @Field("name") name: String,
        @Field("populationDate") populationDate: String,
        @Field("percentage") percentage: String,
    ): Response<Data_Test>


}