#######To clear the working memory###########
from scipy.fftpack.basic import fft2
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
from scipy.fftpack import fft2, fftshift, ifft2


SHOW_TITLE = True
SAVE_IMAGE = True


#--------------------------------------------------- 
# Question 1.1
#--------------------------------------------------- 
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
def display_image(image, title="Image", vmin=0, vmax=255, extent=None):
    plt.imshow(image, plt.cm.gray, vmin=vmin, vmax=vmax, extent=extent)
#    if SAVE_IMAGE:
#        plt.savefig('../output/' + title.replace(' ', '_') + '.png')
    plt.show()

#--------------------------------------------------- 
# Question 1.2
#--------------------------------------------------- 
# Computes the sum of all the pixels of the input image
def sum_pixels(img):
    (width, height) = img.shape
    sum             = 0
    for x in xrange(width):
        for y in xrange(height):
            sum += img[x, y]
    return sum

# return the list of solutions (u, v) such as ft2D(u, v) = s
def solve_discret_equation_FT(ft2d, s):
    res = []
    for x in xrange(len(ft2d)):
        for y in xrange(len(ft2d[0])):
            if ft2d[x, y] == s:
                res.append((x, y))
    return res
        


#--------------------------------------------------- 
# Question 1.3
#--------------------------------------------------- 
def power_spectrum(ft2d):
    res     = []
    epsilon = np.finfo(float).eps
    for x in xrange(len(ft2d)):
        for y in xrange(len(ft2d[0])):
            val             = ft2d[x][y]
            magnitudeSquare = val.imag * val.imag + val.real * val.real
            res.append(np.log(magnitudeSquare + epsilon))
    return res

def power_spectrum_2D(ft2d, fx=None, fy=None):
    epsilon = np.finfo(float).eps
    if fx != None:
        res = np.zeros(len(ft2d[0]))
        for y in xrange(len(ft2d[0])):
            val = ft2d[fx, y]
            magnitudeSquare = val.imag * val.imag + val.real * val.real
            res[y] = np.log(magnitudeSquare + epsilon)
        return res

    if fy != None:
        res = np.zeros(len(ft2d))
        for x in xrange(len(ft2d)):
            val = ft2d[x, fy]
            magnitudeSquare = val.imag * val.imag + val.real * val.real
            res[x] = np.log(magnitudeSquare + epsilon)
        return res

    res = np.zeros(ft2d.shape)
    for x in xrange(len(ft2d)):
        for y in xrange(len(ft2d[0])):
            val             = ft2d[x, y]
            magnitudeSquare = val.imag * val.imag + val.real * val.real
            res[x, y] = np.log(magnitudeSquare + epsilon)
    return res

    

def display_plot(x, y, title="Plot", xlabel="x", ylabel="y", limits=[]):
    plt.figure()
    if isinstance(y, list):
        for arr in y:
            plt.plot(x, arr)
    else:
        plt.plot(x, y)
    plt.grid()
    plt.xlabel(xlabel)
    plt.ylabel(ylabel)
    if len(limits) > 0:
        plt.axis(limits)
    if SHOW_TITLE:
        plt.title(title)
    if SAVE_IMAGE:
        plt.savefig('../output/' + title.replace(' ', '_') + '.png')
    plt.show()

#--------------------------------------------------- 
# Question 1.9 and 1.10
#--------------------------------------------------- 
def from_value_to_index(input_vector, input_value) :   
#Return the index position in such a way, the vecteur value at this position is the closest to the inputValue 
    temp = np.abs(input_vector - input_value)  
    pos = np.argmin(temp)                   
       
    return pos  


def questions1_6():
    path_wheel = "../resource/wheele.pgm"

# Question 1.1
    img = read_pgm(path_wheel)
    (width, height) = img.shape
    display_image(img)

# Question 1.2
    ft2d    = fft2(img)
    sum     = sum_pixels(img)
    freqSol = solve_discret_equation_FT(ft2d, sum)
    
# Question 1.3

