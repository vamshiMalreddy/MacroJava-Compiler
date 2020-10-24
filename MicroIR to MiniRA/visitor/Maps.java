package visitor;
import java.util.*;

public class Maps{

	public HashMap<String,HashMap<String,ArrayList<regstatus>>> Proc_RegStatus;
    public HashMap<String,Integer> Proc_maxcallnum;

	public Maps(){
		this.Proc_RegStatus = new HashMap<String,HashMap<String,ArrayList<regstatus>>>();
		this.Proc_maxcallnum = new HashMap<String,Integer>();
	}

	public HashMap<String,HashMap<String,ArrayList<regstatus>>> AssReg(){
		return this.Proc_RegStatus;
	}

	public HashMap<String,Integer> AssCallarg(){
		return this.Proc_maxcallnum;
	}

}
