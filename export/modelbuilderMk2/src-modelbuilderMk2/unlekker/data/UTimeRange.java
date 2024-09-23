package unlekker.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import unlekker.mb2.util.UMB;

public class UTimeRange extends UMB {
  private UTime start,end;
  
  ArrayList<UTime> dayList;
  
  public UTimeRange() {
    start=new UTime().set(0);
    end=new UTime().set(0);    
  }


  public UTimeRange add(long t) {
    if(start.get()==0 || start.get()>t) start.set(t);
    if(end.get()<t) end.set(t);
    return this;
  }

  public UTimeRange add(UTime t) {
    return add(t.get());
  }
  
  
  public ArrayList<UTime> days() {
    ArrayList<UTime> d=new ArrayList<UTime>();
    
    DayIterator it=new DayIterator();
    
    String s="";
    for(UTime tmp : it) {
      d.add(tmp);
    }
    
    return d;
  }
  
  public int countDays() {
    int cnt=0;
    
    DayIterator it=new DayIterator();
    
    String s="";
    for(UTime tmp : it) {
      cnt++;
      s+=tmp.strDate()+" ";
    }
    log(s);
    
    return cnt;
  }
  
  public String str() {
    String s=strf("%s - %s",
        UTime.datetimeF.format(new Date(start.get())),
        UTime.datetimeF.format(new Date(end.get())));
    return s;
  }

  public UTimeRange set(long start,long end) {
    this.start=new UTime().set(start);
    this.end=new UTime().set(end);
    return this;
  }

  public boolean inRange(long t) {
    return (t>=start.get() && t<=end.get());
  }

  public boolean inRange(UTime t) {
    return inRange(t.get());
  }
  
  public float mapToRange(long t) {
    double D=start.get()-end.get();
    t-=start.get();
    
    return (float)(t/D);
  }
  
  public float mapToRange(UTime t) {
    return mapToRange(t.get());
  }

  class DayIterator implements Iterator<UTime>,Iterable<UTime> {
    UTime now,stop;

    public DayIterator() {
      now=new UTime(start).setStartOfDay();
      stop=new UTime(end).setEndOfDay();
    }
    
    @Override
    public boolean hasNext() {
      if(now.before(stop)) return true;
      return false;
    }

    @Override
    public UTime next() {
      now.addDay(1);
      return now.copy();
    }

    @Override
    public void remove() {
      // TODO Auto-generated method stub
      
    }

    @Override
    public Iterator<UTime> iterator() {
      // TODO Auto-generated method stub
      return this;
    }
    
  }
}