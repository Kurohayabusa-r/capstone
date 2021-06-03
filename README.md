# capstone
B21-CAP0397 Capstone Project

## Android
- The android app project is named ``ENDF``, located inside Android folder in the repo.  
So, if you have cloned our repo, in order to build the app, you have to import the ``ENDF`` folder into the Android Studio, not the parent repo folder: ``capstone``.

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
- Create a GCP Project.
- Enable the necessary APIs and SDKs.
``Firestore, Cloud Run, Cloud Storage, Compute Engine, Container Registry, Maps, Places``
- Create an API key that will be used by your Android app to authenticate.
- Connect your Android app to Firebase, this is required in order to be able to use the Firebase SDK.
- Create a Compute Engine instance, this will be used to deploy the flask app and run the scraping scripts.
- Deploying the ML model to Cloud Run
	1. Use the Dockerfile to build a custom image based on the TensorFlow Serving image
		>This Dockerfile will copy the ML folder which contains the model into the container and run the necessary commands
	2. Upload the image to the Container Registry.
	3. Deploy the image from Container Registry to Cloud Run.
- Running the flask app
	1. Make changes in the flask app accordingly.
	2. Copy the flask app into the compute engine instance.
	3. Set up the compute engine instance to run flask.
		>You may refer to this guide for AWS EC2: https://www.datasciencebytes.com/bytes/2015/02/24/running-a-flask-app-on-aws-ec2/
- Running the scraping scripts
	1. Make changes in the scraping scripts accordingly.
	2. Copy the scripts into the compute engine instance.
	3. Run the scripts to start scraping.
		>Personally, I used pm2 to run the scripts
