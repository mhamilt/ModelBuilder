//sine tests
//by Amanda Ghassaei
//Dec 2012
//https://www.instructables.com/id/3D-Printed-Record/

/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 */

import processing.opengl.*;
import ec.util.*;
import unlekker.mb2.util.*;
import unlekker.mb2.externals.*;
import unlekker.mb2.geo.*;
import unlekker.data.*;
//import poly2Tri.*;

UVertexList recordPerimeterUpper, recordPerimeterLower, recordHoleUpper, recordHoleLower;//storage for perimeter and center hole of record
UVertexList lastEdge;//storage for conecting one groove to the next
UGeo geo;//storage for stl geometry

//variables
float theta;//angle variable
float thetaIter = 10000;//how many values of theta per cycle
float radius;//variable to calculate radius of grooves
int diameter = 12;//diameter of record in inches
float innerHole = 0.286;//diameter of center hole in inches
float innerRad = 2.35;//radius of innermost groove in inches
float outerRad = 5.75;//radius of outermost groove in inches
float grooveSpacing = 20;//pixel spacing of grooves
float bevel = 2;//pixel width of groove bevel

//record parameters
float recordHeight = 0.08;//height of record in inches
int recordBottom = 0;//height of bottom of record

//parameters to test
float amplitude[] = {2, 4, 8};//in units of 16 micron steps (remember this is the amplitude of the sine wave, the total vert displacement will be twice this)
int frequency[] = {1000, 500, 0};//cycles per rotation
float depth[] = {0.5, 1, 0};//how many 16 microns steps below the surface of the record to print the uppermost point of the groove
float grooveWidth[] = {1, 2, 3};//in 600dpi pixels

float incrNum = TWO_PI/thetaIter;//calculcate inrementation amount

int grooveNum = 0;//variable for keeping track of how long this will take

void setup() {//everything that executes in this sketch is contained in the setup()

  geo = new UGeo();//place to store geometery of verticies

  setUpVariables();//convert units, initialize etc
  setUpRecordShape();//draw basic shape of record
  drawGrooves();//draw in grooves

  geo.writeSTL("test.stl");//write stl file from geomtery
}

void setUpVariables() {

  //convert everything to inches
  float micronsPerInch = 25400;//scalingfactor
  float dpi = 600;
  byte micronsPerLayer = 16;//microns per vertical print layer

  grooveSpacing /= dpi;
  bevel /= dpi;
  for (byte i=0; i<3; i++) {
    amplitude[i] = amplitude[i]*micronsPerLayer/micronsPerInch;
    depth[i] = depth[i]*micronsPerLayer/micronsPerInch;
    grooveWidth[i] /= dpi;
  }
}

void setUpRecordShape() {

  //set up storage
  recordPerimeterUpper = new UVertexList();
  recordPerimeterLower = new UVertexList();
  recordHoleUpper = new UVertexList();
  recordHoleLower = new UVertexList();

  //get verticies
  for (theta=0; theta<TWO_PI; theta+=incrNum) {
    //outer edge of record
    float perimeterX = diameter/2+diameter/2*cos(theta);
    float perimeterY = diameter/2+diameter/2*sin(theta);
    recordPerimeterUpper.add(perimeterX, perimeterY, recordHeight);
    recordPerimeterLower.add(perimeterX, perimeterY, recordBottom);
    //center hole
    float centerHoleX = diameter/2+innerHole/2*cos(theta);
    float centerHoleY = diameter/2+innerHole/2*sin(theta);
    recordHoleUpper.add(centerHoleX, centerHoleY, recordHeight);
    recordHoleLower.add(centerHoleX, centerHoleY, recordBottom);
  }

  //close vertex lists (closed loops)
  recordPerimeterUpper.close();
  recordPerimeterLower.close();
  recordHoleUpper.close();
  recordHoleLower.close();

  //connect verticies
  geo.quadstrip(recordHoleUpper, recordHoleLower);
  geo.quadstrip(recordHoleLower, recordPerimeterLower);
  geo.quadstrip(recordPerimeterLower, recordPerimeterUpper);

  //to start, outer edge of record is the last egde we need to connect to with the outmost groove
  lastEdge = new UVertexList();
  lastEdge.add(recordPerimeterUpper);

  println("record drawn, starting grooves");
  grooveNum = 0;//variable for keeping track of how much longer this will take
}

