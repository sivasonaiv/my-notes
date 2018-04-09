# my-notes
## Spark
### Spark Core
#### Architecture
Spark uses a master/worker architecture whereas Hadoop uses Master/Slave Architecture. There is a driver that talks to a single coordinator called master that manages workers in which executors run.

![Spark Architecture](https://jaceklaskowski.gitbooks.io/mastering-apache-spark/content/images/driver-sparkcontext-clustermanager-workers-executors.png)

The driver and the executors run in their own Java processes. You can run them all on the same (horizontal cluster) or separate machines (vertical cluster) or in a mixed machine configuration.
##### Master
Is a running spark instance that connects to a cluster manage for resources 

##### Driver
* JVM Process that hosts SparkContext for a Spark Application
* Master node in a spark application
* Splits a Spark application into tasks and schedules them to run on executors
* Driver is where the task scheduler lives and spawns tasks across workers
* Driver coordinates workers and overall execution of tasks

##### Worker

##### Executor
* Distributed agent responsible for executing tasks
* Typically runs for the entire lifetime of a Spark application which is called static allocation of executors
* Executors provide in-memory storage for RDDs that are cached in Spark applications (via Block Manager).
* When executor starts it first registers with the driver and communicates directly to execute tasks
* Executors can run multiple tasks over its lifetime, both in parallel and sequentially
* Executor use a Executor task launch worker thread pool for launching tasks
* Executors send metrics(and heartbeats) using the internal hearbeater - Hearbeat Sender Thread
* It is recommended to have as many executors as data nodes and as many cores as you can get from the cluster.

##### Task Runner
* TaskRunner is a thread of execution that manages a single individual task
* TaskRunner is created exclusively when Executor is requested a launch a task

##### Dynamic Allocation (of Executors)
* Dynamic Allocation (of Executors) (aka Elastic Scaling) is a Spark feature that allows for adding or removing Spark executors dynamically to match the workload.
* Unlike the "traditional" static allocation where a Spark application reserves CPU and memory resources upfront (irrespective of how much it may eventually use), in dynamic allocation you get as much as needed and no more. It scales the number of executors up and down based on workload, i.e. idle executors are removed, and when there are pending tasks waiting for executors to be launched on, dynamic allocation requests them.
* Dynamic allocation is enabled using spark.dynamicAllocation.enabled setting. When enabled, it is assumed that the External Shuffle Service is also used (it is not by default as controlled by spark.shuffle.service.enabled property).
* Programmable Dynamic Allocation - SparkContext offers the following methods as the developer API for dynamic allocation of executors:
  * requestExecutors
  * killExecutors
  * requestTotalExecutors
  * (private!) getExecutorIds

### Spark SQL
### Spark Streaming
