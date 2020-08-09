# chingari_demo

App uses open weather api to show current weather
Get location from GPS
Fetch weather data from open weather api for that location
Display list using recyclerview of the today's weather forecast (temperature, humidity,
windspeed)

# Tech Stack
This application is written in Kotlin and followed the MVVM and Repository design pattern, tried to separate the modules as much as possible. For network communication have created separate module called api which has api to load the weather from open network. The idea was to have keep the api related stuff in separate module so that can be used in any other application as well.  

To build this application have used following libraries/pattern
- [Retrofit](https://square.github.io/retrofit/)
-[Android architecture components](https://developer.android.com/topic/libraries/architecture)
-[Binding adapters](https://developer.android.com/topic/libraries/data-binding/binding-adapters)
-[Repository pattern]
-[Mock test case for Room database]


![alt text](https://github.com/ernitinjai/chingari_demo/blob/master/screenshot.png)
