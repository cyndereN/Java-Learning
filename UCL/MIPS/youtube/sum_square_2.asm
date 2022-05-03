li  $t0, 1
li  $t1, 0

ble  $t0, 100, loop 

loop:
mult $t0, $t0
mflo $t2
add $t1, $t1, $t2
add $t0, $t0, 1

ble $t0, 100, loop

move  $a0, $t1
li  $v0, 1
syscall