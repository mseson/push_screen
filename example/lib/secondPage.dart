import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class secondPage extends StatefulWidget{
  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return secondPageState();
  }
}

class secondPageState extends State<secondPage>{
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('Running on: second page'),
        ),
      ),
    );
  }

}