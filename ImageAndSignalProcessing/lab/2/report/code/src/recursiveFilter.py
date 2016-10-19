from scipy import *

import numpy as np
import matplotlib.pyplot as plt




nbrSample          = 500

#-----------------------------------
# Gaussian noise parameters
#-----------------------------------
gaussianAmplitude   = 5
gaussianCenter      = nbrSample/2
gaussianWidth       = 50
gaussianRandomBoud  = 0.4
random.seed(10)

#-----------------------------------
# First value of every system input signal.
# Respects the linearity condition of the filter
#-----------------------------------
y0_linearitycondition= 0

def signalX1(sampleSize):
    signal      = [0.5] * sampleSize
    signal[0]   = y0_linearitycondition
    for k in range(1, sampleSize):
        signal[k] = math.sin((math.pi*k)/50)
    return signal

def signalX2(sampleSize):
    signal      = [0.5] * sampleSize
    signal[0]   = y0_linearitycondition
    for k in range(1, sampleSize):
        tmpA        = math.exp((-1./300.) * math.pow((k-150), 2))
        tmpB        = math.exp((-1./2500.) * math.pow((k-150), 2))
        signal[k] = 4*tmpA - tmpB
    return signal

def signalX3(sampleSize):
    signal      = [0.5] * sampleSize
    signal[0]   = y0_linearitycondition
    for k in range(1, sampleSize):
        if ((240<=k) and (k <300)):
            signal[k] = 1
        elif ((300<=k) and (k <380)):
            signal[k] = -2
        else:
            signal[k] = 0;
    return signal

def signalX4(sampleSize):
    signal      = [0.5] * sampleSize
    signal[0]   = y0_linearitycondition
    for k in range(1, sampleSize):
        tmpNumerator    = -1* math.pow((k-gaussianCenter), 2)
        tmpdenominator  = 2 * math.pow(gaussianWidth, 2)
        signal[k] = gaussianAmplitude * math.exp(tmpNumerator / tmpdenominator)
        # add a random noise to the gaussian
        signal[k] = signal[k] + random.uniform(-gaussianRandomBoud, gaussianRandomBoud)
    return signal

# Input: array of signals, and number of samples of each signal
def signalSumOfSignals(arrayOfSignals, sampleSize, nbrSignals):
    signal      = [0.5] * sampleSize
    for k in range(0, sampleSize):
        signal[k] = arrayOfSignals[0][k]
        for s in range(1, nbrSignals):
            signal[k] += arrayOfSignals[s][k]
    return signal


def singleLowPassFilter(inputSignal):
    signal = [0.05*inputSignal[0]] # since y0 = 0 signal[0] = 0.05 * x[0] + 0.095*0
    for i in range(1, len(inputSignal)):
        signal.append(0.05*inputSignal[i]+0.95*signal[i-1])

    return signal

#    sampleSize      = len(inputSignal)
#    outputSignal    = [0.5] * sampleSize
#    outputSignal[0] = 0.05*inputSignal[0] + 0.95*y0_linearitycondition
#    for k in range(1, sampleSize):
#        outputSignal[k] = 0.05 * float(inputSignal[k]) + 0.95 * float(inputSignal[k-1])
#    return outputSignal


# --------------------------------
# Signal definition
# --------------------------------
signals         = [[]] * 4
signals[0]      = signalX1(nbrSample)
signals[1]      = signalX2(nbrSample)
signals[2]      = signalX3(nbrSample)
signals[3]      = signalX4(nbrSample)

signalsFiltered     = [[]] * 4
signalsFiltered[0]  = singleLowPassFilter(signalX1(nbrSample))
signalsFiltered[1]  = singleLowPassFilter(signalX2(nbrSample))
signalsFiltered[2]  = singleLowPassFilter(signalX3(nbrSample))
signalsFiltered[3]  = singleLowPassFilter(signalX4(nbrSample))

signalSum           = signalSumOfSignals(signals,         nbrSample, 4)
signalSumFiltered   = signalSumOfSignals(signalsFiltered, nbrSample, 4)
signalFilteredSum   = singleLowPassFilter(signalSum)
Xnoise              = signalSumOfSignals(signals,         nbrSample, 4)

# --------------------------------
# Signal drawing
# --------------------------------
axis        = np.array(range(nbrSample))
plt.figure()

plt.plot(axis, signalSum, '-', label='Signal: XNoise')
#plt.plot(axis, signalsFiltered[3], '-', label='Signal: S(X4)')
#plt.plot(axis, signalSum, '-', label='Signal: Xnoise = X1 + X2 + X3 + X4')
#plt.plot(axis, signalSumFiltered, '-', label='Signal: S(XXnoise)')

plt.legend()
plt.grid()
plt.xlabel('Time')
plt.ylabel('Signal value')
plt.show()
