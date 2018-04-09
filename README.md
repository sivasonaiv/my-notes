# my-notes
## Spark
### Spark Core
#### Architecture
Spark uses a master/worker architecture whereas Hadoop uses Master/Slave Architecture. There is a driver that talks to a single coordinator called master that manages workers in which executors run.

![Spark Architecture](https://jaceklaskowski.gitbooks.io/mastering-apache-spark/content/images/driver-sparkcontext-clustermanager-workers-executors.png)

The driver and the executors run in their own Java processes. You can run them all on the same (horizontal cluster) or separate machines (vertical cluster) or in a mixed machine configuration.


### Spark SQL
### Spark Streaming
