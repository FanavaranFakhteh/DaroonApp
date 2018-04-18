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

	DaroonApp.init(context);
	
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
	
<h4>cusomize Pay Activity</h4>
also you can cusomize your pay activity

set color to your statusBar

	DaroonApp.setStatusBarColor(your_color);
	
set color to your actionBar

	DaroonApp.setActionBarColor(your_color);

set color to your progressBar

	DaroonApp.setProgressBarColor(your_color);
	
<h5>example :</h5>

	DaroonApp.setStatusBarColor(R.color.colorAccent);
	DaroonApp.setActionBarColor(R.color.colorAccent);
	DaroonApp.setProgressBarColor(R.color.colorAccent);
