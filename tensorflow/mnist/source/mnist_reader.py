import numpy as np
import matplotlib.pyplot as plt


def ex_to_int(ex):
    int(''.join(str(ord(c)) for c in ex))


def read_labels(filename):
    arr = open(filename, 'rb').read()
    train_labels = []
    for i in range(8, len(arr)):
        train_labels.append(int(str(arr[i])))
    return train_labels


def read_images(filename):
    arr = open(filename, 'rb').read()
    images = []
    pixels = 28 * 28
    for i in range(16, len(arr), pixels):
        images.append(np.array(list(map(lambda x: int(str(x)), arr[i:i + pixels]))).reshape(28, 28))

    return images


def plot(image):
    plt.figure()
    plt.imshow(image)
    plt.colorbar()
    plt.gca().grid(False)
    plt.show()


def read_mnist():
    train_labels_file = 'dataset/train-labels'
    test_labels_file = 'dataset/test-labels'

    train_images_filename = 'dataset/train-images'
    test_images_filename = 'dataset/test-images'

    train_labels = np.array(read_labels(train_labels_file))
    test_labels = np.array(read_labels(test_labels_file))

    train_images = read_images(train_images_filename)
    test_images = read_images(test_images_filename)

    print(len(train_labels), len(test_labels), len(train_images), len(test_images))

    plot(train_images[0])
    return train_labels


if __name__ == '__main__':
    tl = read_mnist()
    # print(tl[0:30])
