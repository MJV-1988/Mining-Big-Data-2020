package FriendRecommend;
import java.io.IOException;  
import java.util.*;  
import java.util.Map.Entry;
import org.apache.commons.lang.*;  
import org.apache.hadoop.fs.Path;  
import org.apache.hadoop.conf.*;  
import org.apache.hadoop.io.*;  
import org.apache.hadoop.mapreduce.*;  
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;  
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;  
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;  
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;  
   
public class FriendRecommend
{
	public static class Map extends Mapper<LongWritable,Text,IntWritable,Text>
	{
		public void map(LongWritable key,Text data,Context context) throws IOException,InterruptedException
		{
			String input = data.toString();
			String[] splitData = input.split("\t",-1);
			if(splitData.length==2)
			{//check whether this is a valid line of data
				if(splitData[1].length() != 0)
					IntWritable userKey = new IntWritable(Integer.parseInt(splitData[0]));
					String[] friends = splitData[1].split(",");
					String friend a;
					String friend b;
					IntWritable key a = new IntWritable();
					IntWritable key b = new IntWritable();
					Text value a = new Text();
					Text value b = new Text();
					for(int i=0;i<friends.length;i++)
					{
						friend a = friends[i];
						value a.set("1,"+friend a);
						context.write(userKey,value a);
						key a.set(Integer.parseInt(friend a));
						value a.set("2,"+friend a);
						for (int j=i+1;j<friends.length;j++)
						{
							friend b = friends[j];
							key b.set(Integer.parseInt(friend b));
							value b.set("2,"+friend b);
							context.write(key a,value b);
							context.write(key b,value a);
						}
					}
				}
			}
		}
	}
   
	public static class Reduce extends Reducer<IntWritable,Text,IntWritable,Text>
	{
		public void reduce(IntWritable key, Iterable<Text> friendPair, Context output) throws IOException,InterruptedException
		{
			String[] friend;
			HashMap<String,Integer>hash = new HashMap<String,Integer>();
			for(Text value:friendPair){
				friend = (value.toString()).split(",");
				if(friend[0].equals("1")){
					hash.put(friend[1],-1);//if they are 1 degree friends, save the data
				}else{//else they are 2 degree friends
					if(hash.containsKey(friend[1])){
						if(hash.get(friend[1])!=-1)
							hash.put(friend[1],hash.get(friend[1])+1);
						}
					}
					else{
						hash.put(friend[1], 1);
					}
				}
			}
			ArrayList<Entry<String,Integer>> list = new ArrayList<Entry<String,Integer>>();
			for(Entry<String,Integer> entry : hash.entrySet()){  
                if(entry.getValue()!=-1){
                    list.add(entry);//if the friend is not 1 degree friend, store him
				}
            }
			Collections.sort(list,new Comparator<Entry<String,Integer>>(){
                public int compare(Entry<String,Integer> entry1,Entry<String,Integer>entry2){
                    return entry1.getValue().compareTo(entry2.getValue());//sort by 2 degree friends amount
				}
            });
			ArrayList<Integer>sameLevel = new ArrayList<Integer>();
			ArrayList<Integer>fo = new ArrayList<Integer>();
			if(list.size()==1)  {
                output.write(key, new Text(StringUtils.join(list,",")));
			}
			else if(list.size()>1)
			{
				sameLevel.add(Integer.parseInt(list.get(0).getKey()));
				for(int i=1;i<Math.min(10,list.size());i++){
					if(list.get(i).getValue()==list.get(i-1).getValue()){//use temperory list to save the people with same number of mutural friends
						sameLevel.add(Integer.parseInt(list.get(i).getKey()));
					}else{
						Collections.sort(sameLevel);
						for(int j=0;j<sameLevel.size();j++)
							fo.add(sameLevel.get(j));
						sameLevel.clear();
						sameLevel.add(Integer.parseInt(list.get(i).getKey()));
					}// update temperory list of data to main output list. then clear temperory list and save the new data
                }
				Collections.sort(sameLevel);
				for(int j=0;j<sameLevel.size();j++)
					fo.add(sameLevel.get(j));
                output.write(key,new Text(StringUtils.join(fo,",")));
			}
		}
	}
	
	public static void main(String[]args) throws Exception
	{
        Configuration conf = new Configuration();
		Job job = new Job(conf,"FriendRecommend");
        job.setJarByClass(FriendRecommend.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(Text.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.addInputPath(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));
        job.waitForCompletion(true);  
    }
}