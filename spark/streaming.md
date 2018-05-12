  
  
  Stream processing is the act of continuously incorporating new data to compute a result. In stream processing, the input data is unbounded and has no predetermined beginning or end. It simply forms a series of events that arrive at the stream processing system (e.g., credit card transactions, clicks on a website, or sensor readings from Internet of Things [IoT] devices)

 the Structured Streaming developers coined the term continuous applications to capture end-to-end applications that consist of streaming, batch, and interactive jobs all working on the same data to deliver an end product.
 
 challenges of streaming application
 high  throughput
 low latency
 out of order data
 
 
design approach 

declarative API than one record at a time - provides abstraction of bunch of data that can be recovered in case of failure and no need to maintain state of individual events

event time Vs processing time
  Event time is the idea of processing data based on timestamps inserted into each record at the source, as opposed to the time when the record is received at the streaming application (which is called processing time). 
  
  continuous Vs micro batch
  continuous - one record at a time
  micro-batch systems wait to accumulate small batches of input data (say, 500 ms’ worth), then process each batch in parallel using a distributed collection of tasks, similar to the execution of a batch job in Spark. Micro-batch systems can often achieve high throughput per node because they leverage the same optimizations as batch systems (e.g., vectorized processing), and do not incur any extra per-record overhead, 

Spark’s Streaming APIs -DStream API and structured streaming API

Structured Streaming is also designed to make it easy to build end-to-end continuous applications using Apache Spark that combine streaming, batch, and interactive queries. Single application for all these - batch, stream and interactive query


developers do not need to maintain a separate streaming version of their batch code

Structured Streaming uses the existing structured APIs in Spark (DataFrames, Datasets, and SQL), meaning that all the operations you are familiar with there are supported. Users express a streaming computation in the same way they’d write a batch computation on static data

These logical instructions for the computation are then executed using the same Catalyst engine, including query optimization, code generation, etc. 
main idea behind Structured Streaming is to treat a stream of data as a table to which data is continuously appended. The job then periodically checks for new input data, process it, updates some internal state located in a state store if needed, and updates its result

Continuous Application-A continous application is an end-to-end application that reacts to data in real time by combining a variety of tools: streaming jobs, batch jobs, joins between streaming and offline data, and interactive ad-hoc queries

Transformation  and action - 
All transformations are available for streaming dataframe api
only one action available in Structured Streaming: that of starting a stream, which will then run continuously and output results.

## Components 
* Input Sources - Spark 2.2, the supported input sources are as follows:
  * Apache Kafka 0.10
  * Files on a distributed file system like HDFS or S3 (Spark will continuously read new files in a directory)
  * A socket source for testing

**Output Sinks** - supported output sinks as of Spark 2.2:
Apache Kafka 0.10
Almost any file format
A foreach sink for running arbitary computation on the output records
A console sink for testing
A memory sink for debugging

**Output modes** - how we write data to sinks
supported output modes are as follows:
Append (only add new records to the output sink)
Update (update changed records in place)
Complete (rewrite the full output)


**Triggers**
Whereas output modes define how data is output, triggers define when data is output—that is, when Structured Streaming should check for new input data and update its result.
Spark also supports triggers based on processing time (only look for new data at a fixed interval).

**Event-Time Processing**
support for event-time processing (i.e., processing data based on timestamps included in the record that may arrive out of order)

**Event-time Data**
Event-time means time fields that are embedded in your data
Expressing event-time processing is simple in Structured Streaming. Because the system views the input data as a table, the event time is just another field in that table, and your application can do grouping, aggregation, and windowing using standard SQL operators

**Watermark**
Watermarks are a feature of streaming systems that allow you to specify how late they expect to see data in event time.
usually allow setting watermarks to limit how long they need to remember old data. Watermarks can also be used to control when to output a result for a particular event time window (e.g., waiting until the watermark for it has passed).

**Sample Streaming application**
storing streaming data into in memory table
```scala
val streaming = spark.readStream.schema(dataSchema)
.option("maxFilesPerTrigger", 1).json("/data/activity-data")
val activityCounts = streaming.groupBy("gt").count()
val activityQuery = activityCounts.writeStream.queryName("activity_counts")
.format("memory").outputMode("complete").start()
activityQuery.awaitTermination()
```

Structured Streaming does not let you perform schema inference without explicitly enabling it. You can enable schema inference for this by setting the configuration spark.sql.streaming.schemaInference to true. 


awaitTermination() to prevent the driver process from exiting while the query is active.

spark.streams.active - to see active streams running

Input and output 

file source and file sink

The only difference between using the file source/sink and Spark’s static file source is that with streaming, we can control the number of files that we read in during each trigger via the maxFilesPerTrigger option that we saw earlier.

Kafka source and Kafka sink

maxOffsetsPerTrigger - The total number of offsets to read in a given trigger.

Stream Testing

socket source, console sink and memory sink - used for testing streaming application 
```scala
// in Scala import org.apache.spark.sql.functions.{window, col} 
withEventTime.groupBy(window(col("event_time"), "10 minutes", "5 minutes")) .count() .writeStream .queryName("events_per_window") .format("memory") .outputMode("complete") .start()
```

Batch Interval Vs Sliding Window Interval

Sometimes we need to know what happened in last n seconds every m seconds. 
As a simple example, lets say batch interval is 10 seconds and we need to know what happened in last 60 seconds every 30 seconds.
Here 60 seconds is called window length and 30 second slide interval. 
Lets say first 6 batches are A,B,C,D,E,F which are part of first window. 
After 30 seconds second window will ve formed which will have D,E,F,G,H,I. 
As you can see 3 batches are common between first and second window.

One thing to remember about window is that Spark holds onto the whole window in memory. 
In first window it will combine RDD A to F using union operator to create one big RDD. 
It is going to take 6 times memory and is ok if thats what you need. 
In some cases though you may need to transfer some state batch to batch. 
This can be accomplished using updateStateByKey.




