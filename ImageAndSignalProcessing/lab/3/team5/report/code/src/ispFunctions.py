__author__ = 'alexpashevich'

import numpy as np


def myMin(x):
    m = x[0]
    for k in range(len(x)):
        if x[k] < m:
            m = x[k]
    return m


def myMax(x):
    m = x[0]
    for k in range(0, len(x)):
        if x[k] > m:
            m = x[k]
    return m


def myMean(x):
    m = 0
    for x_i in x:
        m += x_i
    return 1. * m / len(x)


def myStandardDeviation(x):
    mean = myMean(x)
    d = 0
    for x_i in x:
        d += (x_i - mean) ** 2
    return (1. * d / (len(x) - 1)) ** .5


def myHistogram(x, intervalLength):
    minVal = myMin(x)
    maxVal = myMax(x)
    nb_bins = int((maxVal - minVal) / intervalLength) + 1
    h = np.zeros(nb_bins)
    h_x = np.zeros(nb_bins)
    for x_i in x:
        j = int((x_i - minVal) / intervalLength)
        h[j] += 1
        h_x[j] += x_i
    for j in range(0, len(h)):
        if h[j] != 0:
            h_x[j] /= h[j]
    return [h, h_x]


def myNormalizedHistogram(x, intervalLength):
    [h, h_x] = myHistogram(x, intervalLength)
    return [h / len(x), h_x]