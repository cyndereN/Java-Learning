.data
	my_character: .byte 'm'
	age: .word 23
	PI: .float 3.14
	my_double: .double 7.202
	zero_double: .double 0.0
# double、float都是浮点型。 
# double（双精度型）比float（单精度型）存的数据更准确些，占的空间也更大。 
# double精度是float的两倍，所以需要更精确的计算常使用double。 
# 单精度浮点数在机内占4个字节，用32位二进制描述
.text
    # m
	li $v0, 11
	lb $a0, my_character 
	syscall
	
	# m
	li $v0, 4
	la $a0, my_character 
	syscall
	
	# 23
	li $v0, 1
	lw $a0, age
	syscall
	
	# 3.14
	li $v0, 2
	lwc1 $f12, PI
	syscall
	
	# 7.202
	ldc1 $f2, my_double
	ldc1 $f0, zero_double
	
	li $v0, 3
	add.d $f12, $f2, $f0
	syscall
	
