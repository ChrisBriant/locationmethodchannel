import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
    // Get battery level.
  double _lat = 0.0;
  double _lng = 0.0;
  static const platform = MethodChannel('chrisbriant.uk.dev/location');


  @override
  void initState() {
    super.initState();
    _initializeLocation();
    Timer.periodic(Duration(seconds: 10), (Timer timer) {
      _getLocation();
    });
  }


  Future<void> _getLocation() async {
    String locationPrint;
    try {
      final double _latFromDevice = await platform.invokeMethod('getLatitude');
      final double _lngFromDevice = await platform.invokeMethod('getLongitude');
      locationPrint = 'The coordinates are $_latFromDevice, $_lngFromDevice.';
      print(locationPrint);
      setState(() {
        _lat = _latFromDevice;
        _lng = _lngFromDevice;
      });
    } on PlatformException catch (e) {
      print('Failed to get the location');
    }
    
  }


  Future<void> _initializeLocation() async {
    try {
      print('I am trying to init the locaiton');
      final String result = await platform.invokeMethod('initLocation');
      print(result);
    } catch(err) {
      print(err);
    }
  }

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              'Your location is $_lat, $_lng.',
            ),
          ],
        ),
      ),
    );
  }
}
