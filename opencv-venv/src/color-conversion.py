import cv2
import numpy as np


def track_blue():
    cap = cv2.VideoCapture(0)

    while 1:

        # Take each frame
        _, frame = cap.read()

        # Convert BGR to HSV
        hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

        # define range of blue color in HSV
        lower_blue = np.array([110, 50, 50])
        upper_blue = np.array([130, 255, 255])

        # Threshold the HSV image to get only blue colors
        mask = cv2.inRange(hsv, lower_blue, upper_blue)

        # Bitwise-AND mask and original image
        res = cv2.bitwise_and(frame, frame, mask=mask)

        cv2.imshow('frame', frame)
        cv2.imshow('mask', mask)
        cv2.imshow('res', res)
        k = cv2.waitKey(5) & 0xFF
        if k == 27:
            break

    cv2.destroyAllWindows()


def track_yellow_and_blue():
    cap = cv2.VideoCapture(0)
    while True:
        # Take each frame
        _, frame = cap.read()

        # Convert BGR to HSV
        hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)

        # define range of blue color in HSV
        lower_blue = np.array([110, 50, 50])
        upper_blue = np.array([130, 255, 255])

        lower_red = np.array([0, 100, 100])
        upper_red = np.array([40, 100, 100])

        # Threshold the HSV image to get only blue and yellow colours
        mask = cv2.inRange(hsv, lower_blue, upper_blue)
        mask2 = cv2.inRange(hsv, lower_red, upper_red)
        new_mask = cv2.bitwise_or(mask, mask2)

        # Bitwise-AND mask and original image
        res = cv2.bitwise_and(frame, frame, mask=new_mask)

        cv2.imshow('frame', frame)
        cv2.imshow('mask', new_mask)
        cv2.imshow('res', res)
        k = cv2.waitKey(5) & 0xFF
        if k == 27:
            break

    cv2.destroyAllWindows()


# track_blue()
track_yellow_and_blue()
