public classPrime100Continue{
	public static void main( String args[ ] ){
		System.out.println(" **** 100--20 primes ****");
		int n=0;
		outer: for(int i=101;i<200;i+=2){ 
			for(int j=2; j<Math.sqrt(i)+1; j++){ 
				if( i%j==0 )		
					continue outer;
			}
			System.out.print(" "+i);
			n++;					
			if( n<10 )				
				continue;	
			System.out.println( );
			n=0;
		}
		System.out.println( );
	}
}
