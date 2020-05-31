
import time
import numpy as np
import pandas as pd

def main ():

    t1=time.time()

    # Read input file
    input_df = pd.read_csv("web-Google.txt", sep="\t", skiprows=3)

    # Rename column headers for consistency
    input_df.columns = ['FromNodeId','ToNodeId']

    # Generate a unique list of all nodes (From and To), with corresponding index
    nodes_df = pd.DataFrame(pd.concat([input_df['FromNodeId'], input_df['ToNodeId']]), columns=['Nodes'])
    nodes_df = pd.DataFrame(nodes_df.Nodes.unique(), columns=['UniqueNodes'])
    nodes_df.sort_values(by=['UniqueNodes'], inplace=True)
    nodes_df.reset_index(inplace=True)
    del nodes_df['index']

    # Get the node count
    node_count = len(nodes_df.index)
    
    # Sort by the FromNodeId column
    input_df.sort_values(by=['FromNodeId'], inplace=True)
    
    # Calculate the number of other nodes that point to each node
    to_count_df = pd.DataFrame(input_df['FromNodeId'].value_counts()).sort_index()
    
    # Promote the index to a column and rename headers
    to_count_df = to_count_df.reset_index()
    to_count_df.columns = ['FromNodeId','CountOfToNodes']
    
    # Merge the count into the input dataframe
    input_df = pd.merge(input_df, to_count_df, on='FromNodeId')
    
    # Calculate the resulting probabilities
    input_df['TransitionProb'] = 1 / input_df['CountOfToNodes']
    
    # To implement taxation, declare beta value
    beta = 0.8
    
    # Initialize the distribution vector as a dataframe
    v_df = nodes_df
    v_df.columns = ['Nodes']
    v_df['CurrentProb'] = 1 / node_count

    # MapReduce implementation:
    
    # In the map step, the individual products are computed for each
    # transition probability and distribution vector element
    # In the reduce step, the grouped sum of these products is calculated
    # for each ToNodeId

    for i in range(0,60):

        #######    Map Step    #######

        # Merge the distribution vector into the input dataframe by joining
        # FromNodeId in the input dataframe to the Nodes in the distribution dataframe
        input_df = pd.merge(input_df, v_df, left_on='FromNodeId', right_on='Nodes')
        input_df.drop(columns=['Nodes'], inplace=True)

        # Calculate all the individual products that will be summed to compute the necessary dot products
        input_df['PartialDotProduct'] = beta * input_df['TransitionProb'] * input_df['CurrentProb']

        #######   Reduce Step   #######

        # Sum the 'partial dot products' for each ToNodeId
        v_df = pd.DataFrame(input_df.groupby('ToNodeId')['PartialDotProduct'].sum())
        v_df.reset_index(inplace=True)
        v_df.columns = ['Nodes', 'CurrentProb']

        # Add the second term required for taxation
        v_df['CurrentProb'] = v_df['CurrentProb'] + (1-beta) * 1 * 1/node_count

        # Remove the dot product and probability columns - these will be updated
        # with the values at the next iteration
        input_df.drop(columns=['PartialDotProduct', 'CurrentProb'], inplace=True)

    # Sort by the probabilities to find the highest ranking pages
    v_df.columns = ['NodeId', 'PageRank']
    v_df.sort_values(by=['PageRank'], ascending=False, inplace=True)

    # Output the top 10 pages
    print("\nTop ten nodes by largest PageRank: ")
    print((v_df.head(10).to_string(index=False)))

    # Write the results to a csv file
    v_df.to_csv('PageRank-results.csv', index=False)

    t2 = time.time()
    execution_time = t2-t1

    # Output the execution time
    print("\nExecution time:" )
    print(execution_time)

if __name__ == '__main__':
    main()
    
