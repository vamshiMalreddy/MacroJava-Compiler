package visitor;
import java.util.*;

public class usedef{

  public ArrayList<String> use;
  public ArrayList<String> def;
  public ArrayList<Integer> suc;

  public usedef(){
    this.use  = new ArrayList<String>();
    this.def  = new ArrayList<String>();
    this.suc  = new ArrayList<Integer>();
  }
}