import numpy as np
import matplotlib.pyplot as plt
import tensorflow as tf
from tensorflow import keras


def ex_to_int(ex):
    int(''.join(str(ord(c)) for c in ex))


def read_labels(filename):
    arr = open(filename, 'rb').read()
    train_labels = []
    for i in range(8, len(arr)):
        train_labels.append(int(str(arr[i])))
    return np.array(train_labels)


def read_images(filename):
    arr = open(filename, 'rb').read()
    images = []
    pixels = 28 * 28
    for i in range(16, len(arr), pixels):
        images.append(np.array(list(map(lambda x: int(str(x)), arr[i:i + pixels]))).reshape(28, 28))

    return np.array(images)


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

    # plot(train_images[0])
    return train_labels, train_images, test_labels, test_images


def create_and_train_model(train_labels, train_images):
    train_images = train_images / 255.0

    model = keras.Sequential([
        keras.layers.Flatten(input_shape=(28, 28)),
        keras.layers.Dense(100, activation=tf.nn.relu),
        keras.layers.Dense(10, activation=tf.nn.softmax)
    ])

    model.compile(optimizer=tf.train.AdamOptimizer(),
                  loss='sparse_categorical_crossentropy',
                  metrics=['accuracy'])

    model.fit(train_images, train_labels, epochs=5)

    return model


def evaluate(model, test_labels, test_images):
    test_images = test_images / 255.0
    test_loss, test_acc = model.evaluate(test_images, test_labels)
    print('Test accuracy:', test_acc)


def predict_and_generate_result_file(model, test_images):
    predictions = model.predict(test_images)
    predicted_labels = list(map(np.argmax, predictions))
    f = open('predictions.csv', 'w')
    f.write('ImageId,Label\n')
    for i in range(len(predicted_labels)):
        s = str(i + 1) + ',' + str(predicted_labels[i]) + '\n'
        f.write(s)
    f.close()


def create_result_file(predicted_labels):
    f = open('submission.csv', 'w')
    f.write('ImageId,Label\n')
    for i in range(len(predicted_labels)):
        s = str(i + 1) + ',' + str(predicted_labels[i]) + '\n'
        f.write(s)
    f.close()


def predict_kaggle(model):
    f = open('dataset/all/test.csv')
    f.readline()
    predicted_labels = []
    for i in range(28000):
        image = list(map(int, f.readline().split(',')))
        if not image:
            break
        image = np.array(image).reshape(28, 28)
        image = image / 255
        # plot(image)
        predicted_labels.append(np.argmax(model.predict(np.array([image]))))
        # print(predicted_labels[-1])
    create_result_file(predicted_labels)


if __name__ == '__main__':
    print("Reading dataset")
    train_labels, train_images, test_labels, test_images = read_mnist()
    print("Training")
    model = create_and_train_model(train_labels, train_images)
    print("Evaluating")
    evaluate(model, test_labels, test_images)
    # predict_and_generate_result_file(model, test_images)
    predict_kaggle(model)
