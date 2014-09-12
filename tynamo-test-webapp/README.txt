Another Tapestry Template
Secured web application access
with Apache Tapestry 5.4-beta-22 and Tynamo/tapestry-security 0.6.0 (Shiro 1.2.3)

mvn clean tomcat7:run
mvn clean tomcat6:run
mvn clean jetty:run      Before you must change java.version to 1.7 in the pom.xml file

> https://localhost:8443/

Accesses in src/resources/shiro-users.properties:

admin1  111
editor1 222
seller1 333
customer1 444

===================================================================================
Status: update to 5.4-beta-22, font-awesome 4.1.0, IE 9 compatibility, start i18n:fr

update maven to 3.2.3
add font-awesome 4.2.0
add theme switcher for bootswatch
add google bootstrap theme
add ms bootstrap theme
fix navbar bootstrap 'activate'
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

=============================================================================================================

ComponentWorld

https://github.com/tapestry-modules/list
https://github.com/tapestry-modules/tapestry-modules

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


java.lang.NullPointerException
	org.apache.shiro.util.AntPathMatcher.doMatch(AntPathMatcher.java:109)
	org.apache.shiro.util.AntPathMatcher.match(AntPathMatcher.java:90)
	org.apache.shiro.util.AntPathMatcher.matches(AntPathMatcher.java:86)
	org.tynamo.security.services.impl.SecurityFilterChain.matches(SecurityFilterChain.java:46)
	org.tynamo.security.services.impl.SecurityConfiguration.getMatchingChain(SecurityConfiguration.java:76)
	org.tynamo.security.services.impl.SecurityConfiguration.service(SecurityConfiguration.java:48)
	$HttpServletRequestFilter_13731a5f2fa06bda.service(Unknown Source)
	$HttpServletRequestHandler_13731a5f2fa06be0.service(Unknown Source)
	org.apache.tapestry5.modules.TapestryModule$1.service(TapestryModule.java:795)
	$HttpServletRequestHandler_13731a5f2fa06be0.service(Unknown Source)
	$HttpServletRequestHandler_13731a5f2fa06bd9.service(Unknown Source)
	org.apache.tapestry5.TapestryFilter.doFilter(TapestryFilter.java:166)
