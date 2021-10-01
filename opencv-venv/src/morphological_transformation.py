import cv2 as cv
import numpy as np
from matplotlib import pyplot as plt

img = cv.imread('./src/resources/j.png',0)
kernel = np.ones((5,5),np.uint8)

plt.subplot(321),plt.imshow(img),plt.title('Original')

erosion = cv.erode(img,kernel,iterations = 1)
plt.subplot(322),plt.imshow(erosion),plt.title('Erosion')

dilation = cv.dilate(img,kernel,iterations = 1)
plt.subplot(323),plt.imshow(dilation),plt.title('Dilation')

img = cv.imread('./src/resources/opening.png',0)
opening = cv.morphologyEx(img, cv.MORPH_OPEN, kernel)
plt.subplot(324),plt.imshow(opening),plt.title('Opening')

plt.show()
