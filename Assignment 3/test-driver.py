
import pandas as pd
from matplotlib import pyplot as plt
from test_function import test_func

def main ():

    test_df = pd.DataFrame({"A": [2, 4, 1, 6], "B": [32, 45, 87, 12]})

    print(test_df)

    test_func(test_df)


    print(test_df)

    # # Monthly average precipitation
    # boulder_monthly_precip = [0.70, 0.75, 1.85, 2.93, 3.05, 2.02,
    #                           1.93, 1.62, 1.84, 1.31, 1.39, 0.84]
    #
    # # Month names for plotting
    # months = ["Jan", "Feb", "Mar", "Apr", "May", "June", "July",
    #           "Aug", "Sept", "Oct", "Nov", "Dec"]
    #
    # fig, ax = plt.subplots()
    #
    # ax.scatter(months, boulder_monthly_precip)
    # ax.set(title = "Average Monthly Precipitation in Boulder, CO",
    #         xlabel = "Month",
    #         ylabel = "Precipitation (inches)")
    #
    # plt.show()

if __name__ == '__main__':
    main()
