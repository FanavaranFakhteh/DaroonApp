# DaroonApp

**Introduction**
----

**DaroonApp** is an In App Purchase library
for Developers who wanna have payment in there Android Applications.
it's easy to use and works for all Applications


for more information please see the [website](http://daroonapp.com)

**Download**
----

	allprojects { 
			repositories {
				maven { url 'https://jitpack.io' }
			}
		}


	dependencies {
			compile 'com.github.FanavaranFakhteh:DaroonApp:1.0.5'
		}
    
**Usage**
----
    
intialize DaroonApp 

	DaroonApp.init(yourActivity.this);
	
<h4>payment</h4>

	DaroonApp.pay(ResultActivity.class,"price","description","users-number","users-email");
    
* write the price to Rial

* you sould fill user's-email or user's-number or both of them,
if you don't have one of them, send null

<h5>example :</h5> 
    
	DaroonApp.pay(ResultActivity.class,"20000","Gold Access",null,"info.DaroonApp@gmail.com");
        
<h4>get Last Transaction</h4>
    
	DaroonApp.getLastTransaction();

<h4>get All Transactions</h4>
    
	DaroonApp.getAllTransactions("users number" , "user's email");
           
* you sould fill user's-email or user's-number or both of them,
if you don't have one of them, send null
    
<h5>example :</h5>
    
	DaroonApp.getAllTransactions("09120000000" , null);

