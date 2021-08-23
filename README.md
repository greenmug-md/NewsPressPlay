# NewsPressPlay

<img src="https://user-images.githubusercontent.com/34758872/130397136-0f55361f-7965-438f-b1c7-bba1e04c30a2.png" width="300">

#Video Link : https://drive.google.com/file/d/10D-vWKOdMeu5jPKDWARYPp1YvySrpgsA/view?usp=sharing

# App Features

## SignUp/SignIn : Using Google Firebase for Authentication, 

<img src="https://user-images.githubusercontent.com/34758872/130397139-b936949e-7e60-478d-ba62-93346724c80d.png" width="200">
       
<img src="https://user-images.githubusercontent.com/34758872/130397158-2c12498a-abf3-4c26-80cb-d4c078f72af4.png" width="200">

 1. Validations all fields are mandatory for Signup, else we will get a Toast message with Error
 2. Confirm Password and Password Should be Same
 3. Email should be valid


## Bottom Navigation, has 3 pages
    
Popular OR Trending Videos

<img src="https://user-images.githubusercontent.com/34758872/130397221-a50ac554-3202-4530-a34f-bc7636ba351b.png" width="200">
     

Channels: TV Channel to get Specific Genre of Videos

<img src="https://user-images.githubusercontent.com/34758872/130397297-fae79917-a7b2-4fbf-8648-c818f728497b.png" width="200">
 

Watch Later , User can Favourite and BookMark 

<img src="https://user-images.githubusercontent.com/34758872/130397310-7a346e74-9f63-4ecb-a554-f452088500ae.png" width="200">

## Search Videos

User can Search for Video 

<img src="https://user-images.githubusercontent.com/34758872/130397266-2927c8b5-3902-4c79-8cba-b99af0212f2b.png" width="200">

## Favourite Video and Bookmark Video

User can  Favourite Video by clicking on Pink Favourite Icon  and can Bookamrk using the Bookmark icon on the far top left corner.
Once favourited or Bookmarked, It goes to the Watch Later Section.
    
<img src="https://user-images.githubusercontent.com/34758872/130397281-e7ebebc8-1268-4a15-9880-078c07134d48.png" width="200">
  
<img src="https://user-images.githubusercontent.com/34758872/130397281-e7ebebc8-1268-4a15-9880-078c07134d48.png" width="300">
 
# Api.

    Videos are uploaded to EdgeNet Api, and the content_id is saved for the respective video in Firebase firestore.
    
    1. Configure A Service – Video Enablement, Once Service Configured, we receive the API_KEY and Partner_ID. The encrypted values are present
       in import com.greenmug.newspressplay.utilities.Constants file.
    
    2. Add Api  "/api/v1/stream-tech/content/add" , returns content_id.
    
    3. Get all content details for given partner /api/v1/stream-tech/content/get-all
    
    4. Get url for the given content id. /api/v1/stream-tech/content/get-url/{content_id}

    FireBase FireStore, No Sql datbase : setup google.service.json file has the configuration to connect to Firebase. 
    
    Three Type of Nosql Database,  are created with channel, users and videos
    
    
<img src="https://user-images.githubusercontent.com/34758872/130406038-66a4d959-778b-4f7c-997d-af91f695df3d.png" width="400"> 
 
    
# Type of Videos Tested

Video Type 

1. Clear DASH  - Video added https://storage.googleapis.com/wvmedia/clear/h264/tears/tears.mpd
In app search for "Tears" in search page to view Clear Dash 
Firebase
<img width="1063" alt="Screenshot 2021-08-23 at 1 26 08 PM" src="https://user-images.githubusercontent.com/34758872/130411331-863c3eb9-6c37-4ac7-b16c-fd122236a4cd.png">

2. Smooth Streaming - Video Added https://playready.directtaps.net/smoothstreaming/SSWSS720H264/SuperSpeedway_720.ism/Manifest
In app search for "Race" in search page to view Smooth Streaming

<img width="1066" alt="Screenshot 2021-08-23 at 1 30 31 PM" src="https://user-images.githubusercontent.com/34758872/130412071-50d3057b-f567-4ba8-bc09-62bcddbe324f.png">

3. HLS - https://devstreaming-cdn.apple.com/videos/streaming/examples/img_bipbop_adv_example_fmp4/master.m3u8
In app search for "Gears" in search page to view HLS Content
Firebase
<img width="1064" alt="Screenshot 2021-08-23 at 1 32 47 PM" src="https://user-images.githubusercontent.com/34758872/130412274-219f395f-538b-4cdd-9eab-0c721454d1b9.png">


3. Other - https://firebasestorage.googleapis.com/v0/b/newsfeedpress.appspot.com/o/news1.mp4?alt=media&token=45395a8d-61d0-444b-b975-e9f41b19a126
In app search for "Vaccine" in search page Other type mp4
Firebase
<img width="1065" alt="Screenshot 2021-08-23 at 1 40 54 PM" src="https://user-images.githubusercontent.com/34758872/130413559-e3cee965-b953-45d7-b17c-1e3bb47fc680.png">



# Deployment 

Android Studio Version Used

<img width="300" alt="Screenshot 2021-08-23 at 11 39 31 AM" src="https://user-images.githubusercontent.com/34758872/130398716-d26bb4f3-ebeb-4acd-bd4d-965367fa66bd.png">

Google Service JSON file already provided in App. You can use the same to run the app. 

<img width="800" alt="Screenshot 2021-08-23 at 12 53 50 PM" src="https://user-images.githubusercontent.com/34758872/130407041-0ff4e7fd-33f2-40aa-a448-e231b71689dc.png">

Partner ID and Api Key from Edge Net provided for App. You can use the same to run the app.

Open android Studio, Build and Run on Android Device , Android Device should be Wifi or Network Enabled.

App tested on Google Pixel Devices.

# Android Unit Testing

Open Android Unit Test Folder androidTest, Click on com.greenmug.newspressplay and Run tests . Tests written for ViewModels, EdgeNet Api, Database

<img width="800" alt="Screenshot 2021-08-23 at 11 38 56 AM" src="https://user-images.githubusercontent.com/34758872/130398638-db045cad-576c-42ac-8d6d-a5ce5de5750b.png">


# Security

Api Key and Partner Id, has been encrypted using AES Encryption.

<img width="800" alt="Screenshot 2021-08-23 at 11 41 54 AM" src="https://user-images.githubusercontent.com/34758872/130398983-4c4457ed-d21e-498b-8ca8-c18682a28b0a.png">





