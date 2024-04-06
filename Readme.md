#Readme 

This a spring boot application uses gradle for dependency

To Run in development 

```
./gradlew bootRun    
```

To Build

```
./gradlew bootJar 
```


To Run

```
java -jar ./build/libs/drivers-0.0.1-SNAPSHOT.jar
```
## API

List
```
http://demo.devmakk.com/api/v1/drivers

```

Search 
```
http://demo.devmakk.com/api/v1/drivers/search?employeeId=611575
```


Top 10 By Km Driven in short time, ```limit``` is optional
```
http://demo.devmakk.com/api/v1/drivers/topKmDriven?limit=10
```


Top 10 By Safe Speed, ```limit``` is optional
```
http://demo.devmakk.com/api/v1/drivers/topSafeSpeed?limit=10
```
