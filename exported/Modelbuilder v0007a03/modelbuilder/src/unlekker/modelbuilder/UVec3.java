package unlekker.modelbuilder;import java.io.Serializable;import processing.core.PApplet;import processing.core.PVector;import unlekker.util.*;/** * <p>3D vector class.</p>  * @author <a href="http://workshop.evolutionzone.com/">Marius Watz</a> */public class UVec3 implements UConstants, Serializable, Comparable<UVec3> { static final float DEG=(3.14159265358979323846f/180.0f); public float x,y,z; private static final long serialVersionUID = -7031626891070482159L; public UVec3() { } public UVec3(float x,float y,float z) {this.x=x; this.y=y; this.z=z;} public UVec3(float x,float y) {this.x=x; this.y=y; this.z=0;} public UVec3(UVec3 v) {this.x=v.x; this.y=v.y; this.z=v.z;} public UVec3(double _x, double _y, double _z) {	 x=(float)_x;	 y=(float)_y;	 z=(float)_z; }/**   * Parse Vec3 from a String produced by Vec3.toString,   * i.e. '&lt;##,##,##&gt;' (with no spaces).   * @param str  * @return  */ public static UVec3 parse(String str) {	 if(str.equals("null")) return null;	 UVec3 v=new UVec3();	 	 str=str.substring(1,str.length()-2);//	 UUtil.log("Vec3.parse: '"+str);	 String [] tok=str.split(",");	 v.x=Float.parseFloat(tok[0]);	 v.y=Float.parseFloat(tok[1]);	 v.z=Float.parseFloat(tok[2]);//	 UUtil.log("Vec3.parse: '"+str+"' -> "+v);	 	 return v; } public static UVec3[] parse(String str[]) {	 UVec3 v[]=null;	 v=new UVec3[str.length];//	 UUtil.log("Vec3.parse(String []: '"+UUtil.toString(str));	 for(int i=0; i<str.length; i++) {//		 UUtil.log(i+" "+str[i]);		 v[i]=parse(str[i]);	 }	 	 return v; } public static UVec3[] getVec3(int n) {	 UVec3 v[]=new UVec3[n];	 for(int i=0; i<n; i++) v[i]=new UVec3();	 return v; } public static UVec3 interpolate(UVec3 v1,UVec3 v2,float t) {	 UVec3 v=new UVec3(v2).sub(v1).mult(t).add(v1);	 return v; } public static UVec3 [] copyVertices(UVec3[] v) {		UVec3 [] vc=getVec3(v.length);		for(int i=0; i<vc.length; i++) vc[i].set(v[i]);		return vc;	}  public static UVec3 centroid(UVec3 vv[]) {	 UVec3 c=new UVec3();	 for(int i=0; i<vv.length; i++) c.add(vv[i]);	 return c.div(vv.length);	   }  public static UVec3 [] setVArray(UVec3 src[],UVec3 dest[]) {	 if(dest==null) dest=new UVec3[src.length];	 else if(dest.length<src.length) 		 dest=(UVec3 [])UUtil.expandArray(dest,src.length);	 	 for(int i=0; i<src.length; i++) {		 dest[i]=new UVec3(src[i]);	 }	 return dest; }  public boolean cmp(UVec3 v) {//  System.out.println("v=="+v+" cmp "+toString());  if(v.x-this.x==0 && v.y-this.y==0 && v.z-this.z==0) return true;    float xd=x-v.x;  float yd=y-v.y;  float zd=z-v.z;    if((xd*xd+yd*yd+zd*zd)<0.01f) return true;  return false; } public UVec3 setToAngle(float angle,float rad) {	 set(rad,0,0);	 rotateZ(angle);	 return this; } public UVec3 set(UVec3 v) {  x=v.x; y=v.y; z=v.z;  return this; } public UVec3 set(float tx,float ty,float tz) {  x=tx; y=ty; z=tz;  return this; }  public UVec3 set(float _x, float _y) {		x=_x;		y=_y;		return this;	} public UVec3 clamp(float minx,float maxx,float miny,float maxy,  float minz,float maxz) {  if(x<minx) x=minx; else if(x>maxx) x=maxx;  if(y<miny) y=miny; else if(y>maxy) y=maxy;  if(z<minz) z=minz; else if(z>maxz) z=maxz;  return this; } public void abs() {  x=Math.abs(x);  y=Math.abs(y);  z=Math.abs(z); } public static UVec3 abs(UVec3 v) {  return new UVec3(Math.abs(v.x),Math.abs(v.y),Math.abs(v.z)); } public static float[] toFloatArray(UVec3 vv[]) {	 int vlen=vv.length,id=0;	 float arr[]=new float[vlen*3];	 for(int i=0; i<vlen; i++) {		 arr[id++]=vv[i].x;		 arr[id++]=vv[i].y;		 arr[id++]=vv[i].z;	 }	 	 return arr; }  // return a PVector object representing this UVec3 object public PVector toPVector() {	 return new PVector(x,y,z); }  public String toString() {  String s;  s=new String("<"+UUtil.nf(this.x)+","+UUtil.nf(this.y)+","+UUtil.nf(this.z)+">");  return s; } public String toStringRadiansToDegrees() {	  String s;	  s=new String("<"+UUtil.nf(this.x*DEG_TO_RAD)+","+UUtil.nf(this.y*DEG_TO_RAD)+","+UUtil.nf(this.z*DEG_TO_RAD)+">");	  return s;	 } public String toString(int precision) {  String s;  s=new String("<"+UUtil.nf(this.x)+","+   UUtil.nf(this.y)+","+   UUtil.nf(this.z)+">");  return s; } public UVec3 moveTowards(UVec3 v,float dist) {	 float l=distanceTo(v);	 dist/=l;	 x+=(v.x-x)*dist; 	 y+=(v.y-y)*dist; 	 z+=(v.z-z)*dist; 	 return this; } public UVec3 add(UVec3 v) {x+=v.x; y+=v.y; z+=v.z; return this;} public UVec3 add(UVec3 v[],int n) {	 for(int i=0; i<n; i++) add(v[i]);	 return this; } public UVec3 add(float vx,float vy,float vz) {x+=vx; y+=vy; z+=vz; return this;} public UVec3 add(float n) {x+=n; y+=n; z+=n; return this;} public static UVec3 add(UVec3 v1,UVec3 v2) {  return new UVec3(v1.x+v2.x,v1.y+v2.y,v1.z+v2.z); } public UVec3 add(float x2, float y2) {	 x+=x2;	 y+=y2;	return this;	} public UVec3 sub(UVec3 v) {x-=v.x; y-=v.y; z-=v.z;  return this;} public UVec3 sub(float vx,float vy,float vz) {x-=vx; y-=vy; z-=vz; return this;} public UVec3 sub(float n) {x-=n; y-=n; z-=n; return this;} public static UVec3 sub(UVec3 v1,UVec3 v2) {  return new UVec3(v1.x-v2.x,v1.y-v2.y,v1.z-v2.z); } public UVec3 mult(UVec3 v) {x*=v.x; y*=v.y; z*=v.z; return this;} public UVec3 mult(float vx,float vy,float vz) {x*=vx; y*=vy; z*=vz; return this;} public UVec3 mult(float m) {x*=m; y*=m; z*=m; return this;} public UVec3 div(float m) {x/=m; y/=m; z/=m; return this;} public static UVec3 mult(UVec3 v1,UVec3 v2) {  return new UVec3(v1.x*v2.x,v1.y*v2.y,v1.z*v2.z); } public static UVec3 mult(UVec3 v,float m) {  return new UVec3(v.x*m,v.y*m,v.z*m); } public UVec3 norm() {  float l;  l=length();  if(l>0) {x/=l; y/=l; z/=l;}  return this; } public UVec3 norm(float m) {  float l;  l=length();  if(l>0) {x/=l; y/=l; z/=l;}  x*=m; y*=m; z*=m;  return this; } public UVec3 normMult(float m) {  float l;  l=length();  if(l>0) {x/=l; y/=l; z/=l;}  x*=m; y*=m; z*=m;    return this; } static public UVec3 calcFaceNormal(UVec3 a,UVec3 b,UVec3 c) {	 UVec3 v=UVec3.crossProduct(			b.x-a.x,b.y-a.y,b.z-a.z, 			c.x-a.x,c.y-a.y,c.z-a.z).norm();	 	 return v; }  static public UVec3 crossProduct(UVec3 a,UVec3 b,UVec3 c) {	 a=new UVec3(a);	 a.sub(c);	 b=new UVec3(b);	 b.sub(c);	 	 UVec3 cr=new UVec3(			 a.y*b.z - a.z*b.y,			 a.z*b.x - a.x*b.z,			 a.x*b.y - a.y*b.x);	 return cr; } static public UVec3 crossProduct(		 float ax,float ay,float az,float bx,float by,float bz) {	 	 UVec3 cr=new UVec3(ay*bz-az*by,			 az*bx-ax*bz,			 ax*by-ay*bx);	 return cr; } public UVec3 cross(UVec3 v){   float crossX=y*v.z-v.y*z;   float crossY=z*v.x-v.z*x;   float crossZ=x*v.y-v.x*y;   return(new UVec3(crossX,crossY,crossZ)); } public static UVec3 cross(UVec3 v,UVec3 v2){   float crossX=v.y*v2.z-v2.y*v.z;   float crossY=v.z*v2.x-v2.z*v.x;   float crossZ=v.x*v2.y-v2.x*v.y;   return(new UVec3(crossX,crossY,crossZ)); }  public float dot(UVec3 v) {	 return x*v.x+y*v.y+z*v.z; } public static float dot(UVec3 v,UVec3 v2) {	 return v2.x*v.x+v2.y*v.y+v2.z*v.z; } public static float circleArcLength(float r,float deg) {	 // c=TWO_PI*r	 float val=0;	 	 val=(deg/TWO_PI)*r;	 	 return val; }  public float length() {return (float)Math.sqrt(x*x+y*y+z*z);} public static float length(UVec3 v1) {return (float)Math.sqrt(v1.x*v1.x+v1.y*v1.y+v1.z*v1.z);} public static float length(float x,float y,float z) {return (float)Math.sqrt(x*x+y*y+z*z);} /**  * Return the heading angle of this vector, in radians.   * @param plane Which axis plane the function should calculate the heading for  * @return Angle of heading in radians  */ public final float heading(int plane) {	 if(plane==XYPLANE) return (float) Math.atan2(y, x);	 if(plane==XZPLANE) return (float) Math.atan2(z, x);   return (float)Math.atan2(z, y);}  /**  * Returns the angles of rotation for the orientation of a given vector.  *    * @param v   * @return UVec3 object where <code>x</code> contains the rotation around the Z axis, and  * <code>y</code> contains the rotation around the Y axis. The rotations must be performed in the Z-then-Y order.  */ public static UVec3 getHeadingAngles(UVec3 v) {	 float a, b;	 UVec3 v2=new UVec3(v);	 // Y rotation	 a=(float)Math.atan2(v2.z, v2.x);	 v2.rotateY(-a);	 // Z rotation	 b=(float)Math.atan2(v2.y, v2.x);	 v2.set(b, a, 0);	 return v2; } public float angle2D() {	 return (float)Math.atan2(y, x); } public static float angle2D(float x,float y) {	 return (float)Math.atan2(y, x); }  /**  * Calculate distance between current point and a given point  * @param v  * @return  */ public float distanceTo(UVec3 v) {	  return UVec3.length(v.x-x,v.y-y,v.z-z); } /**  * Calculate distance between two XYZ points  * @param v1  * @param v2  * @return  */ public static float dist(UVec3 v1,UVec3 v2) {  return UVec3.length(v2.x-v1.x,v2.y-v1.y,v2.z-v1.z); } /**  * Calculate distance between two XYZ points  * @param x1  * @param y1  * @param z1  * @param x2  * @param y2  * @param z2  * @return  */ public static float dist(float x1,float y1,float z1, 		 float x2,float y2,float z2) {	  return UVec3.length(x2-x1,y2-y1,z2-z1);	 }  public boolean equals(float tx,float ty,float tz) {  return UVec3.equals(x,y,z,tx,ty,tz); } public boolean equals(UVec3 v) {  return equals(x,y,z,v.x,v.y,v.z); }  public static boolean equals(float x1,float y1,float z1,float x2,float y2,float z2) {  if((int)(x1*10000)==(int)(x2*10000) &&  		(int)(y1*10000)==(int)(y2*10000) &&  		(int)(z1*10000)==(int)(z2*10000)) return true;    return false; } public static boolean equals(UVec3 v1,UVec3 v2) {  return UVec3.equals(v1.x,v1.y,v1.z,v2.x,v2.y,v2.z); } public UVec3 rotateX(float deg) {  double sindeg,cosdeg;  double newy,newz;  sindeg=Math.sin(deg); cosdeg=Math.cos(deg);  newy=y*cosdeg-z*sindeg;  newz=y*sindeg+z*cosdeg;  y=(float)newy;  z=(float)newz;  	return this; } public UVec3 rotateY(float deg) {  double sindeg,cosdeg;  double newx,newz;  sindeg=Math.sin(deg); cosdeg=Math.cos(deg);  newx=x*cosdeg-z*sindeg;  newz=x*sindeg+z*cosdeg;  x=(float)newx;  z=(float)newz;  	return this; } public UVec3 rotate(float deg) {	 return rotateZ(deg); } public UVec3 rotateZ(float deg) {  double sindeg,cosdeg;  double newy,newx;  sindeg=Math.sin(deg); cosdeg=Math.cos(deg);  newx=x*cosdeg-y*sindeg;  newy=x*sindeg+y*cosdeg;  x=(float)newx;  y=(float)newy;  	return this; } /**  * Calculates Cartesian point given a polar coordinate.  * a=0 is at the <0,-1,0> position, a=PI/2 is at <0,0,1>.  * b=0 is at the <-1,0,0> position, b=PI/2 is at <0,0,-1>  * @param a Angle 1  * @param b Angle 2  * @param R Radius  * @return Vec3 object containing the Cartesian point represented by the polar coordinates.  */ public static UVec3 fromPolar(float a,float b,float R) {  double sina,cosa,sinb,cosb;  sina=Math.sin(a); cosa=Math.cos(a);  sinb=Math.sin(b); cosb=Math.cos(b);  return new UVec3((float)(R*sina*sinb),(float)(R*cosa),(float)(R*sina*cosb)); } public static UVec3 fromPolar(UVec3 v) {  return UVec3.fromPolar(v.x,v.y,v.z); }public int compareTo(UVec3 o) {  if((int)(o.x*10000)==(int)(x*10000) &&  		(int)(o.y*10000)==(int)(y*10000) &&  		(int)(o.z*10000)==(int)(z*10000)) return 0;  	return (int)(((o.x-x)+(o.y-y)+(o.z-z))*1000);}}