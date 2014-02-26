
# Mule Kick: User Aggregation

+ [Use Case](#usecase)
+ [Run it!](#runit)
    * [Running on CloudHub](#runoncloudhub)
    	* [Deploying your Kick on CloudHub](#deployingyourkickoncloudhub)
    * [Running on premise](#runonopremise)
        * [Properties to be configured](#propertiestobeconfigured)
+ [Customize It!](#customizeit)
    * [config.xml](#configxml)
    * [inboundEndpoints.xml](#inboundendpointsxml)
    * [businessLogic.xml](#businesslogicxml)
    * [errorHandling.xml](#errorhandlingxml)
+ [Testing the Kick](#testingthekick)

# Use Case <a name="usecase"/>
As a Salesforce admin I want to aggregate users from a Salesforce Instances and a DataBase, and compare them to see which users can only be found in one of the two and which users are in both. 

For practical purposes this kick will generate the result in the format of a CSV Report sent by mail.

This Kick (template) should serve as a foundation for extracting data from two systems, aggregating data, comparing values of fields for the objects, and generating a report on the differences. 

As implemented, it gets users from a Salesforce instance and a DB table, compares by the email address of the users, and generates a CSV file which shows users in A, users in B (being "B" the DB), and Users in A and B. The report is then emailed to a configured group of email addresses.

# Run it! <a name="runit"/>

Simple steps to get User Aggregation running.

**Note:** This particular Kick ilustrate the aggregation use case between SalesForce and a Data Base, thus it requires a DB instance to work.
The Kick comes package with a SQL script to create the DB table that uses. 
It is the user responsability to use that script to create the table in an available schema and change the configuration accordingly.
The SQL script file can be found in [src/main/resources/sfdc2jdbc.sql] (../blob/master/src/main/resources/sfdc2jdbc.sql)

## Running on CloudHub <a name="runoncloudhub"/>

While [creating your application on CloudHub](http://www.mulesoft.org/documentation/display/current/Hello+World+on+CloudHub) (Or you can do it later as a next step), you need to go to Deployment > Advanced to set all environment variables detailed in **Properties to be configured** as well as the **mule.env**. 

Once your app is all set and started, supposing you choose as domain name `useraggregation` to trigger the use case you just need to hit `http://useraggregation.cloudhub.io/generatereport` and report will be sent to the emails configured.

### Deploying your Kick on CloudHub <a name="deployingyourkickoncloudhub"/>
Mule Studio provides you with really easy way to deploy your Kick directly to CloudHub, for the specific steps to do so please check this [link](http://www.mulesoft.org/documentation/display/current/Deploying+Mule+Applications#DeployingMuleApplications-DeploytoCloudHub)


## Running on premise <a name="runonopremise"/>
Complete all properties in one of the property files, for example in [mule.prod.properties] (../blob/master/src/main/resources/mule.prod.properties) and run your app with the corresponding environment variable to use it. To follow the example, this will be `mule.env=prod`.

After this, to trigger the use case you just need to hit the local http endpoint with the port you configured in your file. If this is, for instance, `9090` then you should hit: `http://localhost:9090/synccontacts` and this will create a CSV report and send it to the mails set.

## Properties to be configured (With examples)<a name="propertiestobeconfigured"/>
In order to use this Mule Kick you need to configure properties (Credentials, configurations, etc.) either in properties file or in CloudHub as Environment Variables. Detail list with examples:

### Application configuration
+ http.port `9090` 

#### SalesForce Connector configuration for company A
+ sfdc.a.username `bob.dylan@orga`
+ sfdc.a.password `DylanPassword123`
+ sfdc.a.securityToken `avsfwCUl7apQs56Xq2AKi3X`
+ sfdc.a.url `https://login.salesforce.com/services/Soap/u/26.0`

#### Database Connector configuration
+ db.host=localhost
+ db.port=3306
+ db.user=root
+ db.password=
+ db.databasename=sfdc2jdbc


#### SMPT Services configuration
+ smtp.host `smtp.gmail.com`
+ smtp.port `587`
+ smtp.user `exampleuser@gmail.com`
+ smtp.password `ExamplePassword456`

#### Mail details
+ mail.from `exampleuser@gmail.com`
+ mail.to `woody.guthrie@gmail.com`
+ mail.subject `SFDC Users Report`
+ mail.body `Users report comparing users from SFDC Accounts`
+ attachment.name `OrderedReport.csv`

# Customize It!<a name="customizeit"/>
This brief guide intends to give a high level idea of how this Kick is built and how you can change it according to your needs.
As mule applications are based on XML files, this page will be organised by describing all the XML that conform the Kick.
Of course more files will be found such as Test Classes and [Mule Application Files](http://www.mulesoft.org/documentation/display/current/Application+Format), but to keep it simple we will focus on the XMLs.

Here is a list of the main XML files you'll find in this application:

* [config.xml](#configxml)
* [endpoints.xml](#endpointsxml)
* [businessLogic.xml](#businesslogicxml)
* [errorHandling.xml](#errorhandlingxml)


## config.xml<a name="configxml"/>
This file holds the configuration for Connectors and [Properties Place Holders](http://www.mulesoft.org/documentation/display/current/Configuring+Properties) are set in this file. 
**Even you can change the configuration here, all parameters that can be modified here are in properties file, and this is the recommended place to do it so.** 
Of course if you want to do core changes to the logic you will probably need to modify this file.

In the visual editor they can be found on the *Global Element* tab.


## endpoints.xml<a name="endpointsxml"/>
This is the file where you will found the inbound and outbound sides of your integration app.
This Kick has an [HTTP Inbound Endpoint](http://www.mulesoft.org/documentation/display/current/HTTP+Endpoint+Reference) as the way to trigger the use case and an [SMTP Transport](http://www.mulesoft.org/documentation/display/current/SMTP+Transport+Reference) as the outbound way to send the report.

###  Trigger Flow
**HTTP Inbound Endpoint** - Start Report Generation
+ `${http.port}` is set as a property to be defined either on a property file or in CloudHub environment variables.
+ The path configured by default is `generatereport` and you are free to change for the one you prefer.
+ The host name for all endpoints in your CloudHub configuration should be defined as `localhost`. CloudHub will then route requests from your application domain URL to the endpoint.

###  Outbound Flow
**SMTP Outbound Endpoint** - Send Mail
+ Both SMTP Server configuration and the actual mail to be sent are defined in this endpoint.
+ This flow is going to be invoked from the flow that does all the functional work: *mainFlow*, the same that is invoked from the Inbound Flow upon triggering of the HTTP Endpoint.

## businessLogic.xml<a name="businesslogicxml"/>
Functional aspect of the kick is implemented on this XML, directed by one flow responsible of conducting the aggregation of data, comparing records and finally formating the output, in this case being a report.
The *mainFlow* organises the job in three different steps and finally invokes the *outboundFlow* that will deliver the report to the corresponding outbound endpoint.
This flow has Exception Strategy that basically consists on invoking the *defaultChoiseExceptionStrategy* defined in *errorHandling.xml* file.


###  Gather Data Flow
Mainly consisting of two calls (Queries) one to SalesForce and one to a DB and storing each response on the Invocation Variable named *usersFromOrgA* or *usersFromDB* accordingly.

###  Aggregation Flow
[Java Transformer](http://www.mulesoft.org/documentation/display/current/Java+Transformer+Reference) responsible for aggregating the results from the two Lists of Users.
Criteria and format applied:
+ Transformer receives a Mule Message with the two Invocation variables *usersFromOrgA* and *usersFromDB* to result in List of Maps with keys: **Name**, **Email**, **IDInA**, **UserNameInA**, **IDInB** and 
**UserNameInB**.
+ Users will be matched by mail, that is to say, a record in both SFDC organisations with same mail is considered the same user.

###  Format Output Flow
+ [Java Transformer](http://www.mulesoft.org/documentation/display/current/Java+Transformer+Reference) responsible for sorting the list of users in the following order:

1. Users only in Org A
2. Users only in Org B
3. Users in both Org A and Org B

All records ordered alphabetically by mail within each category.
If you want to change this order then the *compare* method should be modified.

+ CSV Report [DataMapper](http://www.mulesoft.org/documentation/display/current/Datamapper+User+Guide+and+Reference) transforming the List of Maps in CSV with headers **Name**, **Email**, **IDInA**, **UserNameInA**, **IDInB** and **UserNameInB**.
+ An [Object to string transformer](http://www.mulesoft.org/documentation/display/current/Transformers) is used to set the payload as an String. 




## errorHandling.xml<a name="errorhandlingxml"/>
Contains a [Catch Exception Strategy](http://www.mulesoft.org/documentation/display/current/Catch+Exception+Strategy) that is only Logging the exception thrown (If so). As you imagine, this is the right place to handle how your integration will react depending on the different exceptions. 



## Testing the Kick <a name="testingthekick"/>

You will notice that the Kick has been shipped with test.
These devidi them self into two categories:

+ Unit Tests
+ Integration Tests

You can run any of them by just doing right click on the class and clicking on run as Junit test.

Do bear in mind that you'll have to tell the test classes which property file to use.
For you convinience we have added a file mule.test.properties located in "src/test/resources".
In the run configurations of the test just make sure to add the following property:

+ -Dmule.env=test