mvn clean tomcat7:run
mvn clean tomcat6:run
mvn clean jetty:run

> https://localhost:8443/

Accesses in src/resources/shiro-users.properties:

admin1  111
editor1 222
seller1 333
customer1 444

Tapestry version used: 5.4-alpha-29

===================================================
status: (notrunning) update to 5.4-alpha-29, + ldap user-role filter, fix navbar and add icons rendering (ongoing)

- [] update: layout template, bootswatch.css, font awesome and icon config in AppModule and fix it
- [] move files to the root of the project
- [] loginLayout -> LoginLayout
- [] fix with http://mail-archives.apache.org/mod_mbox/tapestry-users/201309.mbox/browser
- [] add Shiro UI management like in ISIS
- [] use org.tynamo.security:tapestry-security-jpa with H2 DB
- [] audit view page
- [] http://wiki.apache.org/tapestry/Tapestry5CSRF
- [] better default ssl config: enabling tests on IE?
- [] check https://kawwa.atosworldline.com/?