# Getting Started
In order to build the jar run the following command:
* mvn package

# Creating the Docker Image
The project contains a dockerfile which exposes the application to the port 8080.
In order to build the image and to run container. Execute following commands:
* docker build -t mytoys .
* docker run -d  -p 8080:8080 mytoys

Afterwards it is possible to curl the application with following command:
* curl http://localhost/links?parent=Alter

If you use the correct api-key of course :P 

###  How I approached the work
I used TDD to fulfill the requirements. So first I wrote a MockMvcTest in order test one requirement and implemented that afterwards.
That led to a “minimal” service which implements only the asked requirement and has 100% test coverage.
But this coverage is only accomplished with Integration tests which are slow and alone are not optimal.
The next step would be to implement unit tests in order to test edge cases like null values or min/max values.
Furthermore is the service missing the feature to save further links or navigationEntries. 
If the need would occur to save entries in a DB I would also change the handling of sorting and paging to the Repository like described here for example:
* https://www.baeldung.com/spring-data-sorting

To sum it up there is still a lot of potential to improve the application, but one has always to keep in mind if further improvements are really necessary or does it lead to “over engineering”… 