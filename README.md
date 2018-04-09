# my-notes
## Spark
### Spark Core
#### Architecture
Spark uses a master/worker architecture whereas Hadoop uses Master/Slave Architecture. There is a driver that talks to a single coordinator called master that manages workers in which executors run.

![Spark Architecture](https://jaceklaskowski.gitbooks.io/mastering-apache-spark/content/images/driver-sparkcontext-clustermanager-workers-executors.png)

The driver and the executors run in their own Java processes. You can run them all on the same (horizontal cluster) or separate machines (vertical cluster) or in a mixed machine configuration.

##### Driver

* JVM Process that hosts SparkContext for a Spark Application
* Master node in a spark application
* Splits a Spark application into tasks and schedules them to run on executors
* Driver is where the task scheduler lives and spawns tasks across workers
* Driver coordinates workers and overall execution of tasks

##### Executor

* Distributed agent responsible for executing tasks
* Typically runs for the entire lifetime of a Spark application which is called static allocation of executors
* Executors provide in-memory storage for RDDs that are cached in Spark applications (via Block Manager).
* When executor starts it first registers with the driver and communicates directly to execute tasks
* Executors can run multiple tasks over its lifetime, both in parallel and sequentially
* Executor use a Executor task launch worker thread pool for launching tasks
* Executors send metrics(and heartbeats) using the internal hearbeater - Hearbeat Sender Thread
* It is recommended to have as many executors as data nodes and as many cores as you can get from the cluster.



### Spark SQL
### Spark Streaming
