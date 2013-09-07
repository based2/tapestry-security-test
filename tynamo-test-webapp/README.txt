mvn clean jetty:run

> https://localhost:8443/

Accesses in src/resources/shiro-users.properties:

admin1  111
editor1 222
seller1 333
customer1 444

Version of tapestry can be changed to 5.4-alpha-18

===================================================

Current errors while trying to update the FormGroup Mixin to support horizontal-form with two columns:

<t:textfield id="username2" forlabel="username2" labelsize="col-lg-6" colorborder="danger" inputsize="col-lg-2"
             mixins="formgroup"  placeholder="Login2" />

 org.apache.tapestry5.ioc.internal.OperationException: Exception assembling root component of page Index:
 Could not convert 'col-lg-3' into a component parameter binding: Error parsing property expression 'col-lg-3': Unable to parse input at character position 4.
	at org.apache.tapestry5.ioc.internal.OperationTrackerImpl.logAndRethrow(OperationTrackerImpl.java:180) ~[tapestry-ioc-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.ioc.internal.OperationTrackerImpl.invoke(OperationTrackerImpl.java:88) ~[tapestry-ioc-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.ioc.internal.PerThreadOperationTracker.invoke(PerThreadOperationTracker.java:89) ~[tapestry-ioc-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.ioc.internal.RegistryImpl.invoke(RegistryImpl.java:1112) ~[tapestry-ioc-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.pageload.ComponentAssemblerImpl.assembleRootComponent(ComponentAssemblerImpl.java:88) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.pageload.PageLoaderImpl$3.invoke(PageLoaderImpl.java:203) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.pageload.PageLoaderImpl$3.invoke(PageLoaderImpl.java:196) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.ioc.internal.OperationTrackerImpl.invoke(OperationTrackerImpl.java:80) ~[tapestry-ioc-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.ioc.internal.PerThreadOperationTracker.invoke(PerThreadOperationTracker.java:89) ~[tapestry-ioc-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.ioc.internal.RegistryImpl.invoke(RegistryImpl.java:1112) ~[tapestry-ioc-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.pageload.PageLoaderImpl.loadPage(PageLoaderImpl.java:195) ~[tapestry-core-5.4-alpha-18.jar:na]
	at $PageLoader_132184f1ef57c606.loadPage(Unknown Source) ~[na:na]
	at org.apache.tapestry5.internal.services.PageSourceImpl.getPage(PageSourceImpl.java:104) ~[tapestry-core-5.4-alpha-18.jar:na]
	at $PageSource_132184f1ef57c605.getPage(Unknown Source) ~[na:na]
	at org.apache.tapestry5.internal.services.RequestPageCacheImpl.get(RequestPageCacheImpl.java:81) ~[tapestry-core-5.4-alpha-18.jar:na]
	at $RequestPageCache_132184f1ef57c604.get(Unknown Source) ~[na:na]
	at $RequestPageCache_132184f1ef57c5ff.get(Unknown Source) ~[na:na]
	at org.apache.tapestry5.internal.services.PageRenderRequestHandlerImpl.handle(PageRenderRequestHandlerImpl.java:56) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.modules.TapestryModule$33.handle(TapestryModule.java:1950) ~[tapestry-core-5.4-alpha-18.jar:na]
	at $PageRenderRequestHandler_132184f1ef57c603.handle(Unknown Source) ~[na:na]
	at $PageRenderRequestHandler_132184f1ef57c5fd.handle(Unknown Source) ~[na:na]
	at org.apache.tapestry5.internal.services.ComponentRequestHandlerTerminator.handlePageRender(ComponentRequestHandlerTerminator.java:48) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.services.InitializeActivePageName.handlePageRender(InitializeActivePageName.java:47) ~[tapestry-core-5.4-alpha-18.jar:na]
	at $ComponentRequestHandler_132184f1ef57c5fe.handlePageRender(Unknown Source) ~[na:na]
	at org.apache.tapestry5.internal.services.RequestOperationTracker$2.run(RequestOperationTracker.java:73) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.ioc.internal.OperationTrackerImpl.run(OperationTrackerImpl.java:55) ~[tapestry-ioc-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.ioc.internal.PerThreadOperationTracker.run(PerThreadOperationTracker.java:78) ~[tapestry-ioc-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.ioc.internal.RegistryImpl.run(RegistryImpl.java:1107) ~[tapestry-ioc-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.services.RequestOperationTracker.handlePageRender(RequestOperationTracker.java:66) ~[tapestry-core-5.4-alpha-18.jar:na]
	at $ComponentRequestHandler_132184f1ef57c5fe.handlePageRender(Unknown Source) ~[na:na]
	at org.tynamo.security.SecurityComponentRequestFilter.handlePageRender(SecurityComponentRequestFilter.java:49) ~[tapestry-security-0.5.1.jar:0.5.1]
	at $ComponentRequestFilter_132184f1ef57c5fb.handlePageRender(Unknown Source) ~[na:na]
	at $ComponentRequestHandler_132184f1ef57c5fe.handlePageRender(Unknown Source) ~[na:na]
	at $ComponentRequestHandler_132184f1ef57c5c0.handlePageRender(Unknown Source) ~[na:na]
	at org.apache.tapestry5.internal.services.PageRenderDispatcher.dispatch(PageRenderDispatcher.java:45) ~[tapestry-core-5.4-alpha-18.jar:na]
	at $Dispatcher_132184f1ef57c5c2.dispatch(Unknown Source) ~[na:na]
	at $Dispatcher_132184f1ef57c5bd.dispatch(Unknown Source) ~[na:na]
	at org.apache.tapestry5.modules.TapestryModule$RequestHandlerTerminator.service(TapestryModule.java:298) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.services.RequestErrorFilter.service(RequestErrorFilter.java:26) ~[tapestry-core-5.4-alpha-18.jar:na]
	at $RequestHandler_132184f1ef57c5be.service(Unknown Source) [na:na]
	at org.apache.tapestry5.modules.TapestryModule$3.service(TapestryModule.java:843) [tapestry-core-5.4-alpha-18.jar:na]
	at $RequestHandler_132184f1ef57c5be.service(Unknown Source) [na:na]
	at org.apache.tapestry5.modules.TapestryModule$2.service(TapestryModule.java:833) [tapestry-core-5.4-alpha-18.jar:na]
	at $RequestHandler_132184f1ef57c5be.service(Unknown Source) [na:na]
	at org.apache.tapestry5.internal.services.StaticFilesFilter.service(StaticFilesFilter.java:89) [tapestry-core-5.4-alpha-18.jar:na]
	at $RequestHandler_132184f1ef57c5be.service(Unknown Source) [na:na]
	at org.apache.tapestry5.internal.services.CheckForUpdatesFilter$2.invoke(CheckForUpdatesFilter.java:105) [tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.services.CheckForUpdatesFilter$2.invoke(CheckForUpdatesFilter.java:95) [tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.ioc.internal.util.ConcurrentBarrier.withRead(ConcurrentBarrier.java:85) [tapestry-ioc-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.services.CheckForUpdatesFilter.service(CheckForUpdatesFilter.java:119) [tapestry-core-5.4-alpha-18.jar:na]
	at $RequestHandler_132184f1ef57c5be.service(Unknown Source) [na:na]
	at $RequestHandler_132184f1ef57c59c.service(Unknown Source) [na:na]
	at org.apache.tapestry5.modules.TapestryModule$HttpServletRequestHandlerTerminator.service(TapestryModule.java:249) [tapestry-core-5.4-alpha-18.jar:na]
	at org.tynamo.security.services.impl.SecurityConfiguration$1.call(SecurityConfiguration.java:56) [tapestry-security-0.5.1.jar:0.5.1]
	at org.tynamo.security.services.impl.SecurityConfiguration$1.call(SecurityConfiguration.java:54) [tapestry-security-0.5.1.jar:0.5.1]
	at org.apache.shiro.subject.support.SubjectCallable.doCall(SubjectCallable.java:90) [shiro-core-1.2.2.jar:1.2.2]
	at org.apache.shiro.subject.support.SubjectCallable.call(SubjectCallable.java:83) [shiro-core-1.2.2.jar:1.2.2]
	at org.apache.shiro.subject.support.DelegatingSubject.execute(DelegatingSubject.java:383) [shiro-core-1.2.2.jar:1.2.2]
	at org.tynamo.security.services.impl.SecurityConfiguration.service(SecurityConfiguration.java:54) [tapestry-security-0.5.1.jar:0.5.1]
	at $HttpServletRequestFilter_132184f1ef57c59b.service(Unknown Source) [na:na]
	at $HttpServletRequestHandler_132184f1ef57c59e.service(Unknown Source) [na:na]
	at org.apache.tapestry5.internal.gzip.GZipFilter.service(GZipFilter.java:59) [tapestry-core-5.4-alpha-18.jar:na]
	at $HttpServletRequestHandler_132184f1ef57c59e.service(Unknown Source) [na:na]
	at org.apache.tapestry5.internal.services.IgnoredPathsFilter.service(IgnoredPathsFilter.java:62) [tapestry-core-5.4-alpha-18.jar:na]
	at $HttpServletRequestFilter_132184f1ef57c598.service(Unknown Source) [na:na]
	at $HttpServletRequestHandler_132184f1ef57c59e.service(Unknown Source) [na:na]
	at org.apache.tapestry5.modules.TapestryModule$1.service(TapestryModule.java:793) [tapestry-core-5.4-alpha-18.jar:na]
	at $HttpServletRequestHandler_132184f1ef57c59e.service(Unknown Source) [na:na]
	at $HttpServletRequestHandler_132184f1ef57c597.service(Unknown Source) [na:na]
	at org.apache.tapestry5.TapestryFilter.doFilter(TapestryFilter.java:166) [tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:243) [tomcat-embed-core-7.0.37.jar:7.0.37]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:210) [tomcat-embed-core-7.0.37.jar:7.0.37]
	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:222) [tomcat-embed-core-7.0.37.jar:7.0.37]
	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:123) [tomcat-embed-core-7.0.37.jar:7.0.37]
	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:472) [tomcat-embed-core-7.0.37.jar:7.0.37]
	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:171) [tomcat-embed-core-7.0.37.jar:7.0.37]
	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:99) [tomcat-embed-core-7.0.37.jar:7.0.37]
	at org.apache.catalina.valves.AccessLogValve.invoke(AccessLogValve.java:936) [tomcat-embed-core-7.0.37.jar:7.0.37]
	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:118) [tomcat-embed-core-7.0.37.jar:7.0.37]
	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:407) [tomcat-embed-core-7.0.37.jar:7.0.37]
	at org.apache.coyote.http11.AbstractHttp11Processor.process(AbstractHttp11Processor.java:1004) [tomcat-embed-core-7.0.37.jar:7.0.37]
	at org.apache.coyote.AbstractProtocol$AbstractConnectionHandler.process(AbstractProtocol.java:589) [tomcat-embed-core-7.0.37.jar:7.0.37]
	at org.apache.tomcat.util.net.JIoEndpoint$SocketProcessor.run(JIoEndpoint.java:310) [tomcat-embed-core-7.0.37.jar:7.0.37]
	at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:895) [na:1.6.0_51]
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:918) [na:1.6.0_51]
	at java.lang.Thread.run(Thread.java:680) [na:1.6.0_51]
Caused by: java.lang.RuntimeException: Exception assembling root component of page Index: Could not convert 'col-lg-3' into a component parameter binding: Error parsing property expression 'col-lg-3': Unable to parse input at character position 4.
	at org.apache.tapestry5.internal.pageload.ComponentAssemblerImpl.performAssembleRootComponent(ComponentAssemblerImpl.java:142) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.pageload.ComponentAssemblerImpl.access$000(ComponentAssemblerImpl.java:42) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.pageload.ComponentAssemblerImpl$1.invoke(ComponentAssemblerImpl.java:93) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.pageload.ComponentAssemblerImpl$1.invoke(ComponentAssemblerImpl.java:90) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.ioc.internal.OperationTrackerImpl.invoke(OperationTrackerImpl.java:80) ~[tapestry-ioc-5.4-alpha-18.jar:na]
	... 84 common frames omitted
Caused by: org.apache.tapestry5.ioc.internal.util.TapestryException: Could not convert 'col-lg-3' into a component parameter binding: Error parsing property expression 'col-lg-3': Unable to parse input at character position 4.
	at org.apache.tapestry5.internal.services.BindingSourceImpl.newBinding(BindingSourceImpl.java:84) ~[tapestry-core-5.4-alpha-18.jar:na]
	at $BindingSource_132184f1ef57c5e2.newBinding(Unknown Source) ~[na:na]
	at org.apache.tapestry5.internal.services.PageElementFactoryImpl.newBinding(PageElementFactoryImpl.java:180) ~[tapestry-core-5.4-alpha-18.jar:na]
	at $PageElementFactory_132184f1ef57c61c.newBinding(Unknown Source) ~[na:na]
	at org.apache.tapestry5.internal.pageload.PageLoaderImpl$10.execute(PageLoaderImpl.java:889) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.pageload.ComponentAssemblerImpl.runActions(ComponentAssemblerImpl.java:242) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.pageload.ComponentAssemblerImpl.performAssembleRootComponent(ComponentAssemblerImpl.java:118) ~[tapestry-core-5.4-alpha-18.jar:na]
	... 88 common frames omitted
Caused by: java.lang.RuntimeException: Error parsing property expression 'col-lg-3': Unable to parse input at character position 4.
	at org.apache.tapestry5.internal.services.PropertyConduitSourceImpl.parse(PropertyConduitSourceImpl.java:1481) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.services.PropertyConduitSourceImpl.build(PropertyConduitSourceImpl.java:1352) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.services.PropertyConduitSourceImpl.create(PropertyConduitSourceImpl.java:1329) ~[tapestry-core-5.4-alpha-18.jar:na]
	at $PropertyConduitSource_132184f1ef57c63c.create(Unknown Source) ~[na:na]
	at org.apache.tapestry5.internal.bindings.PropBindingFactory.newBinding(PropBindingFactory.java:49) ~[tapestry-core-5.4-alpha-18.jar:na]
	at $BindingFactory_132184f1ef57c63d.newBinding(Unknown Source) ~[na:na]
	at $BindingFactory_132184f1ef57c633.newBinding(Unknown Source) ~[na:na]
	at org.apache.tapestry5.internal.services.BindingSourceImpl.newBinding(BindingSourceImpl.java:81) ~[tapestry-core-5.4-alpha-18.jar:na]
	... 94 common frames omitted
Caused by: java.lang.RuntimeException: Unable to parse input at character position 4
	at org.apache.tapestry5.internal.antlr.BaseLexer.reportError(BaseLexer.java:50) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.antlr.runtime.Lexer.nextToken(Lexer.java:99) ~[antlr-runtime-3.3.jar:na]
	at org.antlr.runtime.BufferedTokenStream.fetch(BufferedTokenStream.java:133) ~[antlr-runtime-3.3.jar:na]
	at org.antlr.runtime.BufferedTokenStream.sync(BufferedTokenStream.java:127) ~[antlr-runtime-3.3.jar:na]
	at org.antlr.runtime.CommonTokenStream.consume(CommonTokenStream.java:67) ~[antlr-runtime-3.3.jar:na]
	at org.antlr.runtime.DFA.predict(DFA.java:120) ~[antlr-runtime-3.3.jar:na]
	at org.apache.tapestry5.internal.antlr.PropertyExpressionParser.expression(PropertyExpressionParser.java:176) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.antlr.PropertyExpressionParser.start(PropertyExpressionParser.java:115) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.services.PropertyConduitSourceImpl.parse(PropertyConduitSourceImpl.java:1478) ~[tapestry-core-5.4-alpha-18.jar:na]
	... 101 common frames omitted
Caused by: org.antlr.runtime.NoViableAltException: null
	at org.apache.tapestry5.internal.antlr.PropertyExpressionLexer.mNUMBER_OR_RANGEOP(PropertyExpressionLexer.java:989) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.antlr.PropertyExpressionLexer.mTokens(PropertyExpressionLexer.java:1397) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.antlr.runtime.Lexer.nextToken(Lexer.java:89) ~[antlr-runtime-3.3.jar:na]
	... 108 common frames omitted

<t:textfield id="username2" forlabel="username2" labelsize="collg6" colorborder="danger" inputsize="col-lg-2"
             mixins="formgroup"  placeholder="Login2" />

org.apache.tapestry5.ioc.internal.OperationException: Exception assembling root component of page Index:
Could not convert 'collg3' into a component parameter binding: Exception generating conduit for expression 'collg3': Class net.company.pages.Index does not contain a property (or public field) named 'collg3'.
	at org.apache.tapestry5.ioc.internal.OperationTrackerImpl.logAndRethrow(OperationTrackerImpl.java:180) ~[tapestry-ioc-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.ioc.internal.OperationTrackerImpl.invoke(OperationTrackerImpl.java:88) ~[tapestry-ioc-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.ioc.internal.PerThreadOperationTracker.invoke(PerThreadOperationTracker.java:89) ~[tapestry-ioc-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.ioc.internal.RegistryImpl.invoke(RegistryImpl.java:1112) ~[tapestry-ioc-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.pageload.ComponentAssemblerImpl.assembleRootComponent(ComponentAssemblerImpl.java:88) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.pageload.PageLoaderImpl$3.invoke(PageLoaderImpl.java:203) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.pageload.PageLoaderImpl$3.invoke(PageLoaderImpl.java:196) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.ioc.internal.OperationTrackerImpl.invoke(OperationTrackerImpl.java:80) ~[tapestry-ioc-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.ioc.internal.PerThreadOperationTracker.invoke(PerThreadOperationTracker.java:89) ~[tapestry-ioc-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.ioc.internal.RegistryImpl.invoke(RegistryImpl.java:1112) ~[tapestry-ioc-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.pageload.PageLoaderImpl.loadPage(PageLoaderImpl.java:195) ~[tapestry-core-5.4-alpha-18.jar:na]
	at $PageLoader_1321854fbcd92bc6.loadPage(Unknown Source) ~[na:na]
	at org.apache.tapestry5.internal.services.PageSourceImpl.getPage(PageSourceImpl.java:104) ~[tapestry-core-5.4-alpha-18.jar:na]
	at $PageSource_1321854fbcd92bc5.getPage(Unknown Source) ~[na:na]
	at org.apache.tapestry5.internal.services.RequestPageCacheImpl.get(RequestPageCacheImpl.java:81) ~[tapestry-core-5.4-alpha-18.jar:na]
	at $RequestPageCache_1321854fbcd92bc4.get(Unknown Source) ~[na:na]
	at $RequestPageCache_1321854fbcd92bbf.get(Unknown Source) ~[na:na]
	at org.apache.tapestry5.internal.services.PageRenderRequestHandlerImpl.handle(PageRenderRequestHandlerImpl.java:56) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.modules.TapestryModule$33.handle(TapestryModule.java:1950) ~[tapestry-core-5.4-alpha-18.jar:na]
	at $PageRenderRequestHandler_1321854fbcd92bc3.handle(Unknown Source) ~[na:na]
	at $PageRenderRequestHandler_1321854fbcd92bbd.handle(Unknown Source) ~[na:na]
	at org.apache.tapestry5.internal.services.ComponentRequestHandlerTerminator.handlePageRender(ComponentRequestHandlerTerminator.java:48) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.services.InitializeActivePageName.handlePageRender(InitializeActivePageName.java:47) ~[tapestry-core-5.4-alpha-18.jar:na]
	at $ComponentRequestHandler_1321854fbcd92bbe.handlePageRender(Unknown Source) ~[na:na]
	at org.apache.tapestry5.internal.services.RequestOperationTracker$2.run(RequestOperationTracker.java:73) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.ioc.internal.OperationTrackerImpl.run(OperationTrackerImpl.java:55) ~[tapestry-ioc-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.ioc.internal.PerThreadOperationTracker.run(PerThreadOperationTracker.java:78) ~[tapestry-ioc-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.ioc.internal.RegistryImpl.run(RegistryImpl.java:1107) ~[tapestry-ioc-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.services.RequestOperationTracker.handlePageRender(RequestOperationTracker.java:66) ~[tapestry-core-5.4-alpha-18.jar:na]
	at $ComponentRequestHandler_1321854fbcd92bbe.handlePageRender(Unknown Source) ~[na:na]
	at org.tynamo.security.SecurityComponentRequestFilter.handlePageRender(SecurityComponentRequestFilter.java:49) ~[tapestry-security-0.5.1.jar:0.5.1]
	at $ComponentRequestFilter_1321854fbcd92bbb.handlePageRender(Unknown Source) ~[na:na]
	at $ComponentRequestHandler_1321854fbcd92bbe.handlePageRender(Unknown Source) ~[na:na]
	at $ComponentRequestHandler_1321854fbcd92b80.handlePageRender(Unknown Source) ~[na:na]
	at org.apache.tapestry5.internal.services.PageRenderDispatcher.dispatch(PageRenderDispatcher.java:45) ~[tapestry-core-5.4-alpha-18.jar:na]
	at $Dispatcher_1321854fbcd92b82.dispatch(Unknown Source) ~[na:na]
	at $Dispatcher_1321854fbcd92b7d.dispatch(Unknown Source) ~[na:na]
	at org.apache.tapestry5.modules.TapestryModule$RequestHandlerTerminator.service(TapestryModule.java:298) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.services.RequestErrorFilter.service(RequestErrorFilter.java:26) ~[tapestry-core-5.4-alpha-18.jar:na]
	at $RequestHandler_1321854fbcd92b7e.service(Unknown Source) [na:na]
	at org.apache.tapestry5.modules.TapestryModule$3.service(TapestryModule.java:843) [tapestry-core-5.4-alpha-18.jar:na]
	at $RequestHandler_1321854fbcd92b7e.service(Unknown Source) [na:na]
	at org.apache.tapestry5.modules.TapestryModule$2.service(TapestryModule.java:833) [tapestry-core-5.4-alpha-18.jar:na]
	at $RequestHandler_1321854fbcd92b7e.service(Unknown Source) [na:na]
	at org.apache.tapestry5.internal.services.StaticFilesFilter.service(StaticFilesFilter.java:89) [tapestry-core-5.4-alpha-18.jar:na]
	at $RequestHandler_1321854fbcd92b7e.service(Unknown Source) [na:na]
	at org.apache.tapestry5.internal.services.CheckForUpdatesFilter$2.invoke(CheckForUpdatesFilter.java:105) [tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.services.CheckForUpdatesFilter$2.invoke(CheckForUpdatesFilter.java:95) [tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.ioc.internal.util.ConcurrentBarrier.withRead(ConcurrentBarrier.java:85) [tapestry-ioc-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.services.CheckForUpdatesFilter.service(CheckForUpdatesFilter.java:119) [tapestry-core-5.4-alpha-18.jar:na]
	at $RequestHandler_1321854fbcd92b7e.service(Unknown Source) [na:na]
	at $RequestHandler_1321854fbcd92b5c.service(Unknown Source) [na:na]
	at org.apache.tapestry5.modules.TapestryModule$HttpServletRequestHandlerTerminator.service(TapestryModule.java:249) [tapestry-core-5.4-alpha-18.jar:na]
	at org.tynamo.security.services.impl.SecurityConfiguration$1.call(SecurityConfiguration.java:56) [tapestry-security-0.5.1.jar:0.5.1]
	at org.tynamo.security.services.impl.SecurityConfiguration$1.call(SecurityConfiguration.java:54) [tapestry-security-0.5.1.jar:0.5.1]
	at org.apache.shiro.subject.support.SubjectCallable.doCall(SubjectCallable.java:90) [shiro-core-1.2.2.jar:1.2.2]
	at org.apache.shiro.subject.support.SubjectCallable.call(SubjectCallable.java:83) [shiro-core-1.2.2.jar:1.2.2]
	at org.apache.shiro.subject.support.DelegatingSubject.execute(DelegatingSubject.java:383) [shiro-core-1.2.2.jar:1.2.2]
	at org.tynamo.security.services.impl.SecurityConfiguration.service(SecurityConfiguration.java:54) [tapestry-security-0.5.1.jar:0.5.1]
	at $HttpServletRequestFilter_1321854fbcd92b5b.service(Unknown Source) [na:na]
	at $HttpServletRequestHandler_1321854fbcd92b5e.service(Unknown Source) [na:na]
	at org.apache.tapestry5.internal.gzip.GZipFilter.service(GZipFilter.java:59) [tapestry-core-5.4-alpha-18.jar:na]
	at $HttpServletRequestHandler_1321854fbcd92b5e.service(Unknown Source) [na:na]
	at org.apache.tapestry5.internal.services.IgnoredPathsFilter.service(IgnoredPathsFilter.java:62) [tapestry-core-5.4-alpha-18.jar:na]
	at $HttpServletRequestFilter_1321854fbcd92b58.service(Unknown Source) [na:na]
	at $HttpServletRequestHandler_1321854fbcd92b5e.service(Unknown Source) [na:na]
	at org.apache.tapestry5.modules.TapestryModule$1.service(TapestryModule.java:793) [tapestry-core-5.4-alpha-18.jar:na]
	at $HttpServletRequestHandler_1321854fbcd92b5e.service(Unknown Source) [na:na]
	at $HttpServletRequestHandler_1321854fbcd92b57.service(Unknown Source) [na:na]
	at org.apache.tapestry5.TapestryFilter.doFilter(TapestryFilter.java:166) [tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:243) [tomcat-embed-core-7.0.37.jar:7.0.37]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:210) [tomcat-embed-core-7.0.37.jar:7.0.37]
	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:222) [tomcat-embed-core-7.0.37.jar:7.0.37]
	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:123) [tomcat-embed-core-7.0.37.jar:7.0.37]
	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:472) [tomcat-embed-core-7.0.37.jar:7.0.37]
	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:171) [tomcat-embed-core-7.0.37.jar:7.0.37]
	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:99) [tomcat-embed-core-7.0.37.jar:7.0.37]
	at org.apache.catalina.valves.AccessLogValve.invoke(AccessLogValve.java:936) [tomcat-embed-core-7.0.37.jar:7.0.37]
	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:118) [tomcat-embed-core-7.0.37.jar:7.0.37]
	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:407) [tomcat-embed-core-7.0.37.jar:7.0.37]
	at org.apache.coyote.http11.AbstractHttp11Processor.process(AbstractHttp11Processor.java:1004) [tomcat-embed-core-7.0.37.jar:7.0.37]
	at org.apache.coyote.AbstractProtocol$AbstractConnectionHandler.process(AbstractProtocol.java:589) [tomcat-embed-core-7.0.37.jar:7.0.37]
	at org.apache.tomcat.util.net.JIoEndpoint$SocketProcessor.run(JIoEndpoint.java:310) [tomcat-embed-core-7.0.37.jar:7.0.37]
	at java.util.concurrent.ThreadPoolExecutor$Worker.runTask(ThreadPoolExecutor.java:895) [na:1.6.0_51]
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:918) [na:1.6.0_51]
	at java.lang.Thread.run(Thread.java:680) [na:1.6.0_51]
Caused by: java.lang.RuntimeException: Exception assembling root component of page Index: Could not convert 'collg3' into a component parameter binding: Exception generating conduit for expression 'collg3': Class net.company.pages.Index does not contain a property (or public field) named 'collg3'.
	at org.apache.tapestry5.internal.pageload.ComponentAssemblerImpl.performAssembleRootComponent(ComponentAssemblerImpl.java:142) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.pageload.ComponentAssemblerImpl.access$000(ComponentAssemblerImpl.java:42) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.pageload.ComponentAssemblerImpl$1.invoke(ComponentAssemblerImpl.java:93) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.pageload.ComponentAssemblerImpl$1.invoke(ComponentAssemblerImpl.java:90) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.ioc.internal.OperationTrackerImpl.invoke(OperationTrackerImpl.java:80) ~[tapestry-ioc-5.4-alpha-18.jar:na]
	... 84 common frames omitted
Caused by: org.apache.tapestry5.ioc.internal.util.TapestryException: Could not convert 'collg3' into a component parameter binding: Exception generating conduit for expression 'collg3': Class net.company.pages.Index does not contain a property (or public field) named 'collg3'.
	at org.apache.tapestry5.internal.services.BindingSourceImpl.newBinding(BindingSourceImpl.java:84) ~[tapestry-core-5.4-alpha-18.jar:na]
	at $BindingSource_1321854fbcd92ba2.newBinding(Unknown Source) ~[na:na]
	at org.apache.tapestry5.internal.services.PageElementFactoryImpl.newBinding(PageElementFactoryImpl.java:180) ~[tapestry-core-5.4-alpha-18.jar:na]
	at $PageElementFactory_1321854fbcd92bdc.newBinding(Unknown Source) ~[na:na]
	at org.apache.tapestry5.internal.pageload.PageLoaderImpl$10.execute(PageLoaderImpl.java:889) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.pageload.ComponentAssemblerImpl.runActions(ComponentAssemblerImpl.java:242) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.pageload.ComponentAssemblerImpl.performAssembleRootComponent(ComponentAssemblerImpl.java:118) ~[tapestry-core-5.4-alpha-18.jar:na]
	... 88 common frames omitted
Caused by: org.apache.tapestry5.internal.services.PropertyExpressionException: Exception generating conduit for expression 'collg3': Class net.company.pages.Index does not contain a property (or public field) named 'collg3'.
	at org.apache.tapestry5.internal.services.PropertyConduitSourceImpl.build(PropertyConduitSourceImpl.java:1419) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.services.PropertyConduitSourceImpl.create(PropertyConduitSourceImpl.java:1329) ~[tapestry-core-5.4-alpha-18.jar:na]
	at $PropertyConduitSource_1321854fbcd92bfc.create(Unknown Source) ~[na:na]
	at org.apache.tapestry5.internal.bindings.PropBindingFactory.newBinding(PropBindingFactory.java:49) ~[tapestry-core-5.4-alpha-18.jar:na]
	at $BindingFactory_1321854fbcd92bfd.newBinding(Unknown Source) ~[na:na]
	at $BindingFactory_1321854fbcd92bf3.newBinding(Unknown Source) ~[na:na]
	at org.apache.tapestry5.internal.services.BindingSourceImpl.newBinding(BindingSourceImpl.java:81) ~[tapestry-core-5.4-alpha-18.jar:na]
	... 94 common frames omitted
Caused by: org.apache.tapestry5.ioc.util.UnknownValueException: Class net.company.pages.Index does not contain a property (or public field) named 'collg3'.
	at org.apache.tapestry5.internal.services.PropertyConduitSourceImpl$PropertyConduitBuilder.findPropertyAdapter(PropertyConduitSourceImpl.java:1122) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.services.PropertyConduitSourceImpl$PropertyConduitBuilder.implementPropertyAccessors(PropertyConduitSourceImpl.java:492) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.services.PropertyConduitSourceImpl$PropertyConduitBuilder.implementAccessors(PropertyConduitSourceImpl.java:401) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.services.PropertyConduitSourceImpl$PropertyConduitBuilder.implementNavMethodAndAccessors(PropertyConduitSourceImpl.java:392) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.internal.services.PropertyConduitSourceImpl$PropertyConduitBuilder.transform(PropertyConduitSourceImpl.java:272) ~[tapestry-core-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.plastic.PlasticManager.createProxy(PlasticManager.java:235) ~[plastic-5.4-alpha-18.jar:na]
	at org.apache.tapestry5.ioc.internal.services.PlasticProxyFactoryImpl.createProxy(PlasticProxyFactoryImpl.java:64) ~[tapestry-ioc-5.4-alpha-18.jar:na]
	at $PlasticProxyFactory_1321854fbcd92bae.createProxy(Unknown Source) ~[na:na]
	at $PlasticProxyFactory_1321854fbcd92bad.createProxy(Unknown Source) ~[na:na]
	at org.apache.tapestry5.internal.services.PropertyConduitSourceImpl.build(PropertyConduitSourceImpl.java:1415) ~[tapestry-core-5.4-alpha-18.jar:na]
	... 100 common frames omitted

_________________________________________________________________________

TODO

* page with JCE status

* use org.tynamo.security:tapestry-security-jpa

* better default ssl config: enabling tests on IE?

* check https://kawwa.atosworldline.com/?

* load realms of Jetty ?




