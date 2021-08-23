# NewsPressPlay

<img src="https://user-images.githubusercontent.com/34758872/130397136-0f55361f-7965-438f-b1c7-bba1e04c30a2.png" width="300">

#Video Link : https://drive.google.com/file/d/10D-vWKOdMeu5jPKDWARYPp1YvySrpgsA/view?usp=sharing

# App Features

1. SignUp/SignIn : Using Google Firebase for Authentication, 

<img src="https://user-images.githubusercontent.com/34758872/130397139-b936949e-7e60-478d-ba62-93346724c80d.png" width="300">
    
     
<img src="https://user-images.githubusercontent.com/34758872/130397158-2c12498a-abf3-4c26-80cb-d4c078f72af4.png" width="300">

 1.3 Validations all fields are mandatory for Signup, else we will get a Toast message with Error
 SignUp, 
 2. Confirm Password and Password Should be Same
 3. Email should be valid


  
      


2. Bottom Navigation, has 3 pages
    
    1. Popular OR Trending Videos

     <img src="https://user-images.githubusercontent.com/34758872/130397221-a50ac554-3202-4530-a34f-bc7636ba351b.png" width="300">
     

     Channels: TV Channel to get Specific Genre of Videos

    <img src="https://user-images.githubusercontent.com/34758872/130397297-fae79917-a7b2-4fbf-8648-c818f728497b.png" width="300">
 

    Watch Later , User can Favourite and BookMark 

    <img src="https://user-images.githubusercontent.com/34758872/130397310-7a346e74-9f63-4ecb-a554-f452088500ae.png" width="300">

3. Search Videos

    User can Search for Video 

     <img src="https://user-images.githubusercontent.com/34758872/130397266-2927c8b5-3902-4c79-8cba-b99af0212f2b.png" width="300">

4. Favourite Video

    User can view Favourite Video 
    
     <img src="https://user-images.githubusercontent.com/34758872/130397281-e7ebebc8-1268-4a15-9880-078c07134d48.png" width="300">

5. Bookmark Video
    
    User can view Bookmarkk Video 
  
<img src="https://user-images.githubusercontent.com/34758872/130397281-e7ebebc8-1268-4a15-9880-078c07134d48.png" width="300">
 
# Api.

    Videos are uploaded to EdgeNet Api, and the content_id is saved in
    
    1. Configure A Service – Video Enablement, Once Service Configured, we receive the API_KEY and Partner_ID. The encrypted values are present
       in import com.greenmug.newspressplay.utilities.Constants file.
    
    2. Add Api  "/api/v1/stream-tech/content/add" , returns content_id.
    
    3. Get all content details for given partner /api/v1/stream-tech/content/get-all
    
    4. Get url for the given content id. /api/v1/stream-tech/content/get-url/{content_id}

    FireBase FireStore, No Sql datbase : setup google.service.json file has the configuration to connect to Firebase. Three Type of Database, 
    are created channel, users and videos
    
    <img width="994" alt="Screenshot 2021-08-23 at 12 45 02 PM" src="https://user-images.githubusercontent.com/34758872/130406038-66a4d959-778b-4f7c-997d-af91f695df3d.png">

    
  
    
# Type of Videos Tested

# Deployment 

Android Studio Version Used

<img width="300" alt="Screenshot 2021-08-23 at 11 39 31 AM" src="https://user-images.githubusercontent.com/34758872/130398716-d26bb4f3-ebeb-4acd-bd4d-965367fa66bd.png">

Open android Studio, Build and Run on Android Device

App tested on Google Pixel Devices

# Android Unit Testing

Open Android Unit Test Folder androidTest, Click on com.greenmug.newspressplay and Run tests . Tests written for ViewModels, EdgeNet Api, Database

<img width="800" alt="Screenshot 2021-08-23 at 11 38 56 AM" src="https://user-images.githubusercontent.com/34758872/130398638-db045cad-576c-42ac-8d6d-a5ce5de5750b.png">


# Security

Api Key and Partner Id, has been encrypted using AES Encryption.


<img width="800" alt="Screenshot 2021-08-23 at 11 41 54 AM" src="https://user-images.githubusercontent.com/34758872/130398983-4c4457ed-d21e-498b-8ca8-c18682a28b0a.png">





