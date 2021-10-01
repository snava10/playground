import cv2 as cv
import numpy as np
from matplotlib import pyplot as plt

img = cv.imread('./src/resources/opencv_logo.jpg')
blur = cv.blur(img,(5,5))

plt.subplot(321),plt.imshow(img),plt.title('Original')
plt.subplot(322),plt.imshow(blur),plt.title('Blurred')

gaussian = cv.GaussianBlur(img,(5,5),0)
plt.subplot(323),plt.imshow(gaussian),plt.title('Gaussian')

median = cv.medianBlur(img,5)
plt.subplot(324),plt.imshow(median),plt.title('Median')

bilateral = cv.bilateralFilter(img,9,75,75)
plt.subplot(325),plt.imshow(bilateral),plt.title('Bilateral')

plt.show()