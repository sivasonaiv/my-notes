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
* Driver access Spark through SparkContext object which represents a connection to computing cluster

##### Worker
* Workers (aka slaves) are running Spark instances where executors live to execute tasks. They are the compute nodes in Spark.
* A worker receives serialized tasks that it runs in a thread pool.

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

##### Spark Execution Model
When you create SparkContext, each worker starts an executor. This is a separate process (JVM), and it loads your jar, too. The executors connect back to your driver program. Now the driver can send them commands, like flatMap, map and reduceByKey. When the driver quits, the executors shut down.
A new process is not started for each step. A new process is started on each worker when the SparkContext is constructed.
The executor deserializes the command (this is possible because it has loaded your jar), and executes it on a partition.
Shortly speaking, an application in Spark is executed in three steps:
Create RDD graph, i.e. DAG (directed acyclic graph) of RDDs to represent entire computation.
Create stage graph, i.e. a DAG of stages that is a logical execution plan based on the RDD graph. Stages are created by breaking the RDD graph at shuffle boundaries.
Based on the plan, schedule and execute tasks on workers.
In the WordCount example, the RDD graph is as follows:
file → lines → words → per-word count → global word count → output
Based on this graph, two stages are created. The stage creation rule is based on the idea of pipelining as many narrow transformations as possible. RDD operations with "narrow" dependencies, like map() and filter(), are pipelined together into one set of tasks in each stage.
In the end, every stage will only have shuffle dependencies on other stages, and may compute multiple operations inside it.
In the WordCount example, the narrow transformation finishes at per-word count. Therefore, you get two stages:
file → lines → words → per-word count
global word count → output
Once stages are defined, Spark will generate tasks from stages. The first stage will create ShuffleMapTasks with the last stage creating ResultTasks because in the last stage, one action operation is included to produce results.
The number of tasks to be generated depends on how your files are distributed. Suppose that you have 3 three different files in three different nodes, the first stage will generate 3 tasks: one task per partition.
Therefore, you should not map your steps to tasks directly. A task belongs to a stage, and is related to a partition.
The number of tasks being generated in each stage will be equal to the number of partitions.


### Spark SQL
### Spark Streaming
