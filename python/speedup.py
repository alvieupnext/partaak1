import pandas as pd

import matplotlib.pyplot as plt

speedup = pd.read_csv("pc/speedupf2/cores.csv")

def prepare(df):
  df.drop(df.columns[[0,1,2,3, 6]], axis=1, inplace=True)
  df.rename(columns = {"Param: p": "Cores"}, inplace = True)
  T1 = speedup.iloc[0].Score
  df["speedup"] = T1 / df["Score"]
  df["deltaSpeedUp"] = df["speedup"].diff()


prepare(speedup)

print(speedup)

# speedup.to_csv("Speed-Up Phase 1.csv")
#
# x = speedup.index
# y = speedup["Score"]
#
# plt.plot(x, y)
#
# plt.title('Milliseconds Per Operation Fase 2: Desktop')
# plt.ylabel("Time")
# plt.xlabel("Cores")
#
# plt.xticks(x, speedup["Cores"])
#
# plt.show()



# x = speedup.index
# y = speedup["speedup"]
#
# plt.plot(x, y)
#
# plt.title('Speed-Up Fase 2: Desktop')
# plt.ylabel("Speed-Up")
# plt.xlabel("Cores")
#
# plt.xticks(x, speedup["Cores"])
#
# plt.show()

x = speedup.index
y = speedup["deltaSpeedUp"]

plt.plot(x, y)

plt.title('Speed-Up From Previous Core Fase 1: Desktop')
plt.ylabel("Delta Speed-Up")
plt.xlabel("Cores")

plt.xticks(x, speedup["Cores"])

plt.show()