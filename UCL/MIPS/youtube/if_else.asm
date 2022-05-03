# if (i==j)
# f = g+h
# else
# f = f-i

# $s3 = f, $s1 = g, $s2= h
# $s3 = i, $s4 = j

bne $s3, $s4, L1
addu $s0, $s1, $s2  # if i==j
j done

L1:
	subu $s0, $s0, $s3

done: