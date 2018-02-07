# DaroonApp

DaroonApp is a in app purchase library


allprojects { 
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}


dependencies {
	        compile 'com.github.FanavaranFakhteh:DaroonApp:1.0.3'
	}
    
    
    
    intialize DaroonApp
    DaroonApp.init(context);
    
    
    
    payment
    DaroonApp.pay(ResultActivity.class,"price","description","users-email","users-number");
    
    
    
    . write the price to Rial
    . you sould fill user's-email or user's-number or both of them
    . if you don't have one of them, send null
    
    Example : 
    
    DaroonApp.pay(ResultActivity.class,"20000","Gold Access","info.DaroonApp@gmail.com",null);
        
        
    
    get Last Transaction:
    
    DaroonApp.getLastTransaction();
        
        
    
    get All Transactions:
    
    DaroonApp.getAllTransactions("users email" , "user's number");
           
    . you sould fill user's-email or user's-number or both of them
    . if you don't have one of them, send null
    
    Example :
    
    DaroonApp.getAllTransactions("info.DaroonApp@gmail.com" , null);
