//
//  RNRoam.h
//  RoamApp
//
//  Created by GeoSpark on 11/11/22.
//

#import <Foundation/Foundation.h>

#if __has_include("RCTBridgeModule.h")
#import "RCTBridgeModule.h"
#else
#import <React/RCTBridgeModule.h>
#endif
#import <React/RCTEventEmitter.h>

@interface RNRoam : RCTEventEmitter <RCTBridgeModule>

@end
