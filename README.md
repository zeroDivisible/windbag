###Reasoning:

This projects is easing the pain of doing ad-hoc testing of TCP based servers. It shouldn't be even considered alpha - this is a learning project, whose only purpose was to make me use:

* [dropwizard](http://www.dropwizard.io/),
* [netty](http://netty.io/)
* and [emberjs](http://emberjs.com/)

I didn't had an opportunity to do so during my normal job and always wanted to do something more ambitious using mentioned tools.

On a daily basis, I'm working with servers using [Extensible Provisioning Protocol](http://tools.ietf.org/html/rfc5730). Most of the code is old enough to remember the beginnings of steam engines (or at least it feels like that), I wanted to create a programmable tool which might ease the pain of testing parts of the non-testable (legacy) server calls. Without any refactorings, the easiest way of doing this is by directly sending EPP messages to test servers and validating the responses.

While implementing the whole tool, I will try to be generic enough, so introduction of new protocols which the server will be capable of speaking to, should be relatively straightforward.

Readme will be updated as more features will be added.
