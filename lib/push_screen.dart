import 'dart:async';

import 'package:flutter/services.dart';

class PushScreen {
  static const MethodChannel _channel =
      const MethodChannel('push_screen');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

//  static  Function startScreen(String project,String cardInfo,String vrModel,String roomId,int terminal,String projectAccount,String vrTitle,String clientId) {
//    _channel.invokeMethod('startScreen',{'vrModel': vrModel,'roomId': roomId,'terminal': terminal,'projectAccount': projectAccount,'vrTitle': vrTitle,'clientId': clientId});
//  }
  static  Function startScreen() {
    _channel.invokeMethod('startScreen');
  }

  static  Function finishScreen() {
    _channel.invokeMethod('finishScreen');
  }
}
