# -*- coding: utf-8 -*-
import numpy as np
import matplotlib.pyplot as plt



# Definition of expertID and expertPrediction tables
expertID		= np.array([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12])
expertPrediction= np.array([996, 868, 855, 956, 867, 933, 866, 887, 936, 901, 818, 956])
trueValue		= 876

true_stock_line_axis = np.array([1, 12])
true_stock_line_ordinate = np.array([trueValue])

plt.figure()
line = plt.plot(expertID, expertPrediction, '-', marker='h', linewidth=2)
line = plt.plot(([0,12]), ([trueValue, trueValue]), '--', color='red', linewidth=2)

plt.grid()
plt.title('Financial predictions...')
plt.ylabel('Stock market price prediction')
plt.xlabel('Financial expert')
plt.show()

# Saving the signal
# Please refer to http://docs.scipy.org/doc/numpy/reference/generated/numpy.savetxt.html
np.savetxt('../output/experts.txt', (expertID, expertPrediction))
