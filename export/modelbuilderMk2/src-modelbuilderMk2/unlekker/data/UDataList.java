package unlekker.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import unlekker.mb2.util.UMB;


// - calc start/end
// - get in interval
// - get tagged
// - get float range
// - normalize float range
// - get list of 

public class UDataList<T extends UDataPoint> extends UMB implements Iterable<T> {
  private ArrayList<T> data;
  public String name="none";
  public String description="No description";
  
  
  public UDataList() {
    data=new ArrayList<T>();
  }
  
  public UDataList(String name) {
    this();
    this.name=name;
  }

  public UDataList(String name,String description) {
    this(name);
    this.description=description;
  }

  public UDataList<T> add(T p) {
    data.add(p);
    return this;
  }
  
  
  ////////////// NUMERIC ANALYSIS OF LISTS OF VALUES

  public ArrayList<Float> normalizeFloatList(ArrayList<Float> val,boolean maxOnly) {
    float bb[]=analyzeFloatList(val);
    
    if(maxOnly) {
      float m=1f/bb[1];
      
      for(int i=0; i<val.size(); i++) {
        float f=val.get(i);
        val.set(i,f*m);
      }
    }
    else {
      float m=1f/(bb[1]-bb[0]);
      for(int i=0; i<val.size(); i++) {
        float f=val.get(i)-bb[0]; // subtract min
        val.set(i,f*m);
      }
    }
    
    return val;
  }
  
  public UDataList<T> normalizeFloatList(String key,boolean maxOnly) {    
    ArrayList<Float> fl=getFloatList(key);    
    fl=normalizeFloatList(fl, maxOnly);
    
    return this;
  }
  
  public float[] analyzeFloatList(String key) {  
    ArrayList<Float> fl=getFloatList(key);    
    return analyzeFloatList(fl);
  }

  public float[] analyzeFloatList(ArrayList<Float> val) {
    float[] bb=new float[] {
        Float.MAX_VALUE,
        Float.MIN_VALUE,
        0,0
    };
    
    ArrayList<Float> fl=new ArrayList<Float>();
    fl.addAll(val);
    Collections.sort(fl);
    
    for(float tmp : fl) {
      bb[0]=(tmp < bb[0] ? tmp : bb[0]); // min
      bb[1]=(tmp > bb[1] ? tmp : bb[1]); // max
      bb[2]+=tmp; // avg
    }
    
    bb[2]=bb[2]/(float)fl.size();
    bb[3]=fl.get(fl.size()/2); // median
    
    return bb;
  }
  
  ////////////// GET PRIMITIVES AS LISTS
  
  
  /**
   * Returns ArrayList<Float> of Float  values stored in the UDataPoints in 
   * the list under the key "key". Note that this is a read-only operation, 
   * modifications to the resulting ArrayList will not apply to the UDataPoint 
   * instances.
   * @param key
   * @return
   */
  public ArrayList<Float> getFloatList(String key) {
    ArrayList<Float> val=new ArrayList<Float>();
    
    for(T tmp : data) {
      val.add(tmp.getFloat(key));
    }
    
    return val;
  }

  public UDataList<T> setFloatList(String key,ArrayList<Float> val) {    
    for(int i=0; i<val.size(); i++) {
      T pt=data.get(i);
      pt.addFloat(key, val.get(i));
    }
    
    return this;
  }

  /**
   * Returns ArrayList<String> of String values stored in the UDataPoints in 
   * the list under the key "key". Note that this is a read-only operation, 
   * modifications to the resulting ArrayList will not apply to the UDataPoint 
   * instances.
   * @param key
   * @return
   */
  public ArrayList<String> getStringList(String key) {
    ArrayList<String> val=new ArrayList<String>();
    
    for(T tmp : data) {
      val.add(tmp.getString(key));
    }
    
    return val;
  }

  /**
   * Returns ArrayList<Object> of Object values stored in the UDataPoints in 
   * the list under the key "key". Unlike <code>getFloatList()</code> and 
   * <code>getStringList()</code>, object references in the resulting ArrayList 
   * point to the original object instances. Any modification will therefore also
   * apply to the objects stored in the UDataList.
   * @param key
   * @return
   */

  public ArrayList<Object> getObjectList(String key) {
    ArrayList<Object> val=new ArrayList<Object>();
    
    for(T tmp : data) {
      val.add(tmp.getObject(key));
    }
    
    return val;
  }
  
  

  public int size() {
    return data.size();
  }
  
  public UTimeRange getTimeRange() {
    UTime[] t=new UTime[2];
    
    long t1=Long.MAX_VALUE,t2=Long.MIN_VALUE;
    
    for(T tmp : data) {
      long theT=tmp.timeMin().get();
      t1=(theT < t1 ? theT : t1);
      t2=(theT > t2 ? theT : t2);
      
      theT=tmp.timeMax().get();
      t1=(theT < t1 ? theT : t1);
      t2=(theT > t2 ? theT : t2);
    }
    
    t[0]=new UTime(t1);
    t[1]=new UTime(t2);
    
    UTimeRange r=new UTimeRange().set(t1, t2);
    
    return r;
  }
  
  public ArrayList<T> get() {
    return data;
  }

  public ArrayList<T> getInRange(UTimeRange timeRange) {
    ArrayList<T> l=new ArrayList<T>();
    for(T tmp:data) {
      if(timeRange.inRange(tmp.time())) l.add(tmp);
    }
    return data;
  }

  
  public boolean nameEquals(String listname) {
    return this.name.compareTo(name)==0;
  }

  public Iterator<T> iterator() {
    // TODO Auto-generated method stub
    return data.iterator();
  }

  public String str() {
    return strf("%s\t%d",
        name,size());
  }

  
  
}
