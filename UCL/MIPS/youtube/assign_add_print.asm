#include <stdio.h>
int main(void)
{
    int r, x;
    x = 2;
    r = x + 3;
    printf("%d", r);
    return 0;
}
----------

#将t1寄存器的值置为2
li $t1, 2
#将 t1寄存器中的数据加3，存到t0中 （t0 = t1 + 3）
add $t0, $t1, 3
#将t0寄存器的值放到a0寄存器中， 寄存器，寄存器
move $a0, $t0
#将v0寄存器的值置为1, 寄存器，数字
li $v0, 1
#系统调用
syscall 

# $v0 = 1, syscall -> print_int($a0)
# $v0 = 4, syscall -> print_string($a0)