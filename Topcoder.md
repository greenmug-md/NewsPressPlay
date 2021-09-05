# EdgeNet Challenge Series - Video News Feeds App Solution Writeup

The challenge was to build a mobile application [Video News Feeds Application Challenge](https://www.topcoder.com/challenges/1af89a1d-7a29-4617-90f6-675064dc076c) using Alefedge [EdgeNet Video Enablement API](https://alefedge.com/products/prepackaged-solutions/dev-video-enablement/). Here I will discuss my solution and my approach to the requirements during the development of the application.

## Functional Requirements
The Main Requirement was for the user to be able to load and view news feed videos in low latency using the [EdgeNet Video Enablement API](https://alefedge.com/products/prepackaged-solutions/dev-video-enablement/)
Apart from viewing the video news feed, the user will be able to mark that video as a favorite or “save for later” for them to come back and watch it again. We could also add additional features for users to be able to search videos based on their news feed title. 

## Choice of Technology Stack
 
  Based on my experience, I chose the following tech stack:

- Backend:  
  - Alefedge Video Enablement API for Video Storage in EdgeNet.
  - Firebase for Video Storage  (copy of video if Edgenet url fails to load).
  - Firebase  for User Authentication as well as storing Video Meta Data such as Images, Titles, Genre and Description
  
- Frontend: **Android using Kotlin**
  -  Exoplayer Library for Video and Audio Player to play different video format types such as Clear DASH, HLS, Smooth Streaming.
  - Material UI as Theme and Styling library
  - Android Architecture using Model View ViewModel using Android Lifecycle aware component.
  -  Room for Local Database
  -  Coroutines, along with RxJava for Network Calls
  -  Android Unit Testing
    
## Working on the Backend

Started to understand the Swagger for [Video Enablement Api Swagger](https://developerapis.stg-alefedge.com/api-docs-edgetube).
The Three Api's I focused on was
1. Add content to server:  /api/v1/stream-tech/content/add 
2. Get all content details for given partner: /api/v1/stream-tech/content/get-all
3. Get url for the given content id : /api/v1/stream-tech/content/get-url/{content_id}

In order to use the Api's we need two values 'Api Key' and 'Partner Name'.
I followed [Alfedge User Guide Documentation](https://developer.alefedge.com/get-started/create-edge-native-services/user-guide/) provided on the Alefedge website to gather both these values to use the Apis. 
The content_id field uniquely identifies the video content in both the Firebase and Alfedege Video Storage. 

Response of Posting to EdgeNet
```
{
  "URLs": [
    {
      "message": "Content onboarding is in progress",
      "status": "INPROGRESS",
      "url": "https://d29ctshu25jfop.cloudfront.net/aa/raw.mp4",
      "content_id": "bdt"
    }
  ]
}
```
Firebase FireStore used for User Authentication, Storing Video Meta Data and copy of Video if Edgenet fails to load. 

![alt text](https://user-images.githubusercontent.com/34758872/130411331-863c3eb9-6c37-4ac7-b16c-fd122236a4cd.png)

I added videos to the Edgenet and FireStore Cloud Storage for different type of Media Type. 
HLS type with (.m3u8) ,Smooth Streaming(.ism/Manifest), Dash(.mpd) and also other Progressing Formats such as (.mp4)

Code Snippet for EdgeNetService using Retrofit Get Call.

```Retrofit ApiEdgeNetService Service

interface ApiEdgeNetService {

    @GET("api/v1/stream-tech/content/get-all")
    suspend fun getAllContent(
        @Query("partner_name") partner_name:  String = AESEncyption.decrypt(Constants.PARTNER_ID)!!,
        @Header("api_key") api_key: String = AESEncyption.decrypt(Constants.API_KEY)!!
    ): Response<EdgeNetCloud>

    @GET("api/v1/stream-tech/content/get-url/{content_id}")
    suspend fun getContentId(
        @Path("content_id") content_id: String,
        @Query("partner_name") partner_name:  String = AESEncyption.decrypt(Constants.PARTNER_ID)!!,
        @Header("api_key") api_key: String = AESEncyption.decrypt(Constants.API_KEY)!!
    ): Response<EdgeNetUrl>

}
```

 ## Working on the FrontEnd
 
I started with the on Video Player Component using Google Opensource [Exoplayer Library](https://github.com/google/ExoPlayer).
 This Guide [Developer Guide for Exoplayer](https://exoplayer.dev/hello-world.html) helped in understanding and handling different media Types such as HLS, DASH, Smooth Streaming.
 
Next Sign In and Sign Up Components using Firebase Authentication.
Once User SignUps using email, password and 
App lands on a homepage which has a BottomNavigationView , containing 3 pages
 1. Trending Videos
 2. Channels (Clicking on Channel will open Videos corresponding to that particular channel)
 3. Watch Later(Favourites and Watch Later).
 
 
 <img src="https://user-images.githubusercontent.com/34758872/130397297-fae79917-a7b2-4fbf-8648-c818f728497b.png" width="200">
 
 Favourites and Watch Later data was stored in local db using Room Database.
 Lifecycle aware LiveData and ViewModels were used , so that it can update the View Layer when underlying data changes.
 Network Calls were made with help of Kotlin Coroutines and RxJava.
 
  <img src="https://user-images.githubusercontent.com/34758872/130397310-7a346e74-9f63-4ecb-a554-f452088500ae.png" width="200">

 
Android Unit Testing for Network Module, View Models and Database
 
![alt text](https://user-images.githubusercontent.com/34758872/130398638-db045cad-576c-42ac-8d6d-a5ce5de5750b.png)

Android Test for Network Module
```
    var apiClient = getRetrofitClient(Constants.EDGENET_CLOUD);
    var api = apiClient.create(ApiEdgeNetService::class.java)
 
    @Test
    fun getEdgeNetExistingVideo() {
        runBlocking {
            var s = api.getContentId("bev")
            Assert.assertNotNull(s?.body())
        }
    }

# This code part of the file is not the complete code.
```

Android Test for Database
```
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: FavouriteShowDatabase
    private lateinit var dbdao: FavouriteShowDao

    @Before
    public override fun setUp() {
        val application = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(application,FavouriteShowDatabase::class.java).allowMainThreadQueries().build() //only for testing
        dbdao = db.tvFacouritesDao()
    }

    @Test
    fun writeAndReadFavourites() {
            val favourite = Favourites("Travel", "https:://image", "https:://url", "bev", "F" ,"Movie")
            dbdao.addToWatchList(favourite).blockingAwait()
            dbdao.selectAll().test().assertValue { list ->
                list.isNotEmpty()
            }
    }

    @After
    fun closeDB() {
        db.clearAllTables()
        db.close()
    }
}
# This code part of the file is not the complete code.
```
 
## Conclusion
This Challenge me in understanding how to the use the [EdgeNet Video Enablement API](https://alefedge.com/products/prepackaged-solutions/dev-video-enablement/) effectively to store and retrieve information, and also make an Video Player Component using OpenSource Exoplayer to handle different type of media types. as well using the modern Architecture Components along with Room Database to build components for App.

