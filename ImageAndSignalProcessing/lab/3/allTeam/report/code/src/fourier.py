import math
import numpy as np
import matplotlib.pyplot as plt





#------------------------------------
# Exo 1
#------------------------------------
def myDFT1D(signal):
    length  = len(signal)
    i       = complex(0,1)
    res     = []
    for muInd in range(0, length):
        currRes  = 0
        for xInd in range(0, length):
            currRes  += signal[xInd] *  np.exp(-i * 2 * np.pi * muInd * xInd / length)
        res.append(currRes)
    return res

def myInvDFT1D(ft):
    length  = len(ft)
    i       = complex(0,1)
    res     = []
    for tInd in range(0, length):
        currRes  = 0
        for muInd in range(0, length):
            currRes += ft[muInd] * np.exp(i * 2 * np.pi * tInd * muInd / length)
        res.append(currRes / length)
    return res

def gauss(range, coeff, coeffExp):
    res     = []
    for k in xrange(range):
        res.append(coeff * math.exp(-k*k*coeffExp))
    return res

#------------------------------------
# Exo 2
#------------------------------------
def generateDelta(IdxSampleWhereImpuls, numberOfSamples):
    res = [0 for j in xrange(numberOfSamples)]
    res[IdxSampleWhereImpuls] = 1
    return res

def realPartOfComplexArray(a):
    length  = len(a)
    res     = [0. for x in range(0, length)]
    for x in range(0, length):
        res[x] = a[x].real
    return res
def immaginaryPartOfComplexArray(a):
    length  = len(a)
    res     = [0. for x in range(0, length)]
    for x in range(0, length):
        res[x] = a[x].imag
    return res
def magnitudeOfComplexArray(a):
    length  = len(a)
    res     = [0. for x in range(0, length)]
    for x in range(0, length):
        val = a[x].imag * a[x].imag + a[x].real * a[x].real
        res[x] = math.sqrt(val)
    return res
def phaseOfComplexArray(a):
    length  = len(a)
    res     = []
    for x in range(0, length):
        res.append(math.atan(1.0 * a[x].imag / a[x].real))
    return res
def myCos(coeffOut, coeffIn, axis):
    res     = []
    length  = len(axis)
    for k in range(0, length):
        res.append(coeffOut*math.cos(coeffIn*k/length))
    return res

def mySin(coeffOut, coeffIn, axis):
    res     = []
    length  = len(axis)
    for k in range(0, length):
        res.append(coeffOut*math.sin(coeffIn*k/length))
    return res

#------------------------------------
# Exo 3
#------------------------------------
# Return the linear convolution of the 2 causal input signals
# M is the number os samples in the output signal
def my1DLinearConvolution(x, y, M):
    Nx  = len(x)
    Ny  = len(y)
    z   = []
    for k in xrange(M):
        resK = 0
        for l in xrange(k+1):
            if  l >= Nx:
                break
            elif k-l >= Ny:
                continue
            else:
                resK += x[l] * y[k-l]
        z.append(resK)
    return z

def my1DCircularConvolution(x, y, M):
    Nx  = len(x)
    Ny  = len(y)
    z   = []
    for k in xrange(M):
        resK = 0
        for l in xrange(M):
            if  l >= Nx:
                break
#-----------Other cases

        z.append(resK)
    return z

# --------------------------------
# Signal definition
# --------------------------------
"""

# Exo 1
nbrSignalSamples= 10
gaussExpCoeff   = 1.5
gaussCoeff      = 1
gaussExpCoeffT  = math.pi*math.pi / gaussExpCoeff
gaussCoeffT     = math.sqrt(math.pi/gaussExpCoeff)
signal          = gauss(nbrSignalSamples, gaussCoeff, gaussExpCoeff)
expectedTransf  = gauss(nbrSignalSamples, gaussCoeffT, gaussExpCoeffT)
ft              = myDFT1D(signal)
#ftFt            = myInvDFT1D(ft)
imFt            = immaginaryPartOfComplexArray(ft)
realFt          = realPartOfComplexArray(ft)
axis            = np.array(range(nbrSignalSamples))

"""

