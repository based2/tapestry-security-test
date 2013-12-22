Another Tapestry Template
Secured web application access with Apache Tapestry 5.4-beta-1 and Apache Shiro 1.2.2 (Tynamo: tapestry-security 0.5.1)

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

=================================================================================
Status: + fa-icon, loginLayout -> LoginLayout, fix Error at line 30 column 1: XML document structures must start and end within the same entity.

- [] update: font awesome and icon config in AppModule
- center navbar and logout
- [] move files to the root of the project
- [] use org.tynamo.security:tapestry-security-jpa with H2 DB
-- [] add Shiro UI management like in ISIS
- [] use tynamo-security 0.06, when released: http://jira.codehaus.org/browse/TYNAMO-147
- [] audit view page
- [] http://wiki.apache.org/tapestry/Tapestry5CSRF
- [] better default ssl config: enabling tests on IE?
- [] check https://kawwa.atosworldline.com/

http://erik.io/blog/2013/06/08/a-basic-guide-to-when-and-how-to-deploy-https/
  https://www.owasp.org/index.php/SecureFlag