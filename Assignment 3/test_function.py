
import pandas as pd
from matplotlib import pyplot as plt

def test_func(a_df):

    a_df.rename(columns={"A": "New A", "B": "New B"}, inplace=True)
    a_df['New B'] = [1000, 1000, 1000, 1000]
