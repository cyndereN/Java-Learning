.data #数据段
# char* msg = "Hello World";
msg: .ascii "Hello World"

.text #代码段
li $v0, 4 # syscall with v0=4 --> print string
#加载地址, 寄存器，地址（字符串）
la $a0, msg #call parameter -->pointer to the message
syscall