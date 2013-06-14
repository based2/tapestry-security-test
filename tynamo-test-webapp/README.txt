mvn keytool:genkey jetty:run

> https://localhots:8443/tynamo-test-webapp

Accesses in src/resources/shiro-users.properties:

junior1  password1
senior1  password1
admin1  password1

Version of tapestry can be changed to 5.3.7 (used is 5.4-alpha-8)

TOdos_________________________________________________________________________

* enable https://github.com/trsvax/tapestry-bootstrap

* check https://kawwa.atosworldline.com/?

* fix/enable jetty9 ssl with maven depends on ### https://bugs.eclipse.org/bugs/show_bug.cgi?id=409946  ###  > fixed in future jetty9 version

* load realms of Jetty

* use org.tynamo.security:tapestry-security-jpa

* better default ssl config: enabling tests on IE?




