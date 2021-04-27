# Another Tapestry Template for a Secured web application access
with Apache Tapestry 5.6.4 and Tynamo/tapestry-security 0.8.0

Install Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files 8
 http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html
 in your jre/lib/security (it will replace 2 jars)

mvn clean jetty:run
mvn clean tomcat7:run
https://stackoverflow.com/questions/26883836/tomcat-8-maven-plugin-for-java-8
https://stackoverflow.com/questions/41326911/maven-plugin-for-tomcat-9

> https://localhost:8445/index

Accesses in src/resources/shiro-users.properties:

admin1  111
editor1 222
seller1 333
customer1 444

To get live class reloading work for the common module in IntelliJ:
Open Project Structure/Modules/Paths/Use module compile output path/Output path (target/classes)
Copy the path to Project Structure/Project/Project compiler output path

add theme switcher for bootswatch
add google, ms bootstrap theme
- [] use org.tynamo.security:tapestry-security-jpa with H2 DB
add JPA to H2 with Thiago CRUD
- a better grid: with search comphttps://github.com/based2/tapestry-security-testonent/filter -> jquery
- try with https://github.com/spreadthesource/tapestry5-installer
- [] move files to the root of the project
- [] audit view page
- [] http://wiki.apache.org/tapestry/Tapestry5CSRF
   https://github.com/porscheinformatik/tapestry-csrf-protection
- add privacy page a la http://www.joda.org/joda-time/privacy.html
- add cookie warning page a la oracle EU law - http://europa.eu/cookies/index_en.htm
- add robots.txt
- enable css template user change: http://stackoverflow.com/questions/2864070/tapestry-5-loading-css-from-filesystem
-- [] add Shiro UI management like in ISIS
- [] check https://kawwa.atosworldline.com/

https://github.com/brianreavis/selectize.js select,js,bootstrap3

CSP on going discussions:
http://apache-tapestry-mailing-list-archives.1045711.n5.nabble.com/Content-Security-Policy-without-unsafe-inline-td5725690.html
> inline will be allowed in CSP 1.1
http://www.html5rocks.com/en/tutorials/security/content-security-policy/
https://www.owasp.org/index.php/Content_Security_Policy
http://w3c.github.io/webappsec/specs/content-security-policy/csp-specification.dev.html
https://github.com/mathjax/MathJax/issues/256
http://mathiasbynens.be/notes/csp-reports
https://issues.apache.org/jira/browse/WICKET-5406
https://github.com/dschadow/Java-Web-Security/tree/master
http://people.mozilla.org/~bsterne/content-security-policy/details.html
https://github.com/jrburke/requirejs/issues/537
https://bugzilla.mozilla.org/show_bug.cgi?id=959270

http://erik.io/blog/2013/06/08/a-basic-guide-to-when-and-how-to-deploy-https/
  https://www.owasp.org/index.php/SecureFlag
http://security.stackexchange.com/questions/20803/how-does-ssl-work
=============================================================================================================
apache Shiro 1.7.0 fix CVE-2020-17510 (2020-10-29)

ComponentWorld

https://github.com/tapestry-modules/list
https://github.com/tapestry-modules/tapestry-modules

https://github.com/oakstair/tapinapp

http://sody.github.io/greatage/
https://github.com/sody/greatage
https://github.com/sody/dynamit

http://tawus.wordpress.com/2011/04/23/tapestry-magic-5-advising-services/
https://github.com/markshead/wooki

https://github.com/tawus/tawus/blob/master/tawus-core/src/main/java/com/googlecode/tawus/EntityValueEncoder.java
http://tawus.wordpress.com/2011/05/28/tapestry-magic-13-generic-data-access-objects/

http://killertilapia.blogspot.be/2012/08/autobind-all-tapestry5-services.html

https://github.com/brekka/commons-tapestry
https://github.com/gerardocdc/tapestry-fullcalendar
https://github.com/trsvax/tapestry-doc
https://github.com/trsvax/tapestry-librarybinder
https://github.com/trsvax/tapestry-datepicker

https://github.com/derkoe/tapestry-jaxws

https://github.com/thiagohp/tapestry-crud
http://www.arsmachina.com.br/project/tapestrycrud/usage

https://github.com/thiagohp/jsr107tck cache
https://github.com/thiagohp/RI

https://github.com/thiagohp/tapestry5-high

https://github.com/thiagohp/generic-controller typo

https://github.com/trsvax/tapestry-shop

https://github.com/porscheinformatik/tapestry-component-extension

https://github.com/ciaranw/tapestry-service-cache

https://github.com/adaptivui/tapestry-genetify-demo

https://github.com/nmeylan/Tapestry-secondary-assets-pipeline

https://github.com/intercommit/Weaves JPAPagedGridDataSource

https://github.com/trsvax/Jacquard/tree/master/src/main/java/com/trsvax/jacquard/services

https://github.com/hlship/plastic-demos

https://github.com/jmayaalv/Tapestrify

intellij IDEA Emmet templates

https://github.com/satago/tapestry-jpa-transactions

https://github.com/joostschouten/newrelic4tapestry5

https://github.com/anjlab/eclipse-tapestry5-plugin#install

https://github.com/uklance/tapestry-atmosphere
https://github.com/uklance/tapestry-cometd

https://github.com/sveine/tapestry-multi-module-demo/

http://jumpstart.doublenegative.com.au/jumpstart/examples/infrastructure/protectingpages

https://github.com/gagauz/tapestry-css-combiner
https://github.com/gagauz/tapestry-security
https://github.com/gagauz/tapestry-common

https://github.com/xfyre/tapestry5-xtensions bootstrap extensions

https://github.com/dragansah/filebrowser

https://github.com/got5

https://github.com/dragansah/t5-contextmenu

https://github.com/dirent/tapestry-piday Searching for digits in Pi, inspired from The Coding Traing
                                         Implemented with Spring-Boot and Tapestry
    
ParsleyJS js form validator
https://livr-spec.org/introduction/principles.html

https://cycle.js.org/

https://gitlab.nuiton.org/chorem/timebundle
https://www.codelutin.com/contributions.html
