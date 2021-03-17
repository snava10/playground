import numpy as np
import cv2
from matplotlib import pyplot as plt


def load_and_display():
    # Load an color image in grayscale
    img = cv2.imread('resources/image1.jpg', 0)
    cv2.imshow('image', img)
    cv2.waitKey(0)
    cv2.destroyAllWindows()


def load_and_display_resize_window():
    img = cv2.imread('resources/image1.jpg', 0)
    cv2.namedWindow('image', cv2.WINDOW_NORMAL)
    cv2.imshow('image', img)
    cv2.waitKey(0)
    cv2.destroyAllWindows()


def load_and_save():
    img = cv2.imread('resources/image1.jpg', 0)
    cv2.namedWindow('image', cv2.WINDOW_NORMAL)
    cv2.imshow('image', img)
    k = cv2.waitKey(0) & 0xFF
    if k == 27:  # wait for ESC key to exit
        cv2.destroyAllWindows()
    elif k == ord('s'):  # wait for 's' key to save and exit
        cv2.imwrite('resources/image1Gray.png', img)
    cv2.destroyAllWindows()


def load_and_display_matplotlib():
    img = cv2.imread('resources/image1.jpg', 0)
    plt.imshow(img, cmap='gray', interpolation='bicubic')
    plt.xticks([]), plt.yticks([])  # to hide tick values on X and Y axis
    plt.show()


# load_and_display_resize_window()
# load_and_save()
load_and_display_matplotlib()