# there is the first version of the answer
    # epsilon         = np.finfo(float).eps
    # axis            = np.array(range(0, width * height))
    # spectrum        = power_spectrum(ft2d)
    # display_plot(axis, spectrum,
    #              xlabel='Fourier domain\'s index (= x + y*width)',
    #              ylabel='log(modulus(ft(x, y))^2)',
    #              limits=[0, width * height, min(spectrum), max(spectrum)])

# here what i think is right (another version)
    spectrum_2D = power_spectrum_2D(ft2d)
    display_image(spectrum_2D, vmin=min(spectrum_2D.flatten()), vmax=max(spectrum_2D.flatten()))

# Question 1.4

# there is the first version of the answer
    # ft2d_shifted = fftshift(ft2d)
    # spectrum_shifted = power_spectrum(ft2d_shifted)
    # axis = np.array(range(- width*height / 2 + 1, width * height / 2 + 1))
    # display_plot(axis, spectrum_shifted,
    #              xlabel='Fourier domain\'s index (= x + y*width)',
    #              ylabel='log(modulus(ft(x, y))^2)',
    #              limits=[- width * height / 2, width * height / 2 + 2, min(spectrum), max(spectrum)])

# here what i think is right (another version)
    ft2d_shifted = fftshift(ft2d)
    spectrum_shifted_2D = power_spectrum_2D(ft2d_shifted)
    img_size = img.shape[0] * img.shape[1]
    display_image(spectrum_shifted_2D,
                  vmin=min(spectrum_shifted_2D.flatten()),
                  vmax=max(spectrum_shifted_2D.flatten()),
                  extent=[- height / 2 + 1, height / 2 + 1, - width / 2 + 1, width / 2 + 1])


# Question 1.6
    img_computed = ifft2(ft2d_shifted)
    img_computed = img_computed.astype(int)
    display_image(img_computed)

if __name__ == "__main__":
    # I moved questions 1.1-1.6 to separate function to work on the rest of questions
    questions1_6()

    path_chess = "../resource/damierHV.pgm"

# Question 1.7
    img = read_pgm(path_chess)
    (width, height) = img.shape
    display_image(img)

# Question 1.8
    ft2d = fft2(img)
    ft2d_shifted = fftshift(ft2d)
    spectrum_shifted_2D = power_spectrum_2D(ft2d_shifted)
    img_size = img.shape[0] * img.shape[1]
    display_image(spectrum_shifted_2D,
                  vmin=min(spectrum_shifted_2D.flatten()),
                  vmax=max(spectrum_shifted_2D.flatten()),
                  extent=[- height / 2 + 1, height / 2 + 1, - width / 2 + 1, width / 2 + 1])
    # print("min = " + str(min(spectrum_shifted_2D.flatten())) + ", max = " + str(max(spectrum_shifted_2D.flatten())))


# from here i don't really understand WTF is going on

# Question 1.9
    spectrum_x_0 = power_spectrum_2D(ft2d_shifted, fx=0)
    spectrum_x_26 = power_spectrum_2D(ft2d_shifted, fx=-26)
    display_plot(range(len(spectrum_x_0)), [spectrum_x_0, spectrum_x_26])

    # for x in range(-40, -10):
    # spectrum_x_24 = power_spectrum_2D(ft2d_shifted, fx=-26)
    # display_plot(range(len(spectrum_x_24)), spectrum_x_24, title="fx = -" + str(x))

    # spectrum_x_25 = power_spectrum_2D(ft2d_shifted, fx=-27)
    # display_plot(range(len(spectrum_x_25)), spectrum_x_25, title="fx = -27")    

# Question 1.10
    spectrum_y_0 = power_spectrum_2D(ft2d_shifted, fy=0)
    display_plot(range(len(spectrum_y_0)), spectrum_y_0)

    # for y in range(-40, -10):
    #     spectrum_y = power_spectrum_2D(ft2d_shifted, fy=-26)
    #     display_plot(range(len(spectrum_y)), spectrum_y, title="fy = -" + str(y))

    # fx = -24,25
    # fy = 11,12







