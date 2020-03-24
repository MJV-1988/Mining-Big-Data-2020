import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class StubMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

  @Override
  public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
	// Convert text to a string and break into words
    String line = value.toString();
    String[] words = line.split(" ");
    // For each word make a Key-Value pair
    for(String word : words){
    	Text outputKey = new Text(word.toUpperCase().trim().replaceAll("[^A-Z]", ""));
    	IntWritable outputValue = new IntWritable(1);
    	// Generate Key-Value pairs as (Word, 1)
    	context.write(outputKey,  outputValue);
    }
  }
}
