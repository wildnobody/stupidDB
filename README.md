# stupidDB
A new, easy to use java ORM. Pre-alpha.

**Why another java ORM?**

Pretty simple: while looking into wrting a website in java i noticed that no java web framework had built-in database access, like rails or Django. Since i was going to need Users and a lot of data mangment, this was unacceptable. Tried Hibernate, which is unnecessarily complicated, and EclipseLink, which does nothing (What good is an ORM if i still have to write code in SQL?). The attempt is to manage a relationl database almost as if it was NoSQL.

**Code ditribution**

The actual source is in src/zech.stupidDB.

A dummy dataset is in src/userend. note that to use this you will need to put the sqlite jar in your classpath, and set the location of you db in seetings.java.

**Peace out**
