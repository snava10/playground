import cv2
import numpy as np
from matplotlib import pyplot as plt


def resize():
    img = cv2.imread('resources/messi5.jpg')
    cv2.namedWindow('image', cv2.WINDOW_NORMAL)
    cv2.imshow('image', img)
    cv2.waitKey(0)
    cv2.destroyAllWindows()

    res = cv2.resize(img, None, fx=2, fy=2, interpolation=cv2.INTER_CUBIC)

    cv2.namedWindow('image', cv2.WINDOW_NORMAL)
    cv2.imshow('image', res)
    cv2.waitKey(0)
    cv2.destroyAllWindows()

    # OR

    height, width = img.shape[:2]
    res = cv2.resize(img, (2 * width, 2 * height), interpolation=cv2.INTER_CUBIC)
    cv2.namedWindow('image', cv2.WINDOW_NORMAL)
    cv2.imshow('image', img)
    cv2.waitKey(0)
    cv2.destroyAllWindows()


def translation():
    img = cv2.imread('resources/messi5.jpg', 0)
    rows, cols = img.shape

    m = np.float32([[1, 0, 100], [0, 1, 50]])
    dst = cv2.warpAffine(img, m, (cols, rows))

    cv2.namedWindow('image', cv2.WINDOW_NORMAL)
    cv2.imshow('image', dst)
    cv2.waitKey(0)
    cv2.destroyAllWindows()


def rotation():
    img = cv2.imread('resources/messi5.jpg', 0)
    rows, cols = img.shape

    # Rotate 90 degrees by the center of the image with no scaling
    m = cv2.getRotationMatrix2D((cols / 2, rows / 2), 90, 1)
    dst = cv2.warpAffine(img, m, (cols, rows))
    cv2.namedWindow('image', cv2.WINDOW_NORMAL)
    cv2.imshow('image', dst)
    cv2.waitKey(0)
    cv2.destroyAllWindows()


def affine_transformation():
    img = cv2.imread('drawing.png')
    rows, cols, ch = img.shape

    pts1 = np.float32([[50, 50], [200, 50], [50, 200]])
    pts2 = np.float32([[10, 100], [200, 50], [100, 250]])

    M = cv2.getAffineTransform(pts1, pts2)

    dst = cv2.warpAffine(img, M, (cols, rows))

    plt.subplot(121), plt.imshow(img), plt.title('Input')
    plt.subplot(122), plt.imshow(dst), plt.title('Output')
    plt.show()


def generate_grid_image():
    img = np.ones((500, 500, 1), np.uint8) * 255

    rows, cols, ch = img.shape

    for r in range(0, 500, 50):
        img = cv2.line(img, (0, 0), (511, 511), (255, 0, 0), 5)
        pass

    cv2.namedWindow('image', cv2.WINDOW_NORMAL)
    cv2.imshow('image', img)
    cv2.waitKey(0)
    cv2.destroyAllWindows()


# resize()
# translation()
# rotation()
generate_grid_image()
