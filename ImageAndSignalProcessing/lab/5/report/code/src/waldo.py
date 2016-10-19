import numpy as np
import matplotlib.pyplot as plt
import re
from __builtin__ import str
from math import sqrt




def read_pgm(filename, byteorder='>'):
    """Return image data from a raw PGM file as numpy array.
 
    Format specification: http://netpbm.sourceforge.net/doc/pgm.html
 
    """
    with open(filename, 'rb') as f:
        buffer = f.read()
    try:
        header, width, height, maxval = re.search(
            b"(^P5\s(?:\s*#.*[\r\n])*"
            b"(\d+)\s(?:\s*#.*[\r\n])*"
            b"(\d+)\s(?:\s*#.*[\r\n])*"
            b"(\d+)\s(?:\s*#.*[\r\n]\s)*)", buffer).groups()
    except AttributeError:
        raise ValueError("Not a raw PGM file: '%s'" % filename)
    return np.frombuffer(buffer,
                            dtype='u1' if int(maxval) < 256 else byteorder+'u2',
                            count=int(width)*int(height),
                            offset=len(header)
                            ).reshape((int(height), int(width)))

def display_image(image, vmin=0, vmax=255):
    plt.imshow(image, plt.cm.gray, vmin=vmin, vmax=vmax)
    # if SAVE_IMAGE:
        # plt.savefig('../output/img.png')
    plt.show()



def energy2D(img):
    res = 0
    for x in range(len(img)):
        for y in range(len(img[0])):
            res += img[x, y] * img[x, y]
    return res

def correlationPartial2D(U, V, correlationIndexX, correlationIndexY):
    res = 0
    widthV  = len(V[0])
    heightV = len(V)
    for kx in range(0, widthV):
        for ky in range(0, heightV):
            cu  = U[ky + correlationIndexY][kx + correlationIndexX]
            cv  = V[ky][kx]
#            print("xu = " + str(ky + correlationIndexY) + "   yu = " + str(kx + correlationIndexX))
#            print("xv = " + str(kx) + "   yv = " + str(kx))
#            print("cu" + str(cu))
#            print("cv" + str(cv))
#            print("--------------------")
            res += cu * cv
#    print("res = " + str(res))
    return res





image           = read_pgm("../input/FindWaldo2.pgm")   * 1.
pattern         = read_pgm("../input/WadoTarget2.pgm")  * 1.
imageWidth      = len(image[0])
imageHeight     = len(image)
patternWidth    = len(pattern[0])
patternHeight   = len(pattern)
energyImage     = energy2D(image)
energyPattern   = energy2D(pattern)
image           = image     / sqrt(energyImage)
pattern         = pattern   / sqrt(energyPattern)

#display_image(image)
#display_image(pattern)



bestLx          = -1
bestLy          = -1
bestCorrelation = -2

for lx in range(imageWidth-patternWidth):
    for ly in range(imageHeight-patternHeight):
        correlation = correlationPartial2D(image, pattern, lx, ly)
        if (correlation > bestCorrelation):
            bestLx          = lx
            bestLy          = ly
            bestCorrelation = correlation


print("Best correlation           : " + str(bestCorrelation))
print("Waldon found at the pixels : (" + str(lx) + ", " + str(ly) + ")")













