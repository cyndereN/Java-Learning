# stateless service

public class SimpleFactorizer implements Servlet{ 
 public void service(ServletRequest req, ServletResponse resp) { 
 BigInteger i = extract(req); 
 BigInteger[] factors = factorize(i); 
 encodeInResponse(resp, factors); 
 } 
 ... 
}

# add caching
 ... 
 public void service(ServletRequest req, ServletResponse resp) { 
    BigInteger i = extract(req); 
    BigInteger[] factors = null; 
    if(i.equals(lastNumber)){ 
    factors = lastFactors.clone(); 
    } 
    if(factors == null){ 
    factors = factorize(i); 
    lastNumber = i; 
    lastFactors = factors; 
    } 
    encodeInResponse(resp, factors); 
 } 
} 
/*
Two threads A and B: 
[A] lastNumber = X 
[B] lastNumber = Y 
[B] lastFactors = factorize(Y) 
[A] lastFactors = factorize(X) 
Outcome: 
lastNumber = Y 
lastFactors = factorize(X)
*/

/*
Two threads A and B: 
[A] lastNumber.equals(X) 
[B] lastFactors = factorize(Y) 
[A] factors = lastFactors
Outcome: A sends back the 
factors of number Y 
(handled by B) instead of 
those for the input X
*/

# adding caching with better locking
public void service(ServletRequest req, 
 ServletResponse resp) { 
 BigInteger i = extract(req); 
 BigInteger[] factors = null; 
 synchronized(this){ 
 if(i.equals(lastNumber)) 
 factors = lastFactors.clone(); 
 } 
 if(factors == null){ 
 factors = factorize(i); 
 synchronized(this) { 
 lastNumber = i; lastFactors = factors; 
 } 
 } 
 encodeInResponse(resp, factors); 
 }

 # refactoring
public void service(ServletRequest req, ServletResponse resp) { 
 BigInteger i = extract(req); 
 BigInteger[] factors = getSavedFactors(i); 
 if(factors == null){ 
 factors = factorize(i); 
 saveState(i, factors); 
 } 
 encodeInResponse(resp, factors); 
 } 
 private synchronized BigInteger[] 
 getSavedFactors(BigInteger i) { 
 if(i.equals(lastNumber)){ 
 return lastFactors.clone(); 
 } 
 return null; 
 } 
 ... 
 private synchronized void saveState(BigInteger i, 
 BigInteger[] factors) { 
 lastNumber = i; 
 lastFactors = factors; 
 } 