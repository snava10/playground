import 'package:flutter/material.dart';

// ignore: must_be_immutable
class Rating extends StatefulWidget {
  Function onRatingChange;

  Rating(this.onRatingChange);

  @override
  _RatingState createState() => _RatingState(onRatingChange);
}

class _RatingState extends State<Rating> {
  var _sliderValue = 1.0;
  Function onRatingChange;

  _RatingState(this.onRatingChange);

  @override
  Widget build(BuildContext context) {
    return Container(
      height: 50.0,
      child: Slider(
        value: _sliderValue,
        min: 1.0,
        max: 10.0,
        divisions: 10,
        onChanged: onSliderChange,
        onChangeEnd: onChangeEnd,
        key: GlobalKey(debugLabel: "slider1"),
        label: '${_sliderValue.round()}',
      ),
    );
  }

  void onSliderChange(double value) {
    setState(() {
      _sliderValue = value;
    });
  }

  void onChangeEnd(double value) {
    print('On change end');
    onRatingChange(_sliderValue.round());
  }
}
