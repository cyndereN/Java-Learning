/*
Your task is to write a MIPS program that takes as input a number ?>0 and output all its Weird0008 representations.
For example, if ?=5, the output can be:
    5
    41
    32
    311
    221
    2111
    11111
When programming, try to follow the register usage convention (use $t0-$t9 for temporary variables, $s0-$s1 for saved variables, $a0-$a3 for arguments, etc.).
*/

/*
    Below is my solution, which has not been optimized in any way.
    In it, we use a recursion function that takes three arguments:
    1. The sum of the remaining digits (originally, the input number), placed in $a0.
    2. The number of digits in the current prefix (originally, 0), placed in $a1.
    3. The largest digit that¡¯s allowed to maintain monotonicity (originally, 9), placed in $a2.
    In the recursion, we loop through all the options for the next digit (between 1 and min($a0,$a2)).
    (But first, we save all the required registers!)
    For each possible digit, we:
    1. write it to mem[$a1] (where mem is an auxiliary array),
    2. set $a0 <- $a0 minus the current digit
    3. increment $a1
    4. set $a2 <- min($a2,the current digit)
    5. call the function recursively

*/
######### routine to print the numbers on one line.
.data
space: .asciiz " " # space to insert between numbers
newline: .asciiz "\n"
head: .asciiz "\n--------------------------------\n"
mem: .word 0 : 1000 # An auxiliary array
.text
li $8, 15 # The number we wish to represent
la $a0, head # Load the address to print the heading
li $v0, 4 # specify Print String service
syscall # print heading
move $a0, $8 #The sum of digits should equal $8
li $a1, 0 #The number of digits in the current number
li $a2, 9 #The largest allowed digit
jal recursion # call print routine.
li $v0, 10 # system call for exit
syscall # we are out of here.
recursion:
addi $sp, $sp, -16 # make room to store the registers
sw $ra, 0($sp) # we¡¯re calling a function, must save $ra
sw $s0, 4($sp) # by convention, must store $s0 to use it
sw $s1, 8($sp) # by convention, must store $s1 to use it
sw $s2, 12($sp) # by convention, must store $s2 to use it
move $s1, $a1 # we need to remember the number of digits so far
bnez $a0, non_zero # a pseudo instruction, same as bne $a0, $0
la $t0, mem # if we got here, we¡¯re done ($a0=0) time to print
print_loop: # print $a1 digits from mem
lw $t1, ($t0) # iterate over mem. $t0 is the pointer, $t1 is the value
li $v0, 1 # syscall 1 -- print int
move $a0, $t1 # by convention, the argument goes to $a0
syscall # print $t1=mem[$t0/4]
addi $t0, $t0, 4 # ++t0
subi $s1, $s1, 1 # --$s1, that tracks how many digits are left
bgtz $s1, print_loop # if we haven¡¯t printed all digits, go to the next one
la $a0, newline # load address of print heading, we¡¯re done with one representation.
li $v0, 4 # specify Print String service
syscall # print newline
j end_recursion # go restore values and then return to caller
non_zero: # We have ($a0 > 0), so we¡¯re not done
move $s0, $a0 # We need to store $a0 before making rec. calls
move $s2, $a2 # set $s2 = $a2 (we want $s2 = min($a0, $a2))
bge $s0, $s2, digit_loop # If ($s0 >= $s2) then we are done computing the min
move $s2, $s0 # otherwise fix $s2 to $s0
digit_loop: # We loop through $s2, $s2-1,¡­,1 for the next digit
sll $t0, $s1, 2 # $t0 = $s1 * t ? the byte offset for writing the next digit
la $t1, mem($t0) # Get $t1 to point at that memory address
sw $s2, ($t1) # Store the current digit in mem[$s1]
sub $a0, $s0, $s2 # The remaining digits need to sum up to $s0-$s2
move $a2, $s2 # For the recursion, we place the max allowed digit in $a2
addi $a1, $s1, 1 # We have now one more digit
jal recursion # Solve the problem for representing $a0, with the current prefix
addi $s2, $s2, -1 # We¡¯re done with all options with digit $s2, decrement it
bgtz $s2, digit_loop # If $s2>0 we need to continue the loop
end_recursion:
lw $ra, 0($sp) # read registers from stack
lw $s0, 4($sp)
lw $s1, 8($sp)
lw $s2, 12($sp)
addi $sp, $sp, 16 # bring back stack pointer
jr $ra # return