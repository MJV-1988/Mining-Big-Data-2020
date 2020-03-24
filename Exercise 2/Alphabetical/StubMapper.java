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
    char firstLetter;
    // For each word get the first letter and form a Key-Value pair
    for(String word : words){
    	// Remove symbols, spaces and convert to upper-case
    	word = word.toUpperCase().trim().replaceAll("[^A-Z]", "");
    	// Check if the anything is left in the string
    	if (word.length() > 0){ firstLetter = word.charAt(0); }
    	else{ firstLetter = '-'; }
    	// Convert to expected data types
    	Text outputKey = new Text(Character.toString(firstLetter));
    	IntWritable outputValue = new IntWritable(1);
    	// Generate Key-Value pairs as (Letter, 1)
    	context.write(outputKey,  outputValue);
    }
  }
}