void drawGrooves() {

  UVertexList grooveOuterUpper, grooveOuterLower, grooveInnerUpper, grooveInnerLower;//groove verticies

  //set up storage
  grooveOuterUpper = new UVertexList();
  grooveOuterLower = new UVertexList();
  grooveInnerUpper = new UVertexList();
  grooveInnerLower = new UVertexList();

  //DRAW GROOVES
  radius = outerRad;//outermost radius (at 5.75") to start
  for (byte frequencyIndex=0; frequencyIndex<2; frequencyIndex++) {
    for (byte amplitudeIndex=0; amplitudeIndex<3; amplitudeIndex++) {
      for (byte grooveDepthIndex=0; grooveDepthIndex<2; grooveDepthIndex++) {
        for (byte grooveWidthIndex=0; grooveWidthIndex<3; grooveWidthIndex++) {
          for (byte copies=0; copies<2; copies++) {

            //clear lists
            grooveOuterUpper.clear();
            grooveOuterLower.clear();
            grooveInnerUpper.clear();
            grooveInnerLower.clear();

            for (theta=0; theta<TWO_PI; theta+=incrNum) {//for theta between 0 and 2pi

              float sineTheta = sin(theta);
              float cosineTheta = cos(theta);

              //calculate height of groove
              float grooveHeight = recordHeight-depth[grooveDepthIndex]-amplitude[amplitudeIndex]+amplitude[amplitudeIndex]*sin(theta*frequency[frequencyIndex]);

              grooveOuterUpper.add((diameter/2+(radius+bevel)*cosineTheta), (diameter/2+(radius+bevel)*sineTheta), recordHeight);
              grooveOuterLower.add((diameter/2+radius*cosineTheta), (diameter/2+radius*sineTheta), grooveHeight);
              grooveInnerLower.add((diameter/2+(radius-grooveWidth[grooveWidthIndex])*cosineTheta), (diameter/2+(radius-grooveWidth[grooveWidthIndex])*sineTheta), grooveHeight);
              grooveInnerUpper.add((diameter/2+(radius-grooveWidth[grooveWidthIndex]-bevel)*cosineTheta), (diameter/2+(radius-grooveWidth[grooveWidthIndex]-bevel)*sineTheta), recordHeight);
            }

            //close vertex lists (closed loops)
            grooveOuterUpper.close();
            grooveOuterLower.close();
            grooveInnerUpper.close();
            grooveInnerLower.close();

            //connect verticies
            geo.quadstrip(lastEdge, grooveOuterUpper);
            geo.quadstrip(grooveOuterUpper, grooveOuterLower);
            geo.quadstrip(grooveOuterLower, grooveInnerLower);
            geo.quadstrip(grooveInnerLower, grooveInnerUpper);

            //set new last edge
            lastEdge.clear();//clear old data
            lastEdge.add(grooveInnerUpper);

            radius -= grooveSpacing+grooveWidth[grooveWidthIndex];//set next radius

            //tell me how much longer
            grooveNum++;
            print(grooveNum);
            println(" of 72 grooves drawn");
          }
          radius -= 2*grooveSpacing;//extra spacing
        }
        radius -= 2*grooveSpacing;//extra spacing
      }
      radius -= 2*grooveSpacing;//extra spacing
    }
    radius -= 2*grooveSpacing;//extra spacing
  }

  geo.quadstrip(lastEdge, recordHoleUpper);//close remaining space between last groove and center hole
}
