from scipy import *

import matplotlib.pyplot as plt
import numpy as np
from experts import expertPrediction, trueValue





def myMin(x):
	m = x[0]
	for k in range(len(x)):
		if (x[k] < m):
			m = x[k]        
	return m

def myMax(x):
	m = x[0]
	for k in range(0, len(x)):
		if (x[k] > m):
			m = x[k]        
	return m


def myMean(signal):
	res = signal[0]
	for k in range(1, len(signal)):
		res = res + signal[k]
	return res/len(signal)


def myStandardDeviation(signal):
	mean = myMean(signal)
	s	= 0
	for k in range(len(signal)):
		s += math.pow((signal[k] - mean),  2)
	return math.sqrt(s / (len(signal)-1))

# tv: true average value of the signal (unlike the mean which comes from experimental values)
def myAccuracy(signal, tv):
	mean = myMean(signal)
	return (math.fabs(tv - mean))

# Creates a histogram with an interval length equals 1.
def myHistogram(signal):
	histogram	= [0] * len(signal)
	minVal		= myMin(signal)
	for i in range(len(signal)):
		j = int(signal[i] - minVal)
		histogram[j] = histogram[j] + 1
	return histogram

#
#def myHistogram(signal, intervalLength):
#	histogram	= [0] * intervalLength
#	minVal		= myMin(signal)
#	for i in range(len(signal)):
#		j = int((signal[i] - minVal) / intervalLength)
#		histogram[j] = histogram[j] + 1
#	return histogram

def myHistogram(signal,intervalLength):
    minVal = myMin(signal)
    maxVal = myMax(signal)
    length = len(signal)
    N = int(ceil((maxVal-minVal)/intervalLength)) + 1
    h = [0]*N
    histoAxis = [m * intervalLength + minVal for m in xrange(N)]
    for i in range(0,length - 1):
            h[int(ceil((signal[i] - minVal)/intervalLength))] += 1

    return h,histoAxis


mean		= myMean(expertPrediction);
deviation	= myStandardDeviation(expertPrediction)
accuracy	= myAccuracy(expertPrediction, trueValue)
f			= open("../output/ispFunction.txt", 'w')
f.write("Mean of the experts prediction: " + str(mean) + "\n")
f.write("Standard deviation of the experts prediction: " + str(deviation) + "\n")
f.write("Accuracy of the experts prediction: " + str(accuracy) + "\n")
f.close()
#print("The standard deviation of the expert's predictions is: ")
#print(deviation)mean_standardDeviation



(h, histo) = myHistogram(expertPrediction, 10)
print(histo)




#question 6
randomSignalSize	= np.array([10000, 20000, 30000, 40000, 50000, 60000, 70000, 80000, 90000, 100000])
meanDistance		= np.array(range(0, size(randomSignalSize)), dtype=float)
stdDeviationDistance= np.array(range(0, size(randomSignalSize)), dtype=float)
for i in range(0, size(randomSignalSize)):
    signal		= np.random.random(randomSignalSize[i])
    mean		= myMean(signal)
    deviation	= myStandardDeviation(signal)
    a  			= 0
    b			= randomSignalSize[i]-1
    meanDistance[i]			= 5*mean	/ ((a+b)/2)
    stdDeviationDistance[i]	= deviation	/ ((b-a)/math.sqrt(12))


plt.figure()
line = plt.plot(randomSignalSize, meanDistance, '--', color='blue', linewidth=2)
line = plt.plot(randomSignalSize, stdDeviationDistance, '-', color='red', linewidth=2)

plt.grid()
plt.title('Theoretical VS computed values...')
plt.xlabel('Number of sample by signal')
plt.ylabel('Ratio of the theoretical and the computed value')
plt.show()




