import 'package:hotness/user.dart';

class Service {
  static var kira = [
    'https://vignette.wikia.nocookie.net/starwars/images/1/11/Keiraknightley.jpg',
    'https://vignette.wikia.nocookie.net/pirates/images/4/4d/Keira_knightley.jpg'
  ];

  static var jennifer = [
    'http://pulitikapilipinas.altervista.org/wp-content/uploads/2018/03/jennifer-lawrence.jpg',
    'https://cdn2.stylecraze.com/wp-content/uploads/2018/06/Jennifer-Lawrences-Diet-And-Workout-Plan-For-Weight-Loss-And-A-Toned-Body.jpg',
  ];

  final _users = [User(kira), User(jennifer)];
  var _currentUserId = 0;

  User getNextUser() {
    if (_currentUserId == _users.length) _currentUserId = 0;
    return _users[_currentUserId++];
  }
}
