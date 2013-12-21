mvn clean tomcat7:run
mvn clean tomcat6:run
mvn clean jetty:run

> https://localhost:8443/

Accesses in src/resources/shiro-users.properties:

admin1  111
editor1 222
seller1 333
customer1 444

Tapestry version used: 5.4-beta-1

===============================================================================
status: + secure flag for cookies in web.xml, * maven-tomcat7-plugin 2.2 (7.0.47)

http://jira.codehaus.org/browse/TYNAMO-147 - Make tapestry-security compatible with tapestry-5.4

- [] update: font awesome and icon config in AppModule
- [] move files to the root of the project
- [] loginLayout -> LoginLayout
- [] fix with http://mail-archives.apache.org/mod_mbox/tapestry-users/201309.mbox/browser
- [] add Shiro UI management like in ISIS
- [] use org.tynamo.security:tapestry-security-jpa with H2 DB
- [] audit view page
- [] http://wiki.apache.org/tapestry/Tapestry5CSRF
- [] better default ssl config: enabling tests on IE?
- [] check https://kawwa.atosworldline.com/?


http://erik.io/blog/2013/06/08/a-basic-guide-to-when-and-how-to-deploy-https/
  https://www.owasp.org/index.php/SecureFlag