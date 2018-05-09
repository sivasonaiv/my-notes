To add to javadba's excellent answer, 

I recall the docs recommend to have your number of partitions set to 3 or 4 times the number of CPU cores in your cluster so that the work gets distributed more evenly among the available CPU cores. 
Meaning, if you only have 1 partition per CPU core in the cluster you will have to wait for the one longest running task to complete but if you had broken that down further the workload would be more evenly balanced with fast and slow running tasks evening out.
