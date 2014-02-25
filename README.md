###Reasoning:

***this is a learning project, and as such - parts of code are flaky***

This projects is easing the pain of doing ad-hoc testing of TCP based servers. It shouldn't be even considered alpha - this is a learning project, whose only purpose was to make me use:

* [dropwizard](http://www.dropwizard.io/),
* [netty](http://netty.io/)
* and [ember.js](http://emberjs.com/)

(I didn't had an opportunity to do so during my normal job and always wanted to do something more ambitious using mentioned tools.)

On a daily basis, I'm working with servers using [Extensible Provisioning Protocol](http://tools.ietf.org/html/rfc5730). Most of the code is old enough to remember the beginnings of steam engines (or at least it feels like that), I wanted to create a programmable tool which might ease the pain of testing parts of the non-testable (legacy) server calls. Without any refactorings, the easiest way of doing this is by directly sending EPP messages to test servers and validating the responses.

While implementing the whole tool, I will try to be generic enough, so introduction of new protocols which the server will be capable of speaking to, should be relatively straightforward.

###Installation & building.

Project is using ``gradle`` as the build tool, which simplifies management of dependencies and creation of build files for specific IDE's.

Also, as a nice nifty hack - though it's not mandatory, there is a ``npm's`` ``package.json`` file present, which, after running

```
npm install
```

from the directory of the project, installs ``gulp``. Then, when developing, one can just open a terminal window and run ``gulp``, which starts watching of static files in ``core/src/main/resources`` and each time any of those is being updated, it automatically copies them to the build folder (saving a lot of server restarts).

