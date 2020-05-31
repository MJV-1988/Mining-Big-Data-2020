=======================      GROUP MEMBERS     ==========================

Matthew Vincent   -   1148120   -    a1148120@student.adelaide.edu.au
Ayush Phansalkar  -   1776879   -    a1776879@student.adelaide .edu.au 


=======================  RUNNING INSTRUCTIONS  =========================

---------------------------  Exercise 1  --------------------------------

Calculation was done using a calculator, following the formulae outlined the section '1.2.3 An Example of Bonferroni’s Principle' 
of the textbook, Mining of Massive Datasets.



---------------------------  Exercise 2  --------------------------------

To run in standalone mode, import the WordCount.java file into a project in Eclipse. Set the necessary configurations to 
run as a hadoop job, as outlined in the tutorial. Enter the following arguments <pg100.txt> <output>. Then run as a 
Java Application. The output will appear in a folder titled "output", as a file titled "part-r-00000.txt". 

To run in pseudo-distributed mode, first export the project as a JAR File. Open the terminal, navigate to the /home/cloudera
directory, and run the commands:

hadoop fs -put workspace/WordCount/pg100.txt"
hadoop jar WordCount.jar edu.stanford.cs246.wordcount.WordCount pg100.txt output

This will execute the map-reduce procedure. The resulting output can be checked using the following command:

"hadoop fs -ls output"



---------------------------  Exercise 3  --------------------------------


To run in standalone mode, import the WordCount.java file into a project in Eclipse. Set the necessary configurations to 
run as a hadoop job, as outlined in the tutorial. Enter the following arguments <soc.txt> <output>. Then run as a 
Java Application. The output will appear in a folder titled "output", as a file titled "part-r-00000.txt". 

To run in pseudo-distributed mode, first export the project as a JAR File. Open the terminal, navigate to the /home/cloudera
directory, and run the commands:

hadoop fs -put workspace/FriendRecommend/soc.txt"
hadoop jar FriendRecommend.jar FriendRecommend.FriendRecommend soc.txt output

This will execute the map-reduce procedure. The resulting output can be checked using the following command:

"hadoop fs -ls output"



---------------------------  Exercise 4  --------------------------------

For Parts 1 & 2, import the WordLength.java file into a project in Eclipse. Set the necessary configuration to run as a
hadoop job. Run as a Java Application once using the arguments <pg100.txt> <output_pg100>, and a second time using the 
arguments <pg3399.txt> <output_pg3399>. The output will appear in each of the respective output folders. 

For Parts 3 & 4, run both the pg100.txt and pg3399.txt files through the WordCount map-reduce procedure. The output from these
two jobs will include one instance of each word that appears in the files, with the number of times that it appears. This can 
therefore be used as the input argument for the WordLength map-reduce procedure, counting repeated words only once. 

Relabel the output files from the WordCount procedure as pg100_unique.txt and pg3399_unique.txt, then execute the WordLength 
job using these as input arguments. 


