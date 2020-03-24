import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class StubMapper extends Mapper<LongWritable, Text, IntWritable, IntWritable> {

  @Override
  // Count the frequency of word lengths
  public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
	// Convert text to a string and break into words
    String line = value.toString();
    String[] words = line.split(" ");
    // For each word get the first letter and form a Key-Value pair
    for(String word : words){
    	// Remove symbols and spaces
    	word = word.trim().replaceAll("[^A-Z]", "");
    	// Convert to expected output data types
    	IntWritable outputKey = new IntWritable(word.length());
    	IntWritable outputValue = new IntWritable(1);
    	// Generate Key-Value pairs as (Length, 1)
    	context.write(outputKey,  outputValue);
    }
  }
}
