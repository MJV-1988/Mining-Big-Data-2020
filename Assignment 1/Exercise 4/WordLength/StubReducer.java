import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class StubReducer extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {

  @Override
  // Count the number of times the key has been produced by the map function
  public void reduce(IntWritable key, Iterable<IntWritable> values, Context context)
      throws IOException, InterruptedException {
	  // Each reduced will only receive one key
	  int sum = 0;
	  // Sum over the number of occurrences of the key
	  for(IntWritable value : values){ sum += value.get(); }
	  IntWritable outputValue = new IntWritable(sum); // write() unable to accept IntWritable?
	  // Output as (Word, Frequency)
	  context.write(key, outputValue);
  }
}