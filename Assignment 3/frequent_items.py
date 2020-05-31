
import numpy as np
import pandas as pd

def main ():

    # # Define the baskets dictionary
    # baskets = {}
    #
    # # Open the input file
    # with open("Data/chess.txt") as input_file:
    #
    #     # increment for keys
    #     i = 0
    #
    #     # Read each line from the input file into a new dictionary element
    #     for line in input_file:
    #         baskets[i] = line
    #         i += 1

    # Read input file
    baskets_df = pd.read_csv("Data/chess.txt", sep='\s', engine='python', header=None)

    # Combine into single column and get count of each item
    item_count = []

    for i in range( len(baskets_df.columns) ):
        item_count.append(baskets_df[i])

    item_count_df = pd.DataFrame(pd.concat(item_count, ignore_index=True))

    # Calculate the number of other nodes that point to each node
    to_count_df = pd.DataFrame(item_count_df[0].value_counts()).sort_index()

    print(to_count_df)


    # print(baskets_df)

if __name__ == '__main__':
    main()
