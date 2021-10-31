# abs_diff(a, b) calculates the
# absolute difference of two integers.
abs_diff:
    sub $v0, $a0, $a1 # result = a - b
    bgez $v0, return # If +ve jump next instruction.
    sub $v0, $a1, $a0 # result = b - a
return:
    jr $ra # Return from function.

# Calculating absolute difference
      .data
vals: .word 180, 100, 0
      .text
      .globl main
main: jal my_program # Run my program.
      li $v0 10 # Exit program using system call 10.
      syscall # syscall 10 (exit)
my_program:
    addi $sp, $sp, -8 # Move the stack pointer.
    sw $ra, 4($sp)    # Store return address.
    sw $fp, 0($sp)    # Store frame pointer.
    addi $fp, $sp, 4  # Set new frame pointer.
    la $t1, vals      # Load address of data into $t1.
    lw $a0, 0($t1)    # Load A from memory into $a0.
    lw $a1, 4($t1)    # Load B from memory into $a1.
    jal abs_diff      # Jump and link to ‘abs_diff' function.
    la $t1, vals      # Load address of data into $t1 again.
    sw $v0, 8($t1)    # Write result and return.
    lw $ra, 4($sp)    # Restore return address.
    lw $fp, 0($sp)    # Restore frame pointer.
    addi $sp, $sp, 8  # Restore stack pointer position.
    jr $ra            # Return from program.
