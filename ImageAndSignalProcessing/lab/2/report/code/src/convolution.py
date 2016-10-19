# -*- coding: utf-8 -*-
import scipy as sp
import numpy as np
import matplotlib.pyplot as plt



#         ------          PART 1          --------
nbrSample          = 500

#-----------------------------------
# Gaussian noise parameters
#-----------------------------------
gaussianAmplitude   = 5
gaussianCenter      = nbrSample/2
gaussianWidth       = 50
gaussianRandomBoud  = 0.4
sp.random.seed(10)

#-----------------------------------
# First value of every system input signal.
# Respects the linearity condition of the filter
#-----------------------------------
y0_linearitycondition= 0

def signalX1(sampleSize):
    signal      = [0.5] * sampleSize
    signal[0]   = y0_linearitycondition
    for k in range(1, sampleSize):
        signal[k] = sp.math.sin((sp.math.pi*k)/50)
    return signal

def signalX2(sampleSize):
    signal      = [0.5] * sampleSize
    signal[0]   = y0_linearitycondition
    for k in range(1, sampleSize):
        tmpA        = sp.math.exp((-1./300.) * sp.math.pow((k-150), 2))
        tmpB        = sp.math.exp((-1./2500.) * sp.math.pow((k-150), 2))
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
        tmpNumerator    = -1* sp.math.pow((k-gaussianCenter), 2)
        tmpdenominator  = 2 * sp.math.pow(gaussianWidth, 2)
        signal[k] = gaussianAmplitude * sp.math.exp(tmpNumerator / tmpdenominator)
        # add a random noise to the gaussian
        signal[k] = signal[k] + sp.random.uniform(-gaussianRandomBoud, gaussianRandomBoud)
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

plt.plot(axis, signals[3], '-', label='Signal: X4')
plt.plot(axis, signalsFiltered[3], '-', label='Signal: S(X4)')
#plt.plot(axis, signalSum, '-', label='Signal: Xnoise = X1 + X2 + X3 + X4')
#plt.plot(axis, signalSumFiltered, '-', label='Signal: S(XXnoise)')

plt.legend()
plt.grid()
plt.xlabel('Time')
plt.ylabel('Signal value')
plt.show()





#         ------          PART 2          --------
def createOrdinateOf(a):
	m = np.empty_like(a)
	for k in range(len(a)):
			m[k] = k      
	return m
   
def fillWithZeros(array, newSize):
    if len(array) >= newSize: return array
    m = np.zeros(newSize)
    
    for i in range (0, len(array)):
        m[i] = array[i]
    
    return m

# <<< Question 2.1 >>>

def numpyConvolution1D(x, h):
   return np.convolve(x, h)
    
# x and y are arrays
# this returns sum of each x[l].y[k-l] for l ranging from 0 to k
def convolution_sum(x, y, k):
	temp_x = fillWithZeros(x, k+1)
	temp_y = fillWithZeros(y, k+1)
	res_sum = 0
	for l in range(0, k+1):
		res_sum += temp_x[l] * temp_y[k-l]
	return res_sum

def myConvolution1D(x, y):
   length = len(x) + len(y) - 1
   m = np.zeros(length)
   for k in range(0, length):
   	m[k] = convolution_sum(x, y, k)
   return m

x = np.array([1, 0, 2, 3, 2, 1, -1, -2, -1, 0, 2, 3, 3, 2, 1, 1])
h = np.array([2, 2, -1, -1, 3])

convo = numpyConvolution1D(x, h)
myconvo = myConvolution1D(x, h)


# input signal
plt.figure()
line = plt.plot(createOrdinateOf(x), x, '-', marker='h', linewidth=2)
line = plt.plot(createOrdinateOf(h), h, '-', marker='h', linewidth=2, color='RED')
#line = plt.plot(createOrdinateOf(convo), convo, '-', marker='h', linewidth=2, color='GREEN')
line = plt.plot(createOrdinateOf(myconvo), myconvo, '-', marker='h', linewidth=2, color='BLACK')
plt.grid()
plt.title('1D Convolution Implementation');
plt.ylabel('Ordinate')
plt.xlabel('Axis')
plt.show()


# <<< Question 2.4 >>>
def getZFromX(x, M):
	z = np.zeros(len(x))
	for k in range(0, len(x)):
		for l in range(0, M):
			if k - l < 0 : continue
			z[k] += x[k-l]
		z[k] *= 1/M
	return z

def getYFromZ(z, M):
	shift = int((M-1)/2)
	length = len(z) - shift
	y = np.zeros(len(z))
	for k in range(0, length):
		y[k] = z[k+shift]
	return y

M = 5

h = np.array([1/5, 1/5, 1/5, 1/5, 1/5])
numpy_old_y = numpyConvolution1D(Xnoise, h)
XnoiseConvo = myConvolution1D(Xnoise, h)
XnoiseConvoFiltered = getYFromZ(XnoiseConvo, M)

XnoiseConvoFiltered_51 = getYFromZ(XnoiseConvo, 51)

plt.figure()
line = plt.plot(createOrdinateOf(XnoiseConvo), XnoiseConvo, '-', marker='h', linewidth=2, color='RED')
plt.grid()
plt.title('Xnoisy signal');
plt.ylabel('Ordinate')
plt.xlabel('Axis')
plt.show()

plt.figure()
line = plt.plot(createOrdinateOf(XnoiseConvo), XnoiseConvo, '-', marker='h', linewidth=2, color='RED')
plt.grid()
plt.title('Xnoisy convolution');
plt.ylabel('Ordinate')
plt.xlabel('Axis')
plt.show()

plt.figure()
line = plt.plot(createOrdinateOf(XnoiseConvoFiltered), XnoiseConvoFiltered, '-', marker='h', linewidth=2, color='GREEN')
plt.grid()
plt.title('Xnoisy convolution with moving average filter');
plt.ylabel('Ordinate')
plt.xlabel('Axis')
plt.show()

plt.figure()
line = plt.plot(createOrdinateOf(XnoiseConvoFiltered_51), XnoiseConvoFiltered_51, '-', marker='h', linewidth=2, color='GREEN')
plt.grid()
plt.title('Xnoisy convolution with moving average filter and kernel 51');
plt.ylabel('Ordinate')
plt.xlabel('Axis')
plt.show()