# Exo 2
nonNullIndex    = 4
nbrSignalSamples= 50
axis            = np.array(range(nbrSignalSamples))
impulse         = generateDelta(nonNullIndex, nbrSignalSamples)
ftImpulse       = myDFT1D(impulse)
ReFtImpulse     = realPartOfComplexArray(ftImpulse)
ImgFtImpulse    = immaginaryPartOfComplexArray(ftImpulse)
MagFtImpulse    = magnitudeOfComplexArray(ftImpulse)
PhaseFtImpulse  = phaseOfComplexArray(ftImpulse)
# create the expected results
ExpectedRe      = myCos(1, 2*np.pi*nonNullIndex, axis)
ExpectedImg     = mySin(-1, 2*np.pi*nonNullIndex, axis)
ExpectedMag     = [1 for i in range(0, nbrSignalSamples)]
ExpectedPhase   = [0 for i in xrange(nbrSignalSamples)]
for w in xrange(nbrSignalSamples):
    ExpectedPhase[w] = (-2*np.pi*nonNullIndex*w)%(2*math.pi)

"""


# Exo 3
x               = [5, 1, 2, 6, 4]
y               = [1, 2, 3]
M               = len(x) + len(y) -1
LinearConvolution= my1DLinearConvolution(y, x, M)
axisX           = np.array(range(len(x)))
axisY           = np.array(range(len(y)))
axisLinearConvolution= np.array(range(M))
"""

# --------------------------------
# Signal printing
# --------------------------------
# Exo 1
"""
plt.figure()
plt.plot(axis, signal,          label='Gaussian function (A = 1.5)')
plt.plot(axis, expectedTransf,  label='Expected Gaussian function transform')
plt.grid()
plt.xlabel('Time')
plt.ylabel('Signal value')
plt.legend()
plt.show()


plt.figure()
plt.plot(axis, [0]*len(axis),            label='Imaginary part of the transform')
plt.plot(axis, expectedTransf,          label='Real part of the transform')
plt.plot(0, -.5)
plt.grid()
plt.xlabel('Time')
plt.ylabel('Signal value')
plt.legend()
plt.show()


"""


# Exo 2
plt.figure()
plt.plot(axis, ReFtImpulse,     'ro', color='blue', label='Real part of the Fourier transform of the impulse (a=8)')
plt.plot(axis, ImgFtImpulse,    'ro', color='green', label='Imaginary part of the Fourier transform of the impulse(a=8)')
plt.plot(axis, ExpectedRe,     label='Expected Real part of the Fourier transform(a=8)')
plt.plot(axis, ExpectedImg,    label='Expected Imaginary part of the Fourier transform(a=8)')
plt.plot(0, 2)
plt.plot(0, -.5)
plt.grid()
plt.legend()
plt.xlabel('Time')
plt.ylabel('Signal value')
plt.show()



plt.figure()
plt.plot(0, 3)
plt.plot(0, -.5)
plt.plot(axis, MagFtImpulse,    'ro', color='green', label='Magnitude of the Fourier transform of the impulse(a=4)')
plt.plot(axis, PhaseFtImpulse,  'ro', color='red', label='Phase of the Fourier transform of the impulse(a=4)')
plt.plot(axis, PhaseFtImpulse,  '-', color='red')
plt.plot(axis, ExpectedMag,    label='Expected Magnitude of the Fourier transform(a=4)')
#plt.plot(axis, ExpectedPhase,  label='Expected Phase of the Fourier transform(a=0))')
plt.grid()
plt.legend()
plt.xlabel('Time')
plt.ylabel('Signal value')
plt.show()

"""
# Exo 3
plt.figure()
plt.plot(axisX,                 x,                  label='X Signal')
plt.plot(axisY,                 y,                  label='Y Signal')
plt.plot(axisLinearConvolution, LinearConvolution,  label='Linear convolution in full length')
plt.grid()
plt.legend()
plt.xlabel('Time')
plt.ylabel('Signal value')
plt.show()

"""
