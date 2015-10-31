/**
 *  Set Nest Presence
 *
 *  Copyright 2015 Jeff Ward
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Set Nest Presence",
    namespace: "jeffward",
    author: "Jeff Ward",
    description: "Make sure that the Nest presence is synced with SmartThings.  This is for a Nest that may be in a part of the house that is low traffic.  It will set present on the following events when motion detected by any of the SmartThings motion detectors.",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section("When motion is detected on any of these motion detectors:") {
		input "motions", "capability.motionSensor", required: true,  title: "Motion Here", multiple: true 
	}
    section("Set this Nest to 'Home':") {
		input "thenest", "capability.thermostat", title: "Nest", required: true 
	}
}

def installed() {
	log.debug "Installed with settings: ${settings}";

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}";

	unsubscribe();
	initialize();
}

def initialize() {
	
    subscribe(motions, "motion.active", onMotionDetected);
    
}

def onMotionDetected(evt) {
   
   log.debug "Motion detected on a motion sensor, setting Nest to 'Home'";
   thenest.present();
   log.debug "Nest set to present"; 
    
}