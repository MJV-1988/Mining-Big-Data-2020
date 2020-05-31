
import numpy as np
import pandas as pd
import random
import math
import seaborn as sns
from matplotlib import pyplot as plt

def k_means(k, start_indexes, data):

    i = 0

    # Extract the points at the initial indexes
    for i in range(0, k):
        centroid_df = centroid_df.append(iris_df.iloc[start_indexes[i]])

    centroid_df = centroid_df.iloc[:, :-1]
    # print(centroid_df)

    # print("The centroid dataframe after removing the last column:")
    # print(centroid_df)

    centroid_df = centroid_df.reset_index(drop=True)

    # print(centroid_df)

    # Add a new column for updated clusters
    iris_df["Cluster_New"] = np.NaN

    # Loop over rows in iris_df
    for i in range(0, m):

        # get the current row
        m_i = iris_df.iloc[i]

        cent_0_0 = centroid_df.iloc[0][0]
        cent_0_1 = centroid_df.iloc[0][1]
        cent_0_2 = centroid_df.iloc[0][2]
        cent_0_3 = centroid_df.iloc[0][3]

        min_distance = math.sqrt( (m_i[0]-cent_0_0)**2+(m_i[1]-cent_0_1)**2+(m_i[2]-cent_0_2)**2+(m_i[3]-cent_0_3)**2 )

        iris_df.at[i,"Cluster"] = 0

        # Calculate the distance to the remaining centroids, if it is lower
        # that the current min_distance, update min_distance
        for j in range(1, k):

            cent_j_0 = centroid_df.iloc[j][0]
            cent_j_1 = centroid_df.iloc[j][1]
            cent_j_2 = centroid_df.iloc[j][2]
            cent_j_3 = centroid_df.iloc[j][3]

            distance = math.sqrt( (m_i[0]-cent_j_0)**2+(m_i[1]-cent_j_1)**2+(m_i[2]-cent_j_2)**2+(m_i[3]-cent_j_3)**2 )

            if distance < min_distance:
                min_distance = distance
                iris_df.at[i,"Cluster"] = j

    # Recalculate the cluster averages
    centroid_df = iris_df.groupby('Cluster').mean()
    centroid_df = centroid_df.iloc[:, :-1]
    # print(centroid_df)

    # Variable to check whether the means have converged
    converged = False

    # print("Iris_df after first iteration:")
    # print(iris_df)

    while not converged:

        # Loop over rows in iris_df
        for i in range(0, m):

            # get the current row
            m_i = iris_df.iloc[i]

            cent_0_0 = centroid_df.iloc[0][0]
            cent_0_1 = centroid_df.iloc[0][1]
            cent_0_2 = centroid_df.iloc[0][2]
            cent_0_3 = centroid_df.iloc[0][3]

            min_distance = math.sqrt( (m_i[0]-cent_0_0)**2+(m_i[1]-cent_0_1)**2+(m_i[2]-cent_0_2)**2+(m_i[3]-cent_0_3)**2 )

            iris_df.at[i,"Cluster_New"] = 0

            # Calculate the distance to the remaining centroids, if it is lower
            # that the current min_distance, update min_distance
            for j in range(1, k):

                cent_j_0 = centroid_df.iloc[j][0]
                cent_j_1 = centroid_df.iloc[j][1]
                cent_j_2 = centroid_df.iloc[j][2]
                cent_j_3 = centroid_df.iloc[j][3]

                distance = math.sqrt( (m_i[0]-cent_j_0)**2+(m_i[1]-cent_j_1)**2+(m_i[2]-cent_j_2)**2+(m_i[3]-cent_j_3)**2 )

                if distance < min_distance:
                    min_distance = distance
                    iris_df.at[i,"Cluster_New"] = j



        # Recalculate the cluster averages
        centroid_df = iris_df.groupby('Cluster_New').mean()
        centroid_df = centroid_df.iloc[:, :-1]

        # Check to see if the new clusters are different from the old
        if( iris_df["Cluster_New"].equals(iris_df["Cluster"]) ):
            converged = True
        else:
            iris_df["Cluster"] = iris_df["Cluster_New"]

    # Variable for average distance
    avg_distance = 0

    # Calculate the average distance to the
    for i in range(0, m):

        # get the current row
        m_i = iris_df.iloc[i]

        # Get the cluster
        cluster_i = iris_df.at[i,"Cluster"].astype(int)

        cent_i_0 = centroid_df.iloc[cluster_i][0]
        cent_i_1 = centroid_df.iloc[cluster_i][1]
        cent_i_2 = centroid_df.iloc[cluster_i][2]
        cent_i_3 = centroid_df.iloc[cluster_i][3]

        distance = math.sqrt( (m_i[0]-cent_i_0)**2+(m_i[1]-cent_i_1)**2+(m_i[2]-cent_i_2)**2+(m_i[3]-cent_i_3)**2 )

        avg_distance += distance

    # print(avg_distance)

    avg_distance = avg_distance / m

    # print(avg_distance)

    return avg_distance

def main ():

    # Read input file
    iris_df = pd.read_csv("Data/iris.txt", sep=',', engine='python', header=0)

    # Drop the label column
    iris_df = iris_df.iloc[:, :-1]
    iris_df["Cluster"] = np.NaN

    # Get the number of rows in dataframe
    m = len(iris_df.index)

    # Create dataframe to store centroids
    centroid_df = pd.DataFrame(columns=iris_df.columns)

    # Create array to store random numbers
    start_indexes = []
    start_indexes.append(random.randrange(0, m))

    # Choose k numbers randomly from the index, ensuring no duplicates
    i = 1
    while i < k:
        new_index = random.randrange(0, m)

        if new_index not in start_indexes:
            start_indexes.append(new_index)
            i += 1

    # Dataframe for holding the average distance to centroid
    avg_distance_df = pd.DataFrame(columns=['k value','Average Distance'])

    for i in range(2, 10):
        avg_distance = k_means(i, start_indexes)

        print(i)
        print(avg_distance)
        avg_distance_df.append(pd.DataFrame({'k value': i, 'Average Distance': avg_distance}, index=[0]))

        print(avg_distance_df)

    print(avg_distance_df)


    ##############         Create the plot       ##################

    # # Set the style
    # plt.style.use('ggplot')
    #
    # fig, ax = plt.subplots()
    #
    # ax.scatter(iris_df.loc[iris_df['Cluster']==1, ['sepal_length']],iris_df.loc[iris_df['Cluster']==1, ['sepal_width']], label='Cluster 1', marker='.', color='b')
    # ax.scatter(iris_df.loc[iris_df['Cluster']==2, ['sepal_length']],iris_df.loc[iris_df['Cluster']==2, ['sepal_width']], label='Cluster 2', marker='.', color='r')
    # ax.scatter(iris_df.loc[iris_df['Cluster']==3, ['sepal_length']],iris_df.loc[iris_df['Cluster']==3, ['sepal_width']], label='Cluster 3', marker='.', color='g')
    #
    # ax.scatter(centroid_df.iloc[0, 0], centroid_df.iloc[0,1], marker='D', color='b')
    # ax.scatter(centroid_df.iloc[1, 0], centroid_df.iloc[1,1], marker='D', color='r')
    # ax.scatter(centroid_df.iloc[2, 0], centroid_df.iloc[2,1], marker='D', color='g')
    #
    # ax.set(title = "K-means clustering on iris dataset",
    #         xlabel = "sepal length", ylabel = "sepal width")
    #
    # ax.legend(loc='lower right')
    #
    # plt.show()

if __name__ == '__main__':
    main()
