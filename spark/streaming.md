  
  
  Stream processing is the act of continuously incorporating new data to compute a result. In stream processing, the input data is unbounded and has no predetermined beginning or end. It simply forms a series of events that arrive at the stream processing system (e.g., credit card transactions, clicks on a website, or sensor readings from Internet of Things [IoT] devices)

 the Structured Streaming developers coined the term continuous applications to capture end-to-end applications that consist of streaming, batch, and interactive jobs all working on the same data to deliver an end product.
 
 challenges of streaming application
 high  throughput
 low latency
 out of order data

declarative API than one record at a time - provides abstraction of bunch of data that can be recovered in case of failure and no need to maintain state of individual events

event time Vs processing time
  Event time is the idea of processing data based on timestamps inserted into each record at the source, as opposed to the time when the record is received at the streaming application (which is called processing time). 
  
  continuous Vs micro batch
  continuous - one record at a time
  micro-batch systems wait to accumulate small batches of input data (say, 500 ms’ worth), then process each batch in parallel using a distributed collection of tasks, similar to the execution of a batch job in Spark. Micro-batch systems can often achieve high throughput per node because they leverage the same optimizations as batch systems (e.g., vectorized processing), and do not incur any extra per-record overhead, 

Spark’s Streaming APIs -DStream API and structured streaming API

Structured Streaming is also designed to make it easy to build end-to-end continuous applications using Apache Spark that combine streaming, batch, and interactive queries

developers do not need to maintain a separate streaming version of their batch code

Structured Streaming uses the existing structured APIs in Spark (DataFrames, Datasets, and SQL), meaning that all the operations you are familiar with there are supported. Users express a streaming computation in the same way they’d write a batch computation on static data

