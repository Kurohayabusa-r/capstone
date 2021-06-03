# capstone
B21-CAP0397 Capstone Project

## Android
- The android app project is named ``ENDF``, located inside Android folder in the repo.  
So, if you have cloned out repo, in order to build the app, you have to import the ``ENDF`` folder into the Android Studio, not the parent repo folder: ``capstone``.

- The whole project is written in ``Kotlin``.
- In order for the app to run, you have to provide the following before building the app:  
	1. Firebase collection credential in ``google-services.json``.  
	
		This is required under the assumption that you have set up a collection on Firebase and populate it with data you want to visualize in the app. In our context,  it is the earthquakes record from BMKG. This credential is needed to authenticate the request for fetching the data from the app. To setup the credential, place the google-services.json file inside the app folder of the project.  
		
		However, you can go with other alternatives on how and where you want to host your data. This way, the firebase credential is no longer needed.
		
	2. Google Maps API key.  
	
		Place the API key in ``gradle.properties`` and ``local.properties`` file. Both are located in``app`` folder.  
	
		This API key is needed for two main servces: ``Markers`` and ``Places Autocomplete``. For the detailed documentation, refer to:
		- Markers: https://developers.google.com/maps/documentation/android-sdk/marker
		- Places Autocomplete: https://developers.google.com/maps/documentation/places/android-sdk/autocomplete

	

## Machine Learning

## Cloud
