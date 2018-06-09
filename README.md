# DaroonApp

**Introduction**
----

**DaroonApp** is an In App Purchase library
for Developers who wanna have payment in there Android Applications.
it's easy to use and works for all Applications


for more information please see the [website](http://daroonapp.com)

also you can see [Tutorial](http://daroonapp.com)

**Download**
----
[![](https://jitpack.io/v/FanavaranFakhteh/DaroonApp.svg)](https://jitpack.io/#FanavaranFakhteh/DaroonApp)

	allprojects { 
			repositories {
				maven { url 'https://jitpack.io' }
			}
		}


	dependencies {
			compile 'com.github.FanavaranFakhteh:DaroonApp:1.1.0'
		}
    
**Usage**
----
    
intialize DaroonApp 

	DaroonApp.init(yourActivity.this);
	
add **meta-data** to your AndroidManifest with version's token
(you can get token from [panel](http://daroonapp.com) or [DaroonApp's Admin Application](http://daroonapp.com) after your app's version verified)

if you have any problem, you can see [Tutorial](http://daroonapp.com)

	  <meta-data
            android:name="daroonApp"
            android:value="Your_Token"/>
	
<h4>payment</h4>

	DaroonApp.pay(ResultActivity.class,"price","description","user's number","user's email");
    
* write the price to **Rial**

* you sould fill user's-email or user's-number or both of them,
if you don't have one of them, send null

<h5>example :</h5> 
    
	DaroonApp.pay(ResultActivity.class,"20000","Gold Access",null,"info.DaroonApp@gmail.com");
        
<h4>get Last Transaction</h4>
    
	DaroonApp.getLastTransaction();

<h4>get All Transactions</h4>
    
	DaroonApp.getAllTransactions("user's number" , "user's email");
           
* you sould fill user's-email or user's-number or both of them,
if you don't have one of them, send null
    
<h5>example :</h5>
    
	DaroonApp.getAllTransactions("09120000000" , null);

