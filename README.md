
# Anypoint Template: Error handling Scatter-Gather

This template aggregates users from a Salesforce instance and database, compares the records to avoid duplication, and transfers the records to a CSV file that after processing is sent as an email attachment. 

![6e1becd3-ecf4-44a8-8dd8-d74233491797-image.png](https://exchange2-file-upload-service-kprod.s3.us-east-1.amazonaws.com:443/6e1becd3-ecf4-44a8-8dd8-d74233491797-image.png)

This application also shows you how to define and handle exceptions such as log in issues, field mismatch between the different source systems, and more.

![](https://www.youtube.com/embed/u7kKnHS5qio?wmode=transparent)

# License Agreement

This template is subject to the conditions of the [MuleSoft License Agreement](https://s3.amazonaws.com/templates-examples/AnypointTemplateLicense.pdf"). Review the terms of the license before downloading and using this template. You can use this template for free with the Mule Enterprise Edition, CloudHub, or as a trial in Anypoint Studio. 

# Use Case

This template aggregates a list of users from Salesforce and a database instance and compares them to see which users can only be found in one of the two and which users are in both instances. The template generates a CSV file which shows users in A, users in B, and users in A and B. The report is then emailed to a configured group of e-mail addresses.

# Considerations

To make this template run, there are certain preconditions that must be considered. All of them deal with the preparations in both, that must be made for the template to run smoothly. Failing to do so could lead to unexpected behavior of the template.

## Database Considerations

This template illustrates the aggregation use case between SalesForce and a database, thus it requires a database instance to work.

### As a Source of Data

The template comes package with a MySQL script to create the database table that it uses.

Use the script to create the table in an available schema and change the configuration accordingly.

The SQL script file can be found in src/main/resources/sfdc2jdbc.sql.

This template is customized for MySQL. To use it with different SQL implementation, make these changes:

- Update the SQL script dialect.
- Replace MySQL driver library dependency to the one in your pom.xml file.
- Update the database connector configuration in src/main/mule/config.xml and in the `mule.*.properties` file.

## Salesforce Considerations

Here's what you need to know about Salesforce to get this template to work:

- Where can I check that the field configuration for my Salesforce instance is the right one? See: [Salesforce: Checking Field Accessibility for a Particular Field](https://help.salesforce.com/HTViewHelpDoc?id=checking_field_accessibility_for_a_particular_field.htm&language=en_US "Salesforce: Checking Field Accessibility for a Particular Field").
- How can I modify field access settings? See: [Salesforce: Modifying Field Access Settings](https://help.salesforce.com/HTViewHelpDoc?id=modifying_field_access_settings.htm&language=en_US "Salesforce: Modifying Field Access Settings").

### As a Destination of Data

There are no particular considerations for this template regarding Salesforce as a data destination.

# Run it!

Simple steps to get this template running.

## Running On Premises

In this section we detail the way you should run your template on your computer.

### Where to Download Anypoint Studio and the Mule Runtime

If you are new to Mule, download this software:

- [Download Anypoint Studio](https://www.mulesoft.com/platform/studio)
- [Download Mule runtime](https://www.mulesoft.com/lp/dl/mule-esb-enterprise)

**Note:** Anypoint Studio requires JDK 8.

### Importing a Template into Studio

In Studio, click the Exchange X icon in the upper left of the taskbar, log in with your Anypoint Platform credentials, search for the template, and click Open.

<!-- Importing into Studio (end) -->

### Running on Studio

After you import your template into Anypoint Studio, follow these steps to run it:

- Locate the properties file `mule.dev.properties`, in src/main/resources.
- Complete all the properties required as per the examples in the "Properties to Configure" section.
- Right click the template project folder.
- Hover your mouse over `Run as`.
- Click `Mule Application (configure)`.
- Inside the dialog, select Environment and set the variable `mule.env` to the value `dev`.
- Click `Run`.

### Running on Mule Standalone

Update the properties in one of the property files, for example in the mule.prod.properties file, and run your app with a corresponding environment variable. In this example, use `mule.env=prod`. 

## Running on CloudHub

When creating your application in CloudHub, go to Runtime Manager > Manage Application > Properties to set the environment variables listed in "Properties to Configure" as well as the mule.env value.

Once your app is all set and started, there is no need to do anything else. Every time a lead is created or modified, the template automatically synchronizes to Salesforce Org B as long as it has an email address.

### Deploying a Template in CloudHub

In Studio, right click your project name in Package Explorer and select Anypoint Platform > Deploy on CloudHub.

## Properties to Configure

To use this template you need to configure properties (credentials, configurations, etc.) either in a properties file or in CloudHub as environment variables. 

**HTTP Connector Configuration**

- http.port `9090`

**Database Connector Configuration**

- db.host `localhost`
- db.port `3306`
- db.user `mule`
- db.password `mule`
- db.databasename `mule`

**SalesForce Connector Configuration**

- sfdc.username `bob.dylan@sfdc`
- sfdc.password `DylanPassword123`
- sfdc.securityToken `avsfwCUl7apQs56Xq2AKi3X`

**SMTP Services Configuration**

- smtp.host `smtp.gmail.com`
- smtp.port `587`
- smtp.user `your%40email.com`
- smtp.password `password`

**Email Details**

- mail.from `your@email.com`
- mail.to `your@email.com`
- mail.subject `Mail subject`
- mail.body `Please find attached your Users Report`
- attachment.name `users_report.csv`

# API Calls

Salesforce imposes limits on the number of API Calls that can be made. However, this template only makes one API call to Salesforce which occurs during aggregation.

# Customize It!

This brief guide provides a high level understanding of how this template is built and how you can change it according to your needs. As Mule applications are based on XML files, this page describes the XML files used with this template. More files are available such as test classes and Mule application files, but to keep it simple, we focus on these XML files:

- config.xml
- businessLogic.xml
- endpoints.xml
- errorHandling.xml

## config.xml

This file provides the configuration for connectors and configuration properties. Only change this file to make core changes to the connector processing logic. Otherwise, all parameters that can be modified should instead be in a properties file, which is the recommended place to make changes.

In the visual editor these values can be found in the _Global Element_ tab.

## businessLogic.xml

Functional aspects of this template are implemented in this XML file, directed by a flow responsible for conducting the aggregation of data, comparing records, and finally formatting the output as a report.

Using the Scatter-Gather component, this template aggregates the data in different systems. After that the aggregation is implemented as a DataWeave 2 script using the Transform component. Aggregated results are sorted by source of existence:

1. Users only in Salesforce A
2. Users only in Salesforce B
3. Users in both Salesforce A and Salesforce B and transformed to CSV format. The final report in CSV format is sent to email that you configure in the `mule.*.properties` file.

## endpoints.xml

This file provides the inbound and outbound sides of your integration app.

This template has an HTTP Listener as the way to trigger the use case.

### Trigger Flow

**HTTP Listener Connector** - Start Report Generation

- `${http.port}` is set as a property to be defined either on a property file or in CloudHub environment variables.
- The path configured by default is `generatereport` and you are free to change this to one you prefer.
- The host name for all endpoints in your CloudHub configuration should be defined as `localhost`. CloudHub then routes requests from your application's domain URL to the endpoint.

## errorHandling.xml

This file handles how your integration reacts depending on the different exceptions.

This file holds a error handling that is referenced by the main flow in the business logic.

To see how error handling works you need to cause an error defined in error handling components, for example setting the wrong credentials or making a mistake querying your database.

