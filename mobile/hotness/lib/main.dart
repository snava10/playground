import 'package:flutter/material.dart';
import 'package:hotness/service.dart';
import 'package:hotness/user.dart';
import 'rating.dart';

void main() => runApp(new MyApp());

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: 'Flutter Demo',
      theme: new ThemeData(
        primarySwatch: Colors.red,
      ),
      home: new MyHomePage(title: 'Hotness'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => new _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  final _scaffoldKey = GlobalKey<ScaffoldState>();
  final _service = Service();
  User _currentUser;

  _MyHomePageState() {
    _currentUser = _service.getNextUser();
  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
      key: _scaffoldKey,
      appBar: new AppBar(
        leading: new IconButton(
            icon: const Icon(Icons.person),
            color: Colors.white,
            onPressed: _messagePushed),
        centerTitle: true,
        title: new Text(widget.title),
        actions: <Widget>[
          new IconButton(
              icon: const Icon(Icons.message),
              color: Colors.white,
              onPressed: _messagePushed)
        ],
      ),
      body: _buildMainPage(),
      bottomSheet: Rating(_onRatingChange),
    );
  }

  Widget _buildMainPage() {
    return ListView(children: _createListTiles());
  }

  void _messagePushed() {}

  void _onRatingChange(int rating) {
    print('Rating changed to $rating');
    setState(() {
      _currentUser = _service.getNextUser();
    });
  }

  List<ListTile> _createListTiles() {
    return _currentUser.imageUrls.map((s) => ListTile(
          contentPadding: EdgeInsets.symmetric(vertical: 3.0, horizontal: 10.0),
          title: GestureDetector(
            child: Image.network(s),
            onTap: () {
              print("The user tapped!");
            },
          ),
        )).toList();
  }
}
