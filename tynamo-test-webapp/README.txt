Another Tapestry Template for a Secured web application access
with Apache Tapestry 5.4-beta-33 and Tynamo/tapestry-security 0.6.2 (Shiro 1.2.4)

Install Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files 8
 http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html
 in your jre/lib/security (it will replace 2 jars)

mvn clean tomcat7:run
mvn clean tomcat6:run
mvn clean jetty:run

> https://localhost:8443/index

Accesses in src/resources/shiro-users.properties:

admin1  111
editor1 222
seller1 333
customer1 444

To get live class reloading work for the common module in IntelliJ:
Open Project Structure/Modules/Paths/Use module compile output path/Output path (target/classes)
Copy the path to Project Structure/Project/Project compiler output path
===================================================================================
Status: Update to Tapestry Shiro 1.2.5 and jetty

add theme switcher for bootswatch
add google, ms bootstrap theme
- [] use org.tynamo.security:tapestry-security-jpa with H2 DB
add JPA to H2 with Thiago CRUD
- a better grid: with search component/filter -> jquery
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

http://www.cert.ssi.gouv.fr/site/CERTFR-2015-AVI-317/index.html bluecoat

=============================================================================================================

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

https://issues.apache.org/jira/browse/SHIRO-582
ava.lang.NullPointerException
	at org.apache.shiro.util.AntPathMatcher.doMatch(AntPathMatcher.java:109)
	at org.apache.shiro.util.AntPathMatcher.match(AntPathMatcher.java:90)
	at org.apache.shiro.util.AntPathMatcher.matches(AntPathMatcher.java:86)
	at org.tynamo.security.services.impl.SecurityFilterChain.matches(SecurityFilterChain.java:46)
	at org.tynamo.security.services.impl.SecurityConfiguration.getMatchingChain(SecurityConfiguration.java:84)
	at org.tynamo.security.services.impl.SecurityConfiguration.service(SecurityConfiguration.java:52)
	at $HttpServletRequestFilter_19ddf7bc0531.service(Unknown Source)
	at $HttpServletRequestHandler_19ddf7bc0534.service(Unknown Source)
	at org.apache.tapestry5.modules.TapestryModule$1.service(TapestryModule.java:796)
	at $HttpServletRequestHandler_19ddf7bc0534.service(Unknown Source)
	at $HttpServletRequestHandler_19ddf7bc052d.service(Unknown Source)
	at org.apache.tapestry5.TapestryFilter.doFilter(TapestryFilter.java:166)
	at org.eclipse.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1652)
	at org.eclipse.jetty.servlet.ServletHandler.doHandle(ServletHandler.java:585)
	at org.eclipse.jetty.server.handler.ScopedHandler.handle(ScopedHandler.java:143)
	at org.eclipse.jetty.security.SecurityHandler.handle(SecurityHandler.java:577)
	at org.eclipse.jetty.server.session.SessionHandler.doHandle(SessionHandler.java:223)
	at org.eclipse.jetty.server.handler.ContextHandler.doHandle(ContextHandler.java:1127)
	at org.eclipse.jetty.servlet.ServletHandler.doScope(ServletHandler.java:515)
	at org.eclipse.jetty.server.session.SessionHandler.doScope(SessionHandler.java:185)
	at org.eclipse.jetty.server.handler.ContextHandler.doScope(ContextHandler.java:1061)
	at org.eclipse.jetty.server.handler.ScopedHandler.handle(ScopedHandler.java:141)
	at org.eclipse.jetty.server.handler.ContextHandlerCollection.handle(ContextHandlerCollection.java:215)
	at org.eclipse.jetty.server.handler.HandlerCollection.handle(HandlerCollection.java:110)
	at org.eclipse.jetty.server.handler.HandlerWrapper.handle(HandlerWrapper.java:97)
	at org.eclipse.jetty.server.Server.handle(Server.java:499)
	at org.eclipse.jetty.server.HttpChannel.handle(HttpChannel.java:311)
	at org.eclipse.jetty.server.HttpConnection.onFillable(HttpConnection.java:258)
	at org.eclipse.jetty.io.AbstractConnection$2.run(AbstractConnection.java:544)
	at org.eclipse.jetty.util.thread.QueuedThreadPool.runJob(QueuedThreadPool.java:635)
	at org.eclipse.jetty.util.thread.QueuedThreadPool$3.run(QueuedThreadPool.java:555)
	at java.lang.Thread.run(Thread.java:745)