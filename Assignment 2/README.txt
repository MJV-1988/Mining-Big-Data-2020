
=======================      GROUP MEMBERS     ==========================

Matthew Vincent   -   1148120   -    a1148120@student.adelaide.edu.au
Ayush Phansalkar  -   1776879   -    a1776879@student.adelaide.edu.au 


=======================  RUNNING INSTRUCTIONS  =========================


---------------------------  Exercise 3  --------------------------------

PageRank has been implemented as a Python script, saved in the file PageRank.py. 
To run this code, install and open the 64-bit version of the Python 3.8 shell. The code
has been written for the 64-bit version, and will not execute on the 32-bit version. 

The code will read in web-Google.txt as input. Note that the script reads the text file itself, 
not the zipped .gz file that is downloaded from http://snap.stanford.edu/data/web-Google.html. 
This file must be unzipped first and saved in the same directory as the source file, prior to 
executing the script. 

The number of iterations of the algorithm is set at line 59. In the submitted script, the
number of iterations is set at 60. 

The code will compute the PageRank score for each node after 60 iterations, and write the
results to a csv file titled PageRank-results.csv. In addition, the top ten nodes with 
the largest PageRank score will be printed to the terminal, along with the execution time.

