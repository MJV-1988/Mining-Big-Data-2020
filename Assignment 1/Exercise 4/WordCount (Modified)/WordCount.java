package edu.stanford.cs246.wordcount;

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
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class WordCount extends Configured implements Tool 
{
    public static void main(String[] args) throws Exception 
    {
        System.out.println(Arrays.toString(args));
        int res = ToolRunner.run(new Configuration(), new WordCount(), args);
        System.exit(res);
    }
    
    // Driver function
    @Override
    public int run(String[] args) throws Exception 
    {
        System.out.println(Arrays.toString(args));
        Job job = new Job(getConf(), "WordCount");
        job.setJarByClass(WordCount.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        // Attach mapper and reducer functions to job
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
        
        // Define input and output class format
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        
        // Define input and output directories
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
                
        job.waitForCompletion(true);
        return 0;
    }
    
    public static class Map extends Mapper<LongWritable, Text, Text, IntWritable> 
    {       
        @Override
        public void map(LongWritable key, Text value, Context context)
        throws IOException, InterruptedException 
        {     	
        	// Convert text to a string and break into words
            String line = value.toString();
            String[] words = line.split(" ");
            
            // For each word make a Key-Value pair
            for(String word : words)
            {
            	Text outputKey = new Text(word.toUpperCase().trim().replaceAll("[^A-Z]", ""));
            	IntWritable outputValue = new IntWritable(1);
            	
            	// Generate Key-Value pairs as (Word, 1)
            	context.write(outputKey,  outputValue);
            }
        }
    }
    
    public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable> 
    {
        @Override
        public void reduce(Text key, Iterable<IntWritable> values, Context context)
        throws IOException, InterruptedException 
        {
            int sum = 0;
            for (IntWritable val : values) 
            {
                sum += val.get();
            }
            
            context.write(key, new IntWritable(sum));
        }
    }
}