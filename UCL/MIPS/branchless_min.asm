slt $1, $t1, $t2     # $1 = 1 if ($t1 < $t2)
sub $t0, $t1, $t2
mult $1, $t0
mflo $1              # $1 = ($t1-$t2) if ($t1 < $t2) else 0
add $t0, $0, $t2     # $t0 = $t2
add $t0, $t0, $1     # $t0 = $t2 + $1 = min($t1,$t2)

As it turns out, we can get a more efficient code by using bitwise instructions!
For example, consider the following:
### Actual branchless code of computing $t0 <- min($t1, $t2)
subu $1, $t1, $t2
sra $t0, $1, 31   #$t0 = 0xffffffff if ($t1 < $t2) and 0 otherwise
and $t0, $t0, $1  #$t0 = ($t1-$t2) if ($t1 < $t2) and 0 otherwise
add $t0, $t0, $t2
###