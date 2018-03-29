
# Anypoint Template: Error handling Scatter-Gather

+ [License Agreement](#licenseagreement)
+ [Use Case](#usecase)
+ [Considerations](#considerations)
	* [DB Considerations](#dbconsiderations)
	* [Salesforce Considerations](#salesforceconsiderations)
+ [Run it!](#runit)
	* [Running on premise](#runonopremise)
	* [Running on Studio](#runonstudio)
	* [Running on Mule ESB stand alone](#runonmuleesbstandalone)
	* [Running on CloudHub](#runoncloudhub)
	* [Deploying your Anypoint Template on CloudHub](#deployingyouranypointtemplateoncloudhub)
	* [Properties to be configured (With examples)](#propertiestobeconfigured)
+ [API Calls](#apicalls)
+ [Customize It!](#customizeit)
	* [config.xml](#configxml)
	* [businessLogic.xml](#businesslogicxml)
	* [endpoints.xml](#endpointsxml)
	* [errorHandling.xml](#errorhandlingxml)


# License Agreement <a name="licenseagreement"/>
Note that using this template is subject to the conditions of this [License Agreement](AnypointTemplateLicense.pdf).
Please review the terms of the license before downloading and using this template. In short, you are allowed to use the template for free with Mule ESB Enterprise Edition, CloudHub, or as a trial in Anypoint Studio.

# Use Case <a name="usecase"/>
This Template should serve as a foundation for the process of aggregation accounts from Salesforce and Database Instances and compare them to see which accounts can only be found in one of the two and which accounts are in both instances.

As implemented, it gets two accounts, one from Salesforce and other from Database instance. Then it compares by the name of the accounts, and generates a CSV file which shows accounts in A, accounts in B, and accounts in A and B. The report is then e-mailed to a configured group of e-mail addresses.

For practical purposes this Template will generate the result in the format of a CSV Report sent by mail.

# Considerations <a name="considerations"/>

To make this Anypoint Template run, there are certain preconditions that must be considered. All of them deal with the preparations in both, that must be made in order for all to run smoothly.
**Failling to do so could lead to unexpected behavior of the template.**



## DB Considerations <a name="dbconsiderations"/>

This particular Anypoint Template illustrate the aggregation use case between SalesForce and a Database, thus it requires a DB instance to work.

### As source of data

This particular Anypoint Template illustrate the aggregation use case between SalesForce and a Database, thus it requires a DB instance to work.
The Anypoint Template comes package with a MySQL script to create the DB table that uses.
It is the user responsibility to use that script to create the table in an available schema and change the configuration accordingly.
The SQL script file can be found in [src/main/resources/sfdc2jdbc.sql](../master/src/main/resources/sfdc2jdbc.sql)

This template is customized for MySQL. To use it with different SQL implementation, some changes are necessary:

* update SQL script dialect to desired one
* replace MySQL driver library dependency to desired one in [POM](pom.xml)
* replace attribute `driverClassName` of `db:generic-config` element with class name of desired JDBC driver in [src/main/mule/config.xml](../master/src/main/mule/config.xml)
* update JDBC URL in `mule.*.properties` file

## Salesforce Considerations <a name="salesforceconsiderations"/>

There may be a few things that you need to know regarding Salesforce, in order for this template to work.

In order to have this template working as expected, you should be aware of your own Salesforce field configuration.

### FAQ

 - Where can I check that the field configuration for my Salesforce instance is the right one?

    [Salesforce: Checking Field Accessibility for a Particular Field][1]

- Can I modify the Field Access Settings? How?

    [Salesforce: Modifying Field Access Settings][2]


[1]: https://help.salesforce.com/HTViewHelpDoc?id=checking_field_accessibility_for_a_particular_field.htm&language=en_US
[2]: https://help.salesforce.com/HTViewHelpDoc?id=modifying_field_access_settings.htm&language=en_US


### As destination of data

There are no particular considerations for this Anypoint Template regarding Salesforce as data destination.









# Run it! <a name="runit"/>
Simple steps to get Salesforce and Salesforce User Aggregation running.


## Running on premise <a name="runonopremise"/>
In this section we detail the way you should run your Anypoint Template on your computer.


### Where to Download Mule Studio and Mule ESB
First thing to know if you are a newcomer to Mule is where to get the tools.

+ You can download Mule Studio from this [Location](http://www.mulesoft.com/platform/mule-studio)
+ You can download Mule ESB from this [Location](http://www.mulesoft.com/platform/soa/mule-esb-open-source-esb)


### Importing an Anypoint Template into Studio
Mule Studio offers several ways to import a project into the workspace, for instance:

+ Anypoint Studio Project from File System
+ Packaged mule application (.jar)

You can find a detailed description on how to do so in this [Documentation Page](http://www.mulesoft.org/documentation/display/current/Importing+and+Exporting+in+Studio).


### Running on Studio <a name="runonstudio"/>
Once you have imported you Anypoint Template into Anypoint Studio you need to follow these steps to run it:

+ Locate the properties file `mule.dev.properties`, in src/main/resources
+ Complete all the properties required as per the examples in the section [Properties to be configured](#propertiestobeconfigured)
+ Once that is done, right click on you Anypoint Template project folder
+ Hover you mouse over `"Run as"`
+ Click on  `"Mule Application"`

### Running on Mule ESB stand alone <a name="runonmuleesbstandalone"/>
Complete all properties in one of the property files, for example in [mule.prod.properties](../master/src/main/resources/mule.prod.properties) and run your app with the corresponding environment variable to use it. To follow the example, this will be `mule.env=prod`.


## Running on CloudHub <a name="runoncloudhub"/>
While [creating your application on CloudHub](http://www.mulesoft.org/documentation/display/current/Hello+World+on+CloudHub) (Or you can do it later as a next step), you need to go to Deployment > Advanced to set all environment variables detailed in **Properties to be configured** as well as the **mule.env**.


### Deploying your Anypoint Template on CloudHub <a name="deployingyouranypointtemplateoncloudhub"/>
Mule Studio provides you with really easy way to deploy your Template directly to CloudHub, for the specific steps to do so please check this [link](http://www.mulesoft.org/documentation/display/current/Deploying+Mule+Applications#DeployingMuleApplications-DeploytoCloudHub)


## Properties to be configured (With examples) <a name="propertiestobeconfigured"/>
In order to use this Mule Anypoint Template you need to configure properties (Credentials, configurations, etc.) either in properties file or in CloudHub as Environment Variables. Detail list with examples:
### Application configuration
**HTTP Connector configuration**

+ http.port `9090`

**Database Connector configuration**

+ db.host `localhost`
+ db.port `3306`
+ db.user `mule`
+ db.password `mule`
+ db.databasename `mule`

**SalesForce Connector configuration**

+ sfdc.username `bob.dylan@sfdc`
+ sfdc.password `DylanPassword123`
+ sfdc.securityToken `avsfwCUl7apQs56Xq2AKi3X`

**SMTP Services configuration**

+ smtp.host `smtp.gmail.com`
+ smtp.port `587`
+ smtp.user `your%40email.com`
+ smtp.password `password`

**Mail details**

+ mail.from `your@email.com`
+ mail.to `your@email.com`
+ mail.subject `Mail subject`
+ mail.body `Please find attached your Accounts Report`
+ attachment.name `accounts_report.csv`

# API Calls <a name="apicalls"/>
Salesforce imposes limits on the number of API Calls that can be made. However, we make API call to Salesforce only once during aggregation.


# Customize It!<a name="customizeit"/>
This brief guide intends to give a high level idea of how this Anypoint Template is built and how you can change it according to your needs.
As mule applications are based on XML files, this page will be organized by describing all the XML that conform the Anypoint Template.
Of course more files will be found such as Test Classes and [Mule Application Files](http://www.mulesoft.org/documentation/display/current/Application+Format), but to keep it simple we will focus on the XMLs.

Here is a list of the main XML files you'll find in this application:

* [config.xml](#configxml)
* [endpoints.xml](#endpointsxml)
* [businessLogic.xml](#businesslogicxml)
* [errorHandling.xml](#errorhandlingxml)


## config.xml<a name="configxml"/>
Configuration for Connectors and [Configuration Properties](http://www.mulesoft.org/documentation/display/current/Configuring+Properties) are set in this file. **Even you can change the configuration here, all parameters that can be modified here are in properties file, and this is the recommended place to do it so.** Of course if you want to do core changes to the logic you will probably need to modify this file.

In the visual editor they can be found on the *Global Element* tab.


## businessLogic.xml<a name="businesslogicxml"/>
Functional aspect of the Template is implemented on this XML, directed by one flow responsible of conducting the aggregation of data, comparing records and finally formating the output, in this case being a report.

Using Scatter-Gather component we are querying the data in different systems. After that the aggregation is implemented in DataWeave 2 script using Transform component. Aggregated results are sorted by source of existence:

1. Users only in Salesforce A
2. Users only in Salesforce B
3. Users in both Salesforce A and Salesforce B
and transformed to CSV format. Final report in CSV format is sent to email, that you configured in mule.\*.properties file.


## endpoints.xml<a name="endpointsxml"/>
This is the file where you will found the endpoint to start the aggregation.
This Template has a [HTTP Listener Connector](http://www.mulesoft.org/documentation/display/current/HTTP+Listener+Connector) as the way to trigger the use case.

### Trigger Flow
**HTTP Listener Connector** - Start Report Generation

+ `${http.port}` is set as a property to be defined either on a property file or in CloudHub environment variables.
+ The path configured by default is `generatereport` and you are free to change for the one you prefer.
+ The host name for all endpoints in your CloudHub configuration should be defined as `localhost`. CloudHub will then route requests from your application domain URL to the endpoint.



## errorHandling.xml<a name="errorhandlingxml"/>
This is the right place to handle how your integration will react depending on the different exceptions.
This file holds a [Error Handling](http://www.mulesoft.org/documentation/display/current/Error+Handling) that is referenced by the main flow in the business logic.

To see how error handling works you need to cause an error defined in error handling components, e.g. set wrong credentials or make a mistake in query to database.
