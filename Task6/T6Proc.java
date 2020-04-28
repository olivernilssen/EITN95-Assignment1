
// This is an abstract class which all classes that are used for defining real 
// process types inherit. The puropse is to make sure that they all define the 
// method treatSignal, which is needed in the main program.
public abstract class T6Proc extends T6Global{
	public abstract void TreatSignal(T6Signal x);
}