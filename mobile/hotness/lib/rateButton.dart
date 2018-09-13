import 'package:flutter/material.dart';

class RateButton extends StatelessWidget {
  int number;

  RateButton(this.number);

//  final GestureTapCallback onPressed;

  @override
  Widget build(BuildContext context) {
    return RawMaterialButton(
      onPressed: null,
      child: Text(this.number.toString()),
      fillColor: Colors.deepOrange,
      splashColor: Colors.orange,
      shape: CircleBorder(),
    );
  }
}
