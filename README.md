# DemoProject

Installation :
Android Studio (or Gradle)
No need to clone the repository or download any files -- just add this line to your app's build.gradle inside the dependencies section:

    compile 'com.opensource:opendata:0.0.12'

                        (or)
    implementation 'com.opensource:opendata:0.0.12' if you are using Android Studio version 3.0
Note: We recommend you to use

    compile 'com.opensource:opendata:1.+'
Firstly to use this library you need to generete your application api key from http://209.58.136.23:2001


Creating API key
To generate your key please do follow the below steps:

   Registration process :-

Enter your details to create account
Name (Length should not exceed 40 characters)
Company (Company name) 
Contact Number (Contact should be integers only) 
Email Address (Valid email address)
Password(Password should be min 6 characters)
You will get an OTP to your email address which you have given
Enter your  6 digit OTP there, If your OTP verified once, your account will be created
You have to login to check you account status
 
Login Process :-
Enter your login credentials.
Email Address (Email address, with which you have registered)
Password
Once your credentials verified, you have logged in to your account. Overview page will be displayed.
Your account status will be in Review state until your account will be approved by the admin
After admin approval your account will be in active state and will be provided a API key.
You can check your API key in Settings page.


Usage :
To initiate our library, place the below code at initial activity of your application(ex: splash activity).

OpendataApp.initialize(context, apiKey, emailId, advertiserId);

	context -> your application context
	apiKey -> your registered application API key from http://209.58.136.23:2001
	emailId -> primary emailId from the mobile
	advertiserId -> it is the google advertiserId from the mobile.


implement OpenDataCallbacks in the same Activity where you are calling above initialize() method, so that Callback methods will be overriden and you can redirect the user accourdingly with success, failure functions accordingly.

MainActivity extends AppCompatActivity implements OpenDataCallbacks

@Override
      public void onDataSuccess(String message, String data)  {
       //TODO for successful request with data as response

    }

    @Override
     public void onDataFailure(String message) {
        //TODO for failure with failure message.

    }
