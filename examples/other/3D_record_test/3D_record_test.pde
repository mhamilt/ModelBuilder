// sine tests
// by Amanda Ghassaei
// Dec 2012
// https://www.instructables.com/id/3D-Printed-Record/
//-----------------------------------------------------------------------------
/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 */
//-----------------------------------------------------------------------------
import processing.opengl.*;
import ec.util.*;
import unlekker.mb2.util.*;
import unlekker.mb2.externals.*;
import unlekker.mb2.geo.*;
import unlekker.data.*;
//-----------------------------------------------------------------------------
UVertexList recordPerimeterUpper, recordPerimeterLower, recordHoleUpper, recordHoleLower;//storage for perimeter and center hole of record
UVertexList lastEdge; //storage for conecting one groove to the next
UGeo geo;             //storage for stl geometry
//-----------------------------------------------------------------------------
//variables
float theta;               // angle variable
float thetaIter = 1000;    // steps per TWO PI, sampling rate (angular frequency)
float radius;              // variable to calculate radius of grooves
float diameter = 7;        // diameter of record in inches
float innerHole = 0.286;   // diameter of center hole in inches
float innerRad = 2.35;     // radius of innermost groove in inches
float outerRad = 5.75;     // radius of outermost groove in inches
float grooveSpacing = 20;  // pixel spacing of grooves
float bevel = 2;           // pixel width of groove bevel

//-----------------------------------------------------------------------------
//record parameters
float recordHeight = 0.08; // height of record in inches
float recordBottom = 0;    // height of bottom of record
//-----------------------------------------------------------------------------
//parameters to test
float amplitudes[] = {2, 4, 8};     //in units of 16 micron steps (remember this is the amplitude of the sine wave, the total vert displacement will be twice this)
float frequencies[] = {440};  //cycles per rotation
float grooveDepths[] = {0.5, 1};    //how many 16 microns steps below the surface of the record to print the uppermost point of the groove
float grooveWidths[] = {1, 2, 3};   //in 600dpi pixels
float incrNum = TWO_PI / thetaIter; //calculcate inrementation amount
int grooveNum = 0;                  //variable for keeping track of how long this will take
int numberOfCopies = 1;
int totalNumGrooves = amplitudes.length * frequencies.length * grooveDepths.length * grooveWidths.length * numberOfCopies; 
//-----------------------------------------------------------------------------
void setup()
{
  UMB.setPApplet(this);
  geo = new UGeo();//place to store geometery of verticies

  setUpVariables();//convert units, initialize etc
  setUpRecordShape();//draw basic shape of record
  drawGrooves();//draw in grooves

  geo.writeSTL(sketchPath("test.stl"));
}

//-----------------------------------------------------------------------------

void setUpVariables()
{
  //convert everything to inches
  float micronsPerInch = 25400;//scalingfactor
  float dpi = 600;
  float micronsPerLayer = 16;//microns per vertical print layer

  grooveSpacing /= dpi;
  bevel /= dpi;
  for (byte i = 0; i < amplitudes.length - 1; i++)
  {
    amplitudes  [i]  = amplitudes[i]  * micronsPerLayer/micronsPerInch;
    grooveDepths[i]  = grooveDepths[i]* micronsPerLayer/micronsPerInch;
    grooveWidths[i] /= dpi;
  }
  int i = 2;
  amplitudes  [i]  = amplitudes[i] * micronsPerLayer / micronsPerInch;  
  grooveWidths[i] /= dpi;
}

//-----------------------------------------------------------------------------

void setUpRecordShape()
{
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

//-----------------------------------------------------------------------------

void drawGrooves()
{

  UVertexList grooveOuterUpper, grooveOuterLower, grooveInnerUpper, grooveInnerLower;//groove verticies

  //set up storage
  grooveOuterUpper = new UVertexList();
  grooveOuterLower = new UVertexList();
  grooveInnerUpper = new UVertexList();
  grooveInnerLower = new UVertexList();

  //DRAW GROOVES
  radius = outerRad;//outermost radius (at 5.75") to start

  for (float amplitude : amplitudes)
  {
    for (float frequency : frequencies)
    {
      for (float grooveDepth : grooveDepths)
      {
        for (float grooveWidth : grooveWidths)
        {
          for (byte copies=0; copies < numberOfCopies; copies++)
          {
            //clear lists
            grooveOuterUpper.clear();
            grooveOuterLower.clear();
            grooveInnerUpper.clear();
            grooveInnerLower.clear();

            for (theta=0; theta<TWO_PI; theta+=incrNum)//for theta between 0 and 2pi
            {

              float sineTheta = sin(theta);
              float cosineTheta = cos(theta);

              //calculate height of groove
              float grooveHeight = recordHeight - grooveDepth - amplitude + (amplitude * sin(theta * frequency));

              grooveOuterUpper.add((diameter/2 + (radius + bevel) * cosineTheta),
                (diameter/2+(radius+bevel)*sineTheta), recordHeight);
              grooveOuterLower.add((diameter/2 + radius*cosineTheta), (diameter/2+radius*sineTheta), grooveHeight);
              grooveInnerLower.add((diameter/2 + (radius-grooveWidth) * cosineTheta), (diameter/2+(radius-grooveWidth)*sineTheta), grooveHeight);
              grooveInnerUpper.add((diameter/2 + (radius-grooveWidth - bevel) * cosineTheta), (diameter/2+(radius - grooveWidth - bevel)*sineTheta), recordHeight);
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

            radius -= grooveSpacing+grooveWidth;//set next radius

            //tell me how much longer
            grooveNum++;
            print(grooveNum);
            print(" of ");
            print(totalNumGrooves); 
            println(" grooves drawn");
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
