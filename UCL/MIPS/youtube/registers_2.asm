# int leaf_example (int g, h, i, j){
#     int f;
#    f = (g+h)-(i+j)
#    return f;
# }


# s register are caller safe and t register are callee safe;
# we cant modify the s register in the callee function

.text 
	main:
		# g~j -> a0,~a3
		# f in $s0
		# result in $v0
		addi $a0, $0, 10
		addi $a1, $0, 30
		addi $a2, $0, 10
		addi $a3, $0, 0
		
		jal leaf_example
		
		addi $v0, $0, 1
		syscall
		
	li $v0, 10
	syscall
	
	leaf_example:
		# push 
		# $s0 = f, $t0 = g+h, $t1 = i+j
		addi $sp, $sp, -12 
		sw $t1, 8($sp)
		sw $t0, 4($sp)
		sw $s0, 0($sp)
		
		add $t0, $a0, $a1
		add $t1, $a2, $a3
		sub $s0, $t0, $t1
		# store return value in v0 or v1
		add $v0, $0, $s0
		
		# pop
		lw $s0, 0($sp)
		lw $t0, 4($sp)
		lw $t1, 8($sp)
		jr $ra
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
# 堆和栈的区别：

# 一、堆栈空间分配的区别：
# 1、栈（操作系统）：由操作系统自动分配释放 ，存放函数的参数值，局部变量的值等。其操作方式类似于数据结构中的栈；
# 2、堆（操作系统）： 一般由程序员分配释放， 若程序员不释放，程序结束时可能由OS回收，分配方式倒是类似于链表。

# 二、堆和栈缓存方式的区别：
# 1、栈使用的是一级缓存， 他们通常都是被调用时处于存储空间中，调用完毕立即释放；
# 2、堆是存放在二级缓存中，生命周期由虚拟机的垃圾回收算法来决定（并不是一旦成为孤儿对象就能被回收）。所以调用这些对象的速度要相对来得低一些。

# 三、堆栈数据结构的区别：

# 栈（数据结构）：一种先进后出的数据结构。
# 堆（数据结构）：堆可以被看成是一棵树，如：堆排序；
		
		