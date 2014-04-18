Another Tapestry Template
Secured web application access with Apache Tapestry 5.4-beta-3 and Apache Shiro 1.2.3 (Tynamo: tapestry-security 0.6.0)

mvn clean tomcat7:run
mvn clean tomcat6:run
mvn clean jetty:run

> https://localhost:8443/

Accesses in src/resources/shiro-users.properties:

admin1  111
editor1 222
seller1 333
customer1 444

Tapestry version used: 5.4-beta-4
=================================================================================
Status: Test java jca jsse jce unlimited policy files presence,
        update to 5.4-beta-4,
        update to tynamo-security 0.6.0 and shiro 1.2.3,
        enable navbar component for persistence and better reuse,
        Board> SuiteProviders fix exception in mapgrid

- try with https://github.com/spreadthesource/tapestry5-installer
- [] move files to the root of the project
- [] audit view page
- [] http://wiki.apache.org/tapestry/Tapestry5CSRF
   https://github.com/porscheinformatik/tapestry-csrf-protection
- add privacy page a la http://www.joda.org/joda-time/privacy.html
- add cookie warning page a la oracle EU law - http://europa.eu/cookies/index_en.htm
- add robots.txt
- enable css template user change: http://stackoverflow.com/questions/2864070/tapestry-5-loading-css-from-filesystem
- [] use org.tynamo.security:tapestry-security-jpa with H2 DB
-- [] add Shiro UI management like in ISIS
- [] check https://kawwa.atosworldline.com/

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

- The CDN gets to decide *what* code is delivered to *which* users. Could be a prime target for, say, another FERRETCANNON.
- If the CDN is compromised, so is your site.
- If an attacker on a local network manages to inject poisoned cache data into requests for said CDN, your site is compromised.
- All of your visitors are disclosed to the CDN owner.
- If the CDN goes down, your site does so, too. Note that the inverse doesn't apply: the CDNs superior availability has no positive effect on your site.
- Loading from another host may cause an unnecessary DNS lookup and will cause an unnecessary TLS connection.
(by phlo)


http://erik.io/blog/2013/06/08/a-basic-guide-to-when-and-how-to-deploy-https/
  https://www.owasp.org/index.php/SecureFlag
http://security.stackexchange.com/questions/20803/how-does-ssl-work