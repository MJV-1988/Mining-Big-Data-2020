import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class StubMapper extends Mapper<LongWritable, Text, IntWritable, IntWritable> 
{
	@Override
	// Count the frequency of word lengths
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException 
	{
		// Convert text to a string and break into words
		String line = value.toString();
		String[] words = line.split(" ");
		
		// For each word get the first letter and form a Key-Value pair
		for(String word : words)
		{
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
