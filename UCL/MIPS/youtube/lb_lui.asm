# b为(从（lsb）低字节即给定地址开始读）取一个有符号字节（八位）存到一个寄存器的最右端，那么这个时候一个寄存器里面应该有32位，
# 也就是它有24位还没用，那么如果是有符号字节在左端扩展1，如果是无符号字节（lbu），在左端扩展0；
# lui（load upper immediate）是在寄存器左端16位放一个16位数（在指令里面）右端补0，相比较lhu来说，lhu是在寄存器低16位放数
# lui是专门用来设置寄存器中常数的高16位的，并且MIPS为汇编保留了$at寄存器来创造一个32位数
.data 
vals: .word 2166502212
t2: .word 0
.text
la $t1, vals
la $t2, t2
lb $t0, 0($t1)
sw $t0, 0($t2)
li $t3, -61
sw $t3, 0($t1)