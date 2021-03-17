import numpy as np
import cv2
from matplotlib import pyplot as plt


def img_show(img):
    plt.imshow(img, cmap='gray', interpolation='bicubic')
    plt.xticks([]), plt.yticks([])  # to hide tick values on X and Y axis
    plt.show()


def draw_shape(shape, img=None):
    img = np.zeros((512, 512, 3), np.uint8) if img is None else img
    if shape == 'line':
        # Draw a diagonal blue line with thickness of 5 px
        img = cv2.line(img, (0, 0), (511, 511), (255, 0, 0), 5)
    elif shape == 'rectangle':
        img = cv2.rectangle(img, (384, 0), (510, 128), (0, 255, 0), 3)
    elif shape == 'circle':
        img = cv2.circle(img, (447, 63), 63, (0, 0, 255), -1)
    elif shape == 'ellipse':
        img = cv2.ellipse(img, (256, 256), (100, 50), 0, 0, 180, 255, -1)
    elif shape == 'polygon':
        pts = np.array([[10, 5], [20, 30], [70, 20], [50, 10]], np.int32)
        pts = pts.reshape((-1, 1, 2))
        img = cv2.polylines(img, [pts], True, (0, 255, 255))
    elif shape == 'text':
        font = cv2.FONT_HERSHEY_SIMPLEX
        cv2.putText(img, 'OpenCV', (10, 500), font, 4, (255, 255, 255), 2, cv2.LINE_AA)

    img_show(img)


image = np.zeros((512, 512, 3), np.uint8)
draw_shape('line', image)
draw_shape('rectangle', image)
draw_shape('circle', image)
draw_shape('ellipse', image)
draw_shape('polygon', image)
draw_shape('text', image)
