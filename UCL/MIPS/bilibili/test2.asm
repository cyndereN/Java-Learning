.data
msg: .asciiz "\nGood code!\n"
.text
li $v0, 4 # print string
la $a0, msg #pointer to the message
syscall