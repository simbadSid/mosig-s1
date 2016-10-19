#######To clear the working memory###########
def clearall():
    all = [var for var in globals() if var[0] != "_"]
    for var in all:
        del globals()[var]
#############################################
 
clearall()
 
# imports necessary for this exercise       
import re
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.image as mpimg
 
# Import your functions from previous computer exercises
from ispFunctions import myMin, myMax, myHistogram
 
# Function to read a pgm image from a file
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
 
# Function used in this computer exercise to display an images                                   
def displayImage(image):
    plt.imshow(image, plt.cm.gray, vmin=0, vmax=255)
    plt.show()


def displayHistogram(binPos, binVal, intervalLength):
    plt.figure()
    plt.grid()
    plt.bar(binPos, binVal, intervalLength)
    x1,x2,y1,y2 = plt.axis()
    plt.axis((0,256,y1,y2))
    plt.title('Histogram')
    plt.xlabel('Color range')
    plt.ylabel('Color cardinal')
    plt.show()

def stretchContrast(inputImage, newRangeMin, newRangeMax):
    actualMax = myMax(inputImage)
    actualMin = myMin(inputImage)
    outputImage = np.zeros(len(inputImage))
    for i in range(len(inputImage)):
        newVal = newRangeMin + (newRangeMax - newRangeMin) / (actualMax - actualMin) * (inputImage[i] - actualMin)
        outputImage[i] = newVal
    return outputImage

if __name__ == "__main__":
    # open image (choose the right one)
    path = "../resource/amelie.pgm"
    # path = "../resource/amelie.pgm"
    # path = "../resource/mystery.pgm"
    img = read_pgm(path)
    (width, height) = img.shape

    # perform contrasting
    imgContrasted = stretchContrast(img.flatten(), 0, 255)
    # change image from 1D to 2D
    imgStretched = imgContrasted.reshape(width, height)
    # show what happened
    displayImage(img)
    displayImage(imgStretched)

    # show histograms to compare them
    intervalLength = 1
    [binValBefore, binPosBefore] = myHistogram(img.flatten(), intervalLength)
    [binValAfter, binPosAfter] = myHistogram(imgContrasted, intervalLength)
    displayHistogram(binPosBefore, binValBefore, intervalLength)
    displayHistogram(binPosAfter, binValAfter, intervalLength)

    # print values of histograms if needed
    # print("Bins positions:")
    # print(binValBefore)
    # print("Bins values:")
    # print(binValBefore)

    # print("Bins positions:")
    # print(binPosAfter)
    # print("Bins values:")
    # print(binValAfter)







