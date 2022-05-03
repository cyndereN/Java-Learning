# #include <stdio.h>

# int main(void) {
#    int i, last_number;
#    int numbers[10] = { 0 };

 #   i = 0;
 #   while (i < 10) {
 #       scanf("%d", &numbers[i]);
 #       last_number = numbers[i];
 #       i++;
 #   }
 #   i = 0;
 #   while (i < 10) {
 #       if (numbers[i] >= last_number) {
 #           printf("%d\n", numbers[i]);
 #       }
 #       i++;
 #   }
# }


# Read 10 numbers into an array
# then print the numbers which are
# larger than the last number read.

# i in register $t0
# registers $t1, $t2 & $t3 used to hold temporary results
# t4 is the last value



.data
numbers:  .word 0,0,0,0,0,0,0,0,0,0  # int numbers[10] = {0};

.text 
.global main
main:
    li   $t0, 0         # i = 0
loop0:
    bge  $t0, 10, end0  # while (i < 10) {

    li   $v0, 5         #   scanf("%d", &numbers[i]);
    syscall             # 输入的数字储存在v0中

    mul  $t1, $t0, 4    #   calculate &numbers[i]，一个int是4个byte
    la   $t2, numbers   #  储存数组的首地址
    add  $t3, $t1, $t2  #  数组的首地址，加上i * 4，就是i号元素应该在的位置
    sw   $v0, ($t3)     #   store entered number in array，sw就是save word的意思。
                        # v0输入的值储存在t3地址
    add  $t4, $v0, 0    # also can be s0
    addi $t0, $t0, 1    #   i++;
    j    loop0          # }
end0:
    li   $t0, 0         # i = 0
loop1:
    bge  $t0, 10, end1  # while (i < 10) {

    mul  $t1, $t0, 4    #   calculate &numbers[i]
    la   $t2, numbers   #
    add  $t3, $t1, $t2  #
    lw   $a0, ($t3)     #   load numbers[i] into $a0
    bge  $a0, $t4, print # 如果大于等于，就需要去有输出的分支。

    addi $t0, $t0, 1    #   i++
    j    loop1          # }
print:
    li   $v0, 1         #   printf("%d", numbers[i])
    syscall

    li   $a0, '\n'      #   printf("%c", '\n');
    li   $v0, 11
    syscall
    addi $t0, $t0, 1    #   i++  在输出分支里继续增加控制变量。
    j    loop1          # }
end1:
    li $v0, 10  # terminate excecution
    syscall 
