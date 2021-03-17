import cv2
import numpy as np


def print_all_events():
    events = [i for i in dir(cv2) if 'EVENT' in i]
    print(events)


# mouse callback function
def draw_circle_handler(event, x, y, flags, param):
    if event == cv2.EVENT_LBUTTONDBLCLK:
        cv2.circle(img, (x, y), 100, (255, 0, 0), -1)


def draw_circle_on_double_click():
    # Create a black image, a window and bind the function to window
    cv2.namedWindow('image')
    cv2.setMouseCallback('image', draw_circle_handler)

    while True:
        cv2.imshow('image', img)
        if cv2.waitKey(20) & 0xFF == 27:
            break
    cv2.destroyAllWindows()


drawing = False  # true if mouse is pressed
mode = True  # if True, draw rectangle. Press 'm' to toggle to curve
ix, iy = -1, -1


def drawing_application():
    # mouse callback function
    def draw_circle(event, x, y, flags, param):
        global ix, iy, drawing, mode

        if event == cv2.EVENT_LBUTTONDOWN:
            drawing = True
            ix, iy = x, y

        elif event == cv2.EVENT_MOUSEMOVE:
            if drawing:
                if mode:
                    pass
                    # cv2.rectangle(img, (ix, iy), (x, y), (0, 255, 0), 3)
                else:
                    cv2.circle(img, (x, y), 5, (0, 0, 255), -1)

        elif event == cv2.EVENT_LBUTTONUP:
            drawing = False
            if mode:
                cv2.rectangle(img, (ix, iy), (x, y), (0, 255, 0), 3)
            else:
                cv2.circle(img, (x, y), 5, (0, 0, 255), -1)

    img = np.zeros((512, 512, 3), np.uint8)
    cv2.namedWindow('image')
    cv2.setMouseCallback('image', draw_circle)

    while True:
        cv2.imshow('image', img)
        k = cv2.waitKey(1) & 0xFF
        if k == ord('m'):
            mode = not mode
        elif k == 27:
            break

    cv2.destroyAllWindows()


img = np.zeros((512, 512, 3), np.uint8)
# print_all_events()
# draw_circle_on_double_click()
drawing_application()
