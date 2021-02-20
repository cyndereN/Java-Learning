class Assertion {
	public static void main(String[] args)	{
		assert hypotenuse(3,4)==5 : "wrong algorithm";
	}
	static double hypotenuse( double x, double y ){
		return Math.sqrt( x*x + y*y + 1);
	}
}
