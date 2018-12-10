# DaroonApp

**Introduction**
----

**DaroonApp** is an In App Purchase library
for Developers who wanna have payment in there Android Applications.
it's easy to use and works for all Applications


for more information please see the [website](http://daroonapp.com)

also you can see [Tutorial](http://telegram.me/daroonapp)

at the end your can download Admin application [here](https://my.daroonapp.com/application/latest/download)

**Download**
----
[![](https://jitpack.io/v/FanavaranFakhteh/DaroonApp.svg)](https://jitpack.io/#FanavaranFakhteh/DaroonApp)

	allprojects { 
			repositories {
				maven { url 'https://jitpack.io' }
			}
		}


	dependencies {
			 implementation 'com.github.FanavaranFakhteh:DaroonApp:1.1.7'
		}
    
**Usage**
----
    
intialize DaroonApp 

	DaroonApp.init(yourActivity.this);
	
add **meta-data** to your AndroidManifest with version's token
(you can get token from [panel](http://daroonapp.com) or [DaroonApp's Admin Application](http://daroonapp.com) after your app's version verified)

if you have any problem, you can see [Tutorial](http://telegram.me/daroonapp)

	  <meta-data
            android:name="daroonApp"
            android:value="Your_Token"/>
	
<h4>payment</h4>

	DaroonApp.pay(ResultActivity.class,"price(necessary)","description(optional)","user's number(necessary)","user's email(optional)");
    
* write the price to **Rial**

* you sould fill user's number but user's email is optional,
if you don't have user's email, send null

<h5>example :</h5> 
    
	DaroonApp.pay(ResultActivity.class,"20000","Gold Access","0912*******",null);
        
<h4>get Last user's Transaction</h4>
    
	DaroonApp.getLastTransaction();

<h4>get All user's Transactions</h4>
    
	DaroonApp.getAllTransactions("user's number(necessary)" , "user's email(optional)");
           
* you should fill user's number but user's email is optional,
if you don't have user's email, send null
    
<h5>example :</h5>
    
	DaroonApp.getAllTransactions("09120000000" , null);

