import pandas as pd

import matplotlib.pyplot as plt

# netid: avargasg

seq = pd.read_csv("pc/thresholdf2/seq.csv")

su = pd.read_csv("pc/thresholdf2/speedup.csv")

thr = pd.read_csv("pc/thresholdf2/threshold.csv")

Tseq = seq["Score"].mean()

def prepare(df, cores):
  df.drop(df.columns[[0,1,2,3, 6]], axis=1, inplace=True)

  name = "T" + str(cores)
  df.rename(columns = {'Score': name, "Score Error (99.9%)": name + ' error', "Param: T": "T"}, inplace = True)


prepare(thr, 1)
prepare(su, 6)

merged = pd.merge(thr,su, on = "T", how='outer')
merged.insert(loc=0, column='Tseq', value=pd.Series([Tseq for x in range(len(merged.index))]))

#overhead
merged['overhead'] = merged['T1'] / merged['Tseq']
# merged['c_speedup'] = merged['T1'] / merged['T6']
merged['a_speedup'] = merged['Tseq'] / merged['T6']

# merged.to_csv("Optimal Threshold Phase 2.csv")
# merged = pd.read_csv("Optimal Threshold Phase 1.csv")

#cut 1, 10 and 100
cut = merged.tail(-1)

# print(thr)
# print(su)

y = cut['a_speedup']
x = cut.index



print(merged)

plt.plot(x, y)
plt.title('Applicational Speed-Up Fase 2: Desktop')
plt.xlabel("Sequential Threshold")
plt.ylabel("Speed-Up")

plt.xticks(x, cut["T"])
plt.show()


plt.plot(x, y)
plt.title('Overhead Fase 2: Desktop')
plt.xlabel("Sequential Threshold")
plt.ylabel("overhead")

plt.xticks(x, cut["T"])


