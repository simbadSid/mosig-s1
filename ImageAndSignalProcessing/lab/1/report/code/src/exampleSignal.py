# -*- coding: utf-8 -*-
"""
Created on Wed Sep  9 12:44:13 2015

@author: cfouard
"""
import numpy as np
import matplotlib.pyplot as plt

#########Maim program########################
# Definition of axis and ordinate tables
axis = np.array(	[-5.3,		-3.5,		-2,		0,		1,		3])
ordinate = np.array(	[3.401877,	-1.056171,	2.830992,	2.984400,	4.3116474,	-3.024486])

newAxis = np.array(	[1,		12])
newOrdinate = np.array(	[876,		876])
# plotting data into a figure
# Do not hesitate to visit http://matplotlib.org/faq/howto_faq.html
# http://matplotlib.org/examples/index.html
plt.figure()
line = plt.plot(axis, ordinate, '-', marker='h', linewidth=2)
plt.grid()
plt.title('An example of signal...');
plt.ylabel('Ordinate')
plt.xlabel('Axis')
plt.show()

# Saving the signal
# Please refer to http://docs.scipy.org/doc/numpy/reference/generated/numpy.savetxt.html
np.savetxt('../output/example.txt', (axis, ordinate))
