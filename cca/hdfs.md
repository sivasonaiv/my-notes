HDFS

File system that manages storage across network of machines are called as distributed filesystems.

HDFS good in storing
	- very large files
	- streaming data access - write once read many times pattern
	- commodity hardware

HDFS not good for
	- low-latency data access - large amount of data can be retrieved with the cost of latency
	- lots of small files
	- multiple writers - arbitrary file modifications
	
HDFS block size - 128 MB by default (can be overridden through configuration)

HDFS blocks are large compared to disk blocks to minimize the cost of seeks

Namenodes and data nodes
	Name Node(Master) and no of datanodes(Worker)
Name node
	- manages filesystem namespace - maintains filesystem tree and the metadata of all the files
	  and directories in the tree
	- this information stored persistently on the local disk in the form of two files
		the namespace image and the edit log
	- it does not store block locations persistently - this information reconstructed from datanode
		when the system starts

POSIX - portable operating system interface

Data node
	- datanodes are the workhorses of the filesystem
	- store and retrieve blocks when they are told to and report back to the name node periodically
		with the list of blocks that they are storing

without name node - cant read the filesystem - no way to reconstruct the files from its blocks
name node should be resilient to failure - there are two ways
	- back up the files of filesystem metadata - hadoop can be configured so that namenode
		writes its persistent state to multiple file systems. these writes are synchronous and atomic
		usually write them into both local disk and remote NFS
	- secondary name node - periodically merge the namespace image with the edit log to
		prevent the edit log becoming too large. this copy of merged namespace image can be used
		in the event of failure

Block Caching
	- frequently accessed files/blocks cached in off-heap block cache
	- for increased read performance - no of datanodes to cache can be configured per-file basis
	- ex: lookup table used in a join
	- users/application instruct namenode to which files to be cached by adding cache directive to
	   a cache pool


HDFS Federation
	- scales cluster by adding additional name node
	 to manage amount of filesystem metadata storage to overcome memory issues
	- each namenode manages portion of the filesystem name space
		namenode 1 - /user
		namenode 2 - /share
	- failure of one namenode does not affect the availability of namespaces managed by
		other namenode 

HDFS High Availability
	- replicating namnode metadata on multiple filesystems and using the secondary namenode 
		- still it does not provide high availability - name node is still single point of failure
	- solution to this problem - instead of having secondary namenode, can configure
		standby namenode with shared editlogs 
		- namenodes must use shared storage to share the edit logs. in case of failure of namenode,
			standby namenode reads end of shared edit log to synchronize its 
			state with the active namenode
		- datanodes should send block report to both the namenodes because block mappings
			stored in a namenodes memory not on disk
		- standby namnode incorporates secondary namenodes role - taking periodic checkpoints of
			active namenodes namespace
		- there are two ways for shared storage - NFS filer and QJM - quorum journal manager
			QJM - runs a group of journal nodes and each edit must be written to a majority of 
			the journal nodes







		- 


	