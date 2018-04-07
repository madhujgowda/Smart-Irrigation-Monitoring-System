import pyrebase
import RPi.GPIO as GPIO
import time
import dht11
import datetime
import urllib
import urllib2
import cookielib
from getpass import getpass
import sys

GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.setup(14,GPIO.IN) #for soil moisture sensor
GPIO.setup(15,GPIO.OUT) #for relay switch

# Initialize Firebase
#Please edit the below lines according to your firebase initialization
config = {
    "apiKey": "**your api key comes here**",
    "authDomain": "**your auth domain comes here**",
    "databaseURL": "**your database url comes here**",
    "projectId": "**your project id comes here**",
    "storageBucket": "**your storage bucket comes here**",
    "messagingSenderId": "**your messaging sender id comes here**"
}
firebase = pyrebase.initialize_app(config)

temperature = "1` c"
humidity = "1 %"
prevres = firebase.database().child("soil_moisture").get()
prevmoisture = prevres.val()

while True:
    #get the mobile number from the database
    numres = firebase.database().child("number").get()
    num = numres.val()

    #to know the current moisture level from the sensors.
    GPIO.setmode(GPIO.BCM)
    GPIO.setup(14,GPIO.IN)
    if(GPIO.input(14)==GPIO.LOW):
        curmoisture = "High"
    else:
        curmoisture = "Low"
    
    #to know whether the relay switch is in on or in off
    relay_switch = firebase.database().child("relay_switch").get()
    if(relay_switch.val() == "ON"):
        print("ON")
        GPIO.setmode(GPIO.BCM)
        GPIO.setup(15,GPIO.OUT)
        GPIO.output(15,1)
    else:
        print("OFF")
        GPIO.setmode(GPIO.BCM)
        GPIO.setup(15,GPIO.OUT)
        GPIO.output(15,0)
        
    GPIO.setmode(GPIO.BCM)
    GPIO.setup(14,GPIO.IN)
    instance = dht11.DHT11(pin=17)
    result = instance.read()
    if result.is_valid():
        #if we are getting values from the sensors, then it means the sensor wires are in proper and we say that sensors are connected.
        firebase.database().child("sensors").set("Connected")
        print("Last valid input: " + str(datetime.datetime.now()))
        print("Temperature:  " + str(result.temperature))
        print("Humidity: " + str(result.humidity))
        
        if(result.temperature!=0):
            temperature = str(result.temperature) + "` c"
            humidity = str(result.humidity) + " %"
    else:
        print("not connected")
        firebase.database().child("sensors").set("Disconnected")

    if(curmoisture=="High"):
        print("high")
        values = {
            "temperature": temperature,
            "humidity": humidity,
        }
        if(prevmoisture!=curmoisture):
            print("send message as high moisture")
            #please fill your own username and password for way2sms.com
            username = "**your username for the way2sms goes here**"
            passwd = "**your password comes here**"
            message = "Soil Moisture is HIGH.. Please switch OFF the water pump"
            number = str(num)
            message = "+".join(message.split(' '))
            print "entered to send sms" 
            #Logging into the SMS Site
            url = 'http://site24.way2sms.com/Login1.action?'
            data = 'username='+username+'&password='+passwd+'&Submit=Sign+in'
             
            #For Cookies:
            cj = cookielib.CookieJar()
            opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(cj))
             
            # Adding Header detail:
            opener.addheaders = [('User-Agent','Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36')]
             
            try:
                usock = opener.open(url, data)
            except IOError:
                print "Error while logging in."
                sys.exit(1)
             
            #sending sms for the number
            jession_id = str(cj).split('~')[1].split(' ')[0]
            send_sms_url = 'http://site24.way2sms.com/smstoss.action?'
            send_sms_data = 'ssaction=ss&Token='+jession_id+'&mobile='+number+'&message='+message+'&msgLen=136'
            opener.addheaders = [('Referer', 'http://site25.way2sms.com/sendSMS?Token='+jession_id)]
             
            try:
                sms_sent_page = opener.open(send_sms_url,send_sms_data)
            except IOError:
                print "Error while sending message"
                
            prevmoisture = "High";
    else:
        print("low")
        values = {
            "temperature": temperature,
            "humidity": humidity,
        }
        if(prevmoisture!=curmoisture):
            print("send message as low moisture")
            #please fill your own username and password for way2sms.com
            username = "**your username for the way2sms goes here**"
            passwd = "**your password comes here**"
            message = "Soil Moisture is LOW.. Please switch ON the water pump"
            number = str(num)
            message = "+".join(message.split(' '))
            print "entered to send sms" 
            #Logging into the SMS Site
            url = 'http://site24.way2sms.com/Login1.action?'
            data = 'username='+username+'&password='+passwd+'&Submit=Sign+in'
             
            #For Cookies:
            cj = cookielib.CookieJar()
            opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(cj))
             
            # Adding Header detail:
            opener.addheaders = [('User-Agent','Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120 Safari/537.36')]
             
            try:
                usock = opener.open(url, data)
            except IOError:
                print "Error while logging in."
                sys.exit(1)
             
            #sending sms for the number
            jession_id = str(cj).split('~')[1].split(' ')[0]
            send_sms_url = 'http://site24.way2sms.com/smstoss.action?'
            send_sms_data = 'ssaction=ss&Token='+jession_id+'&mobile='+number+'&message='+message+'&msgLen=136'
            opener.addheaders = [('Referer', 'http://site25.way2sms.com/sendSMS?Token='+jession_id)]
             
            try:
                sms_sent_page = opener.open(send_sms_url,send_sms_data)
            except IOError:
                print "Error while sending message"
                

            print "SMS has been sent."
            prevmoisture = "Low";
        
    time.sleep(1)
    
    #writing values back to the database.
    firebase.database().child("values").set(values)
    firebase.database().child("soil_moisture").set(curmoisture)