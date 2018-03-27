import pyrebase
import RPi.GPIO as GPIO
import time
import dht11
import datetime

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.setup(14,GPIO.IN)

config = {
    "apiKey": "--your app api key comes here--",
    "authDomain": "smart-irrigation-system-fa967.firebaseapp.com",
    "databaseURL": "https://smart-irrigation-system-fa967.firebaseio.com",
    "projectId": "smart-irrigation-system-fa967",
    "storageBucket": "smart-irrigation-system-fa967.appspot.com",
    "messagingSenderId": "--your app messaging sender id comes here--"
}
firebase = pyrebase.initialize_app(config)

temperature = "1"
humidity = "1"
while True:
    instance = dht11.DHT11(pin=17)
    result = instance.read()
    if result.is_valid():
        print("Last valid input: " + str(datetime.datetime.now()))
        print("Temperature: %d C" % result.temperature)
        print("Humidity: %d %%" % result.humidity)
        
        if(result.temperature!=0):
            temperature = result.temperature
            humidity = result.humidity
        
    if(GPIO.input(14)==GPIO.LOW):
        values = {
            "temperature": temperature,
            "humidity": humidity,
            "soil_moisture": "healthy"
        }
    else:
        values = {
            "temperature": temperature,
            "humidity": humidity,
            "soil_moisture": "water them"
        }
        
    time.sleep(1)
    print("here")
    
    firebase.database().child("values").set(values)
