## Module 06: Web Development with Spring MVC

### HTTP Request Methods
- GET
	- is a request for a resource (html ﬁle, javascript ﬁle, image, etc)
	- is used when you visit a website.
- HEAD
	- is like GET, but only asks for meta information without the body.
- POST
	- is used to post data to the server.
	- typical use case for POST is to post form data to the server (like a checkout form)
- PUT is a create OR update request
	- is a request for the enclosed entity be stored at the supplied URI.
	- if the entity exists, it is expected to be updated.
- POST is a create request
- DELETE
	- Is a request to delete the speciﬁed resource
- TRACE
	- Will echo the received request. Can be used to see if request was altered by intermediate servers
- OPTIONS
	- Returns the HTTP methods supported by the server for the speciﬁed URL
- CONNECT
	- Converts the request to a transparent TCP/IP tunnel, typically for HTTPS through an unencrypted HTTP proxy
- PATCH
	- Applies partial modiﬁcations to the speciﬁed resource

**Safe Methods**

Safe Methods are considered safe to use because they only fetch information and do not cause changes on the server.

The Safe Methods are: GET, HEAD, OPTIONS, and TRACE

**Idempotent Methods**

Idempotence
- A quality of an action such that repetitions of the action have no further effect on the outcome.
- Clients can make thge same call repeatedly while producing the same result.

PUT and DELETE are Idempotent Methods.

Safe Methods (GET, HEAD, TRACE, OPTIONS) are also Idempotent.

**Non-Idempotent Methods**

POST is NOT Idempotent
Multiple Posts are likely to create multiple resources

### HTTP Status Codes
- 100 series are informational in nature
	- 200 Okay;
	201 Created;
	204 Accepted
- 200 series indicate successful request
	- 301 Moved Permanently
- 300 series are redirection
	- 301 Moved Permanently
- 400 series are client errors
	- 400 Bad Request, can not process due to the client error;
	- 401 Not Authorized;
	- 404 Not Found - Resource not found
	- 405 Method not allowed, HTTP method not allowed
	- 409 Conflict, possible with simultaneous updates
	- 417 Expectations failed, sometimes used with RESTFull interfaces
- 500 series are server side errors
	- 500 Internal Server Error; 503 Service Unavailable

### Web Dev Tools
- Chrome Developer Tool
- Axis TCP Monitor Plugin
    - InteliJ plugin to monitor and inspect HTTP request
- Spring Boot Dev Tools
    - Run the application is faster.
    - It is just necessary built the project
    - It is necessary add pom dependency
- Live Reload - browser plugin to automatically update the page when change the code
    - It is __NOT__ necessary reload the application