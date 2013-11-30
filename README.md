BankIt
======

A software to plan your expenses.

Add your recurrent incomes and expenses, BankIt allow you to view the state of your account for the next two months.
To avoid to input all your operations, you can directly import the operations file retrieved from your bank website.


Tomcat deployment
-----------------

If you want to deploy the war on Tomcat, you must define a JNDI resource for the database. The name is `jdbc/bankit`

Here is the steps to configure a MySQL database :

1. Create a database on your MySQL server then add a user with all rights on it

2. Copy the [MySQL driver](http://repo1.maven.org/maven2/mysql/mysql-connector-java/5.1.27/mysql-connector-java-5.1.27.jar) to the tomcat `lib/` directory

3. Add the following resource into the Tomcat `conf/context.xml` file, between `<Context>` tags :

```xml
<Resource 
	auth="Container" 
	driverClassName="com.mysql.jdbc.Driver" 
	factory="org.apache.tomcat.jdbc.pool.DataSourceFactory"
	name="jdbc/bankit" 
	username="bankit"
	password="bankitpass" 
	type="javax.sql.DataSource" 
	url="jdbc:mysql://localhost:3306/bankit"
	testOnBorrow="true"
	validationQuery="SELECT 1" 
/>
```

4. Deploy the war as usual (put into webapps folder)