# 1+2+3...100
# i = 1;
# s = 0;
# while (i<=100){
#    s = s + i;
#    i = i + 1;
# } 
# printf("%d", s);


li  $t0, 1
li  $t1, 0

ble  $t0, 100, loop 

# more like do while
loop:
add $t1, $t1, $t0
add $t0, $t0, 1

ble $t0, 100, loop

move  $a0, $t1
li  $v0, 1
syscall