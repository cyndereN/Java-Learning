package comp207p.main;

import org.apache.bcel.classfile.*;
import org.apache.bcel.generic.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;


public class ConstantFolder {
    ClassParser parser = null;
    ClassGen gen = null;
    JavaClass original = null;
    JavaClass optimized = null;
    private boolean isOptimised = true;

    public ConstantFolder(String classFilePath) {
        try {
            this.parser = new ClassParser(classFilePath);
            this.original = this.parser.parse();
            this.gen = new ClassGen(this.original);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isLoadOp(Instruction instruction) {
        if (instruction instanceof LDC)
            return true;
        if (instruction instanceof LDC2_W)
            return true;
        if (instruction instanceof ConstantPushInstruction) {
            return true;
        }
        return false;
    }

    // Replace Bipish and Sipush with load instruction
    private void preprocessor(ConstantPoolGen cpgen, InstructionList instList) {
        InstructionHandle[] handles = instList.getInstructionHandles();
        ConstantPool cp = cpgen.getConstantPool();
        for (int i = 0; i < handles.length; i++) {
            if (handles[i].getInstruction() instanceof BIPUSH || handles[i].getInstruction() instanceof SIPUSH) {
                Number value = ((ConstantPushInstruction) handles[i].getInstruction()).getValue();
                Constant newConstant = new ConstantInteger((Integer) value);

                int handleIndex = cpgen.addConstant(newConstant, cpgen);
                instList.insert(handles[i], new LDC(handleIndex));

                setTarget(i + 1, i, cpgen, instList);
                try {
                    instList.delete(handles[i]);
                } catch (TargetLostException e) {
                    System.out.println("target lost");
                }
            }
        }
    }

    private Number loadDataValue(LDC2_W handle, ConstantPoolGen cpgen) {
        return handle.getValue(cpgen);
    }

    private Object loadDataValue(LDC handle, ConstantPoolGen cpgen) {
        return handle.getValue(cpgen);
    }


    private int calc(PushInstruction handleX, PushInstruction handleY, Instruction operand, ConstantPoolGen cpgen) {
        int index = -1;
        if (operand instanceof IADD) {
            int x;
            int y;
            if (handleX instanceof CPInstruction) {
                x = (int) loadDataValue((LDC) handleX, cpgen);
            } else {
                x = (int) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (int) loadDataValue((LDC) handleY, cpgen);
            } else {
                y = (int) ((ConstantPushInstruction) handleY).getValue();
            }
            int ans = x + y;
            index = cpgen.addConstant(new ConstantInteger(ans), cpgen);
        } else if (operand instanceof ISUB) {
            int x;
            int y;
            if (handleX instanceof CPInstruction) {
                x = (int) loadDataValue((LDC) handleX, cpgen);
            } else {
                x = (int) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (int) loadDataValue((LDC) handleY, cpgen);
            } else {
                y = (int) ((ConstantPushInstruction) handleY).getValue();
            }
            int ans = x - y;
            index = cpgen.addConstant(new ConstantInteger(ans), cpgen);
        } else if (operand instanceof IMUL) {
            int x;
            int y;
            if (handleX instanceof CPInstruction) {
                x = (int) loadDataValue((LDC) handleX, cpgen);
            } else {
                x = (int) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (int) loadDataValue((LDC) handleY, cpgen);
            } else {
                y = (int) ((ConstantPushInstruction) handleY).getValue();
            }
            int ans = x * y;
            index = cpgen.addConstant(new ConstantInteger(ans), cpgen);
        } else if (operand instanceof IDIV) {
            int x;
            int y;
            if (handleX instanceof CPInstruction) {
                x = (int) loadDataValue((LDC) handleX, cpgen);
            } else {
                x = (int) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (int) loadDataValue((LDC) handleY, cpgen);
            } else {
                y = (int) ((ConstantPushInstruction) handleY).getValue();
            }
            int ans = x / y;
            index = cpgen.addConstant(new ConstantInteger(ans), cpgen);
        } else if (operand instanceof FADD) {
            float x;
            float y;
            if (handleX instanceof CPInstruction) {
                x = (float) loadDataValue((LDC) handleX, cpgen);
            } else {
                x = (float) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (float) loadDataValue((LDC) handleY, cpgen);
            } else {
                y = (float) ((ConstantPushInstruction) handleY).getValue();
            }
            float ans = x + y;
            index = cpgen.addConstant(new ConstantFloat(ans), cpgen);
        } else if (operand instanceof FSUB) {
            float x;
            float y;
            if (handleX instanceof CPInstruction) {
                x = (float) loadDataValue((LDC) handleX, cpgen);
            } else {
                x = (float) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (float) loadDataValue((LDC) handleY, cpgen);
            } else {
                y = (float) ((ConstantPushInstruction) handleY).getValue();
            }
            float ans = x - y;
            index = cpgen.addConstant(new ConstantFloat(ans), cpgen);
        } else if (operand instanceof FMUL) {
            float x;
            float y;
            if (handleX instanceof CPInstruction) {
                x = (float) loadDataValue((LDC) handleX, cpgen);
            } else {
                x = (float) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (float) loadDataValue((LDC) handleY, cpgen);
            } else {
                y = (float) ((ConstantPushInstruction) handleY).getValue();
            }
            float ans = x * y;
            index = cpgen.addConstant(new ConstantFloat(ans), cpgen);
        } else if (operand instanceof FDIV) {
            float x;
            float y;
            if (handleX instanceof CPInstruction) {
                x = (float) loadDataValue((LDC) handleX, cpgen);
            } else {
                x = (float) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (float) loadDataValue((LDC) handleY, cpgen);
            } else {
                y = (float) ((ConstantPushInstruction) handleY).getValue();
            }
            float ans = x / y;
            index = cpgen.addConstant(new ConstantFloat(ans), cpgen);
        } else if (operand instanceof LADD) {
            long x;
            long y;
            if (handleX instanceof CPInstruction) {
                x = (long) loadDataValue((LDC2_W) handleX, cpgen);
            } else {
                x = (long) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (long) loadDataValue((LDC2_W) handleY, cpgen);
            } else {
                y = (long) ((ConstantPushInstruction) handleY).getValue();
            }
            long ans = x + y;
            index = cpgen.addConstant(new ConstantLong(ans), cpgen);
        } else if (operand instanceof LSUB) {
            long x;
            long y;
            if (handleX instanceof CPInstruction) {
                x = (long) loadDataValue((LDC2_W) handleX, cpgen);
            } else {
                x = (long) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (long) loadDataValue((LDC2_W) handleY, cpgen);
            } else {
                y = (long) ((ConstantPushInstruction) handleY).getValue();
            }
            long ans = x - y;
            index = cpgen.addConstant(new ConstantLong(ans), cpgen);
        } else if (operand instanceof LMUL) {
            long x;
            long y;
            if (handleX instanceof CPInstruction) {
                x = (long) loadDataValue((LDC2_W) handleX, cpgen);
            } else {
                x = (long) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (long) loadDataValue((LDC2_W) handleY, cpgen);
            } else {
                y = (long) ((ConstantPushInstruction) handleY).getValue();
            }
            long ans = x * y;
            index = cpgen.addConstant(new ConstantLong(ans), cpgen);
        } else if (operand instanceof LDIV) {
            long x;
            long y;
            if (handleX instanceof CPInstruction) {
                x = (long) loadDataValue((LDC2_W) handleX, cpgen);
            } else {
                x = (long) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (long) loadDataValue((LDC2_W) handleY, cpgen);
            } else {
                y = (long) ((ConstantPushInstruction) handleY).getValue();
            }
            long ans = x / y;
            index = cpgen.addConstant(new ConstantLong(ans), cpgen);
        } else if (operand instanceof DADD) {
            double x;
            double y;
            if (handleX instanceof CPInstruction) {
                x = (double) loadDataValue((LDC2_W) handleX, cpgen);
            } else {
                x = (double) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (double) loadDataValue((LDC2_W) handleY, cpgen);
            } else {
                y = (double) ((ConstantPushInstruction) handleY).getValue();
            }
            double ans = x + y;
            index = cpgen.addConstant(new ConstantDouble(ans), cpgen);
        } else if (operand instanceof DSUB) {
            double x;
            double y;
            if (handleX instanceof CPInstruction) {
                x = (double) loadDataValue((LDC2_W) handleX, cpgen);
            } else {
                x = (double) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (double) loadDataValue((LDC2_W) handleY, cpgen);
            } else {
                y = (double) ((ConstantPushInstruction) handleY).getValue();
            }
            double ans = x - y;
            index = cpgen.addConstant(new ConstantDouble(ans), cpgen);
        } else if (operand instanceof DMUL) {
            double x;
            double y;
            if (handleX instanceof CPInstruction) {
                x = (double) loadDataValue((LDC2_W) handleX, cpgen);
            } else {
                x = (double) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (double) loadDataValue((LDC2_W) handleY, cpgen);
            } else {
                y = (double) ((ConstantPushInstruction) handleY).getValue();
            }
            double ans = x * y;
            index = cpgen.addConstant(new ConstantDouble(ans), cpgen);
        } else if (operand instanceof DDIV) {
            double x;
            double y;
            if (handleX instanceof CPInstruction) {
                x = (double) loadDataValue((LDC2_W) handleX, cpgen);
            } else {
                x = (double) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (double) loadDataValue((LDC2_W) handleY, cpgen);
            } else {
                y = (double) ((ConstantPushInstruction) handleY).getValue();
            }
            double ans = x / y;
            index = cpgen.addConstant(new ConstantDouble(ans), cpgen);
        } else if (operand instanceof LCMP) {
            long x;
            long y;
            if (handleX instanceof CPInstruction) {
                x = (long) loadDataValue((LDC2_W) handleX, cpgen);
            } else {
                x = (long) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (long) loadDataValue((LDC2_W) handleY, cpgen);
            } else {
                y = (long) ((ConstantPushInstruction) handleY).getValue();
            }
            int ans;
            if (x < y)
                ans = -1;
            else if (x == y)
                ans = 0;
            else
                ans = 1;
            index = cpgen.addConstant(new ConstantInteger(ans), cpgen);
        } else if (operand instanceof FCMPG || operand instanceof FCMPL) {
            float x;
            float y;
            if (handleX instanceof CPInstruction) {
                x = (float) loadDataValue((LDC) handleX, cpgen);
            } else {
                x = (float) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (float) loadDataValue((LDC) handleY, cpgen);
            } else {
                y = (float) ((ConstantPushInstruction) handleY).getValue();
            }
            int ans;
            if (x < y)
                ans = -1;
            else if (x == y)
                ans = 0;
            else
                ans = 1;
            index = cpgen.addConstant(new ConstantInteger(ans), cpgen);
        } else if (operand instanceof DCMPG || operand instanceof DCMPL) {
            double x;
            double y;
            if (handleX instanceof CPInstruction) {
                x = (double) loadDataValue((LDC2_W) handleX, cpgen);
            } else {
                x = (double) ((ConstantPushInstruction) handleX).getValue();
            }
            if (handleY instanceof CPInstruction) {
                y = (double) loadDataValue((LDC2_W) handleY, cpgen);
            } else {
                y = (double) ((ConstantPushInstruction) handleY).getValue();
            }
            int ans;
            if (x < y)
                ans = -1;
            else if (x == y)
                ans = 0;
            else
                ans = 1;
            index = cpgen.addConstant(new ConstantInteger(ans), cpgen);
        }
        return index;
    }

    private int foldUnaryOperand(CPInstruction handle, Instruction operand, ConstantPoolGen cpgen) {
        int index = -1;
        if (operand instanceof I2L) {
            int value = (Integer) loadDataValue((LDC) handle, cpgen);
            index = cpgen.addConstant(new ConstantLong((long) value), cpgen);
        } else if (operand instanceof I2F) {
            int value = (Integer) loadDataValue((LDC) handle, cpgen);
            index = cpgen.addConstant(new ConstantFloat((float) value), cpgen);
        } else if (operand instanceof I2D) {
            int value = (Integer) loadDataValue((LDC) handle, cpgen);
            index = cpgen.addConstant(new ConstantDouble((double) value), cpgen);
        } else if (operand instanceof L2I) {
            long value = (Long) loadDataValue((LDC2_W) handle, cpgen);
            index = cpgen.addConstant(new ConstantInteger((int) value), cpgen);
        } else if (operand instanceof L2F) {
            long value = (Long) loadDataValue((LDC2_W) handle, cpgen);
            index = cpgen.addConstant(new ConstantFloat((float) value), cpgen);
        } else if (operand instanceof L2D) {
            long value = (Long) loadDataValue((LDC2_W) handle, cpgen);
            index = cpgen.addConstant(new ConstantDouble((double) value), cpgen);
        } else if (operand instanceof D2F) {
            double value = (Double) loadDataValue((LDC2_W) handle, cpgen);
            index = cpgen.addConstant(new ConstantFloat((float) value), cpgen);
        } else if (operand instanceof D2I) {
            double value = (Double) loadDataValue((LDC2_W) handle, cpgen);
            index = cpgen.addConstant(new ConstantInteger((int) value), cpgen);
        } else if (operand instanceof D2L) {
            double value = (Double) loadDataValue((LDC2_W) handle, cpgen);
            index = cpgen.addConstant(new ConstantLong((long) value), cpgen);
        } else if (operand instanceof F2D) {
            float value = (Float) loadDataValue((LDC) handle, cpgen);
            index = cpgen.addConstant(new ConstantDouble((double) value), cpgen);
        } else if (operand instanceof F2I) {
            float value = (Float) loadDataValue((LDC) handle, cpgen);
            index = cpgen.addConstant(new ConstantInteger((int) value), cpgen);
        } else if (operand instanceof F2L) {
            float value = (Float) loadDataValue((LDC) handle, cpgen);
            index = cpgen.addConstant(new ConstantLong((long) value), cpgen);
        } else if (operand instanceof INEG) {
            int value = (int) loadDataValue((LDC) handle, cpgen);
            index = cpgen.addConstant(new ConstantInteger(0 - value), cpgen);
        } else if (operand instanceof FNEG) {
            float value = (float) loadDataValue((LDC) handle, cpgen);
            index = cpgen.addConstant(new ConstantFloat(0.0f - value), cpgen);
        } else if (operand instanceof DNEG) {
            double value = (double) loadDataValue((LDC2_W) handle, cpgen);
            index = cpgen.addConstant(new ConstantDouble(0.0 - value), cpgen);
        } else if (operand instanceof LNEG) {
            long value = (long) loadDataValue((LDC2_W) handle, cpgen);
            index = cpgen.addConstant(new ConstantLong(0L - value), cpgen);
        }
        return index;
    }

    private int foldUnaryOperand(ConstantPushInstruction handle, Instruction operand, ConstantPoolGen cpgen) {
        int index = -1;
        if (operand instanceof I2L || operand instanceof F2L || operand instanceof D2L) {
            long value = (long) handle.getValue();
            index = cpgen.addConstant(new ConstantLong(value), cpgen);
        } else if (operand instanceof I2F || operand instanceof D2F || operand instanceof L2F) {
            float value = (float) handle.getValue();
            index = cpgen.addConstant(new ConstantFloat(value), cpgen);
        } else if (operand instanceof I2D || operand instanceof F2D || operand instanceof L2D) {
            double value = (double) handle.getValue();
            index = cpgen.addConstant(new ConstantDouble(value), cpgen);
        } else if (operand instanceof L2I || operand instanceof F2I || operand instanceof D2I) {
            int value = (int) handle.getValue();
            index = cpgen.addConstant(new ConstantInteger(value), cpgen);
        } else if (operand instanceof INEG) {
            int value = (int) handle.getValue();
            index = cpgen.addConstant(new ConstantInteger(0 - value), cpgen);
        } else if (operand instanceof FNEG) {
            float value = (float) handle.getValue();
            index = cpgen.addConstant(new ConstantFloat(0.0f - value), cpgen);
        } else if (operand instanceof DNEG) {
            double value = (double) handle.getValue();
            index = cpgen.addConstant(new ConstantDouble(0.0 - value), cpgen);
        } else if (operand instanceof LNEG) {
            long value = (long) handle.getValue();
            index = cpgen.addConstant(new ConstantLong(0L - value), cpgen);
        }
        return index;
    }

    private void setTarget(int from, int to, ConstantPoolGen cpgen, InstructionList insrtList) {
        InstructionHandle[] handles = insrtList.getInstructionHandles();
        for (InstructionHandle handle : handles) {
            if (handle.getInstruction() instanceof InstructionTargeter) {
                if (((InstructionTargeter) handle.getInstruction()).containsTarget(handles[from])) {
                    ((InstructionTargeter) handle.getInstruction()).updateTarget(handles[from], handles[to]);
                }
            }
        }
    }

    private void setTarget(InstructionHandle from, InstructionHandle to, ConstantPoolGen cpgen, InstructionList insrtList) {
        InstructionHandle[] handles = insrtList.getInstructionHandles();
        for (InstructionHandle handle : handles) {
            if (handle.getInstruction() instanceof InstructionTargeter) {
                if (((InstructionTargeter) handle.getInstruction()).containsTarget(from)) {
                    ((InstructionTargeter) handle.getInstruction()).updateTarget(from, to);
                }
            }
        }
    }

    private int findTargetIndex(InstructionHandle[] handles, InstructionHandle handle) {
        for (int i = 0; i < handles.length; i++) {
            if (handles[i].equals(handle))
                return i;
        }
        return -1;
    }

    private boolean foldIfElseStatementUnary(InstructionHandle instructionHandle, int value, int index, InstructionHandle[] handles, ConstantPoolGen cpgen, InstructionList instList) {
//        System.out.println(index);
        IfInstruction instruction = (IfInstruction) instructionHandle.getInstruction();
        boolean flag = false;
        if (instruction instanceof IFEQ && value == 0)
            flag = true;
        if (instruction instanceof IFGE && value >= 0)
            flag = true;
        if (instruction instanceof IFLE && value <= 0)
            flag = true;
        if (instruction instanceof IFGT && value > 0)
            flag = true;
        if (instruction instanceof IFLT && value < 0)
            flag = true;
        if (instruction instanceof IFNE && value != 0)
            flag = true;

        InstructionHandle ifTarget = instruction.getTarget();
        if (findTargetIndex(handles, ifTarget) < index) {
            return false;
        }
        InstructionHandle gotoTarget = null;
        if (ifTarget.getPrev().getInstruction() instanceof GotoInstruction) {
            gotoTarget = ((GotoInstruction) ifTarget.getPrev().getInstruction()).getTarget();
            if (findTargetIndex(handles, gotoTarget) < findTargetIndex(handles, ifTarget) - 1) {
                return false;
            }
        }
        if (flag) {
            setTarget(handles[index - 1], ifTarget, cpgen, instList);
            try {
                instList.delete(handles[index - 1], ifTarget.getPrev());
            } catch (TargetLostException e) {
                System.out.println("target lost");
            }
        } else {
            setTarget(handles[index - 1], handles[index + 1], cpgen, instList);
            try {
                instList.delete(handles[index - 1], handles[index]);
                if (gotoTarget != null) {
                    instList.delete(ifTarget.getPrev(), gotoTarget.getPrev());
                }
            } catch (TargetLostException e) {
                System.out.println("target lost");
            }
        }
        return true;
    }

    private boolean foldIfElseStatementBinary(IfInstruction instruction, int x, int y, int index, InstructionHandle[] handles, ConstantPoolGen cpgen, InstructionList instList) {
        boolean flag = false;
        if (instruction instanceof IF_ICMPEQ && x == y)
            flag = true;
        if (instruction instanceof IF_ICMPGE && x >= y)
            flag = true;
        if (instruction instanceof IF_ICMPLE && x <= y)
            flag = true;
        if (instruction instanceof IF_ICMPGT && x > y)
            flag = true;
        if (instruction instanceof IF_ICMPLT && x < y)
            flag = true;
        if (instruction instanceof IF_ICMPNE && x != y)
            flag = true;
        InstructionHandle ifTarget = instruction.getTarget();
        if (findTargetIndex(handles, ifTarget) < index) {
            return false;
        }
        InstructionHandle gotoTarget = null;
        if (ifTarget.getPrev().getInstruction() instanceof GotoInstruction) {
            gotoTarget = ((GotoInstruction) ifTarget.getPrev().getInstruction()).getTarget();
            if (findTargetIndex(handles, gotoTarget) < findTargetIndex(handles, ifTarget) - 1) {
                return false;
            }
        }
        if (flag) {
            setTarget(handles[index - 2], ifTarget, cpgen, instList);
            try {
                instList.delete(handles[index - 2], ifTarget.getPrev());
            } catch (TargetLostException e) {
                System.out.println("target lost");
            }
        } else {
            setTarget(handles[index - 2], handles[index + 1], cpgen, instList);
            try {
                instList.delete(handles[index - 2], handles[index]);
                if (gotoTarget != null) {
                    instList.delete(ifTarget.getPrev(), gotoTarget.getPrev());
                }
            } catch (TargetLostException e) {
                System.out.println("target lost");
            }
        }
        return true;
    }

    private void simpleFolding(ConstantPoolGen cpgen, InstructionList instList) {

        InstructionHandle[] handles = instList.getInstructionHandles();
        ConstantPool cp = cpgen.getConstantPool();

        // Task 1 : Simple folding...
        boolean isOptimised;
        do {
            isOptimised = true;
            for (int i = 1; i < handles.length; i++) {
                // Unary operand
                if (handles[i].getInstruction() instanceof ConversionInstruction ||
                        handles[i].getInstruction() instanceof INEG ||
                        handles[i].getInstruction() instanceof FNEG ||
                        handles[i].getInstruction() instanceof DNEG ||
                        handles[i].getInstruction() instanceof LNEG) {
                    if (isLoadOp(handles[i - 1].getInstruction())) {
                        int index;
                        isOptimised = false;
                        if (handles[i - 1].getInstruction() instanceof CPInstruction) {
                            index = foldUnaryOperand((CPInstruction) handles[i - 1].getInstruction(), handles[i].getInstruction(), cpgen);
                        } else {
                            index = foldUnaryOperand((ConstantPushInstruction) handles[i - 1].getInstruction(), handles[i].getInstruction(), cpgen);
                        }
                        Instruction operand = handles[i].getInstruction();
                        if (operand instanceof I2L || operand instanceof F2L || operand instanceof D2L || operand instanceof I2D || operand instanceof F2D || operand instanceof L2D) {
                            instList.insert(handles[i], new LDC2_W(index));
                        } else if (operand instanceof I2F || operand instanceof D2F || operand instanceof L2F || operand instanceof L2I || operand instanceof F2I || operand instanceof D2I) {
                            instList.insert(handles[i], new LDC(index));
                        } else if (operand instanceof INEG || operand instanceof FNEG) {
                            instList.insert(handles[i], new LDC(index));
                        } else if (operand instanceof DNEG || operand instanceof LNEG) {
                            instList.insert(handles[i], new LDC2_W(index));
                        }
                        setTarget(i - 1, i, cpgen, instList);
                        try {
                            // delete the old ones
                            instList.delete(handles[i]);
                            instList.delete(handles[i - 1]);
                        } catch (TargetLostException e) {
                            System.out.println("target lost");
                        }
                        break;
                    }
                }
                if (handles[i].getInstruction() instanceof IFEQ || handles[i].getInstruction() instanceof IFLE
                        || handles[i].getInstruction() instanceof IFGT || handles[i].getInstruction() instanceof IFLT
                        || handles[i].getInstruction() instanceof IFNE || handles[i].getInstruction() instanceof IFGE) {
                    if (isLoadOp(handles[i - 1].getInstruction())) {
                        int value;
                        if (handles[i - 1].getInstruction() instanceof ConstantPushInstruction)
                            value = (int) ((ConstantPushInstruction) handles[i - 1].getInstruction()).getValue();
                        else
                            value = (int) ((LDC) handles[i - 1].getInstruction()).getValue(cpgen);
                        if (foldIfElseStatementUnary(handles[i], value, i, handles, cpgen, instList))
                            isOptimised = false;
                        break;
                    }
                }

                // Binary operand
                if (i == 1)
                    continue;
                if (handles[i].getInstruction() instanceof ArithmeticInstruction || handles[i].getInstruction() instanceof LCMP
                        || handles[i].getInstruction() instanceof FCMPG || handles[i].getInstruction() instanceof FCMPL
                        || handles[i].getInstruction() instanceof DCMPG || handles[i].getInstruction() instanceof DCMPL) {
                    if (handles[i - 1].getInstruction() instanceof PushInstruction
                            && handles[i - 2].getInstruction() instanceof PushInstruction
                            && !(handles[i - 1].getInstruction() instanceof LoadInstruction)
                            && !(handles[i - 2].getInstruction() instanceof LoadInstruction)
                            ) {
                        isOptimised = false;
                        int index = calc((PushInstruction) handles[i - 2].getInstruction(), (PushInstruction) handles[i - 1].getInstruction(), handles[i].getInstruction(), cpgen);
                        Instruction operand = handles[i].getInstruction();
                        if (operand instanceof IADD || operand instanceof ISUB || operand instanceof IMUL || operand instanceof IDIV || operand instanceof FADD || operand instanceof FSUB || operand instanceof FMUL || operand instanceof FDIV
                                || operand instanceof LCMP || operand instanceof FCMPL || operand instanceof FCMPG || operand instanceof DCMPL || operand instanceof DCMPG) {
                            instList.insert(handles[i], new LDC(index));
                        } else {
                            instList.insert(handles[i], new LDC2_W(index));
                        }
                        setTarget(i - 2, i, cpgen, instList);
                        try {
                            // delete the old ones
                            instList.delete(handles[i]);
                            instList.delete(handles[i - 1]);
                            instList.delete(handles[i - 2]);
                        } catch (TargetLostException e) {
                            System.out.println("target lost");
                        }
                        break;
                    }
                }
                if (handles[i].getInstruction() instanceof IF_ICMPEQ || handles[i].getInstruction() instanceof IF_ICMPGE
                        || handles[i].getInstruction() instanceof IF_ICMPGT || handles[i].getInstruction() instanceof IF_ICMPLE
                        || handles[i].getInstruction() instanceof IF_ICMPLT || handles[i].getInstruction() instanceof IF_ICMPNE) {
                    if ((handles[i - 1].getInstruction() instanceof LDC || handles[i - 1].getInstruction() instanceof ICONST)
                            && (handles[i - 2].getInstruction() instanceof LDC || handles[i - 2].getInstruction() instanceof ICONST)) {
                        int x, y;
                        if (handles[i - 1].getInstruction() instanceof ConstantPushInstruction)
                            y = (int) ((ConstantPushInstruction) handles[i - 1].getInstruction()).getValue();
                        else
                            y = (int) ((LDC) handles[i - 1].getInstruction()).getValue(cpgen);
                        if (handles[i - 2].getInstruction() instanceof ConstantPushInstruction)
                            x = (int) ((ConstantPushInstruction) handles[i - 2].getInstruction()).getValue();
                        else
                            x = (int) ((LDC) handles[i - 2].getInstruction()).getValue(cpgen);
                        if (foldIfElseStatementBinary((IfInstruction) handles[i].getInstruction(), x, y, i, handles, cpgen, instList))
                            isOptimised = false;
                        break;
                    }
                }

            }
            handles = instList.getInstructionHandles();
        } while (!isOptimised);
    }

    private void constantVariables(ConstantPoolGen cpgen, InstructionList instList) {
        InstructionHandle[] handles = instList.getInstructionHandles();
        ConstantPool cp = cpgen.getConstantPool();

        boolean isOptimised;
        do {
            isOptimised = true;
            for (int i = 1; i < handles.length; i++) {
                if (handles[i].getInstruction() instanceof StoreInstruction
                        && handles[i - 1].getInstruction() instanceof PushInstruction
                        && !(handles[i - 1].getInstruction() instanceof LoadInstruction)) {
                    int index = ((StoreInstruction) handles[i].getInstruction()).getIndex();
                    boolean findLoad = false;
                    for (int j = 1; j < handles.length; j++) {
                        if (j == i) continue;
                        if (handles[j].getInstruction() instanceof LoadInstruction
                                && index == ((LoadInstruction) handles[j].getInstruction()).getIndex()
                                || handles[j].getInstruction() instanceof IINC
                                && ((IINC) handles[j].getInstruction()).getIndex() == index) {
                            findLoad = true;
                            break;
                        }
                    }
                    boolean findOther = false;
                    for (int j = 1; j < handles.length; j++) {
                        if (j == i) continue;
                        if (handles[j].getInstruction() instanceof StoreInstruction
                                && index == ((StoreInstruction) handles[j].getInstruction()).getIndex()
                                || handles[j].getInstruction() instanceof IINC
                                && ((IINC) handles[j].getInstruction()).getIndex() == index) {
                            findOther = true;
                            break;
                        }
                    }
                    if (findOther && findLoad)
                        continue;
                    isOptimised = false;
                    if (handles[i - 1].getInstruction() instanceof ConstantPushInstruction) {
                        Number value = ((ConstantPushInstruction) handles[i - 1].getInstruction()).getValue();
                        Constant newConstant = new ConstantInteger(0);
                        if (value instanceof Integer) {
                            newConstant = new ConstantInteger((int) value);
                        } else if (value instanceof Long) {
                            newConstant = new ConstantLong((long) value);
                        } else if (value instanceof Double) {
                            newConstant = new ConstantDouble((double) value);
                        } else if (value instanceof Float) {
                            newConstant = new ConstantFloat((float) value);
                        }

                        int handleIndex = cpgen.addConstant(newConstant, cpgen);
                        for (int j = i + 1; j < handles.length; j++) {
                            if (handles[j].getInstruction() instanceof LoadInstruction &&
                                    ((LoadInstruction) handles[j].getInstruction()).getIndex() == index) {
                                if (value instanceof Integer || value instanceof Float) {
                                    instList.insert(handles[j], new LDC(handleIndex));
                                } else if (value instanceof Long || value instanceof Double) {
                                    instList.insert(handles[j], new LDC2_W(handleIndex));
                                }
                                setTarget(j + 1, j, cpgen, instList);
                                try {
                                    instList.delete(handles[j]);
                                } catch (TargetLostException e) {
                                    System.out.println("target lost");
                                }
                            }
                        }
                    } else if (handles[i - 1].getInstruction() instanceof CPInstruction) {
                        if ((handles[i - 1].getInstruction()) instanceof LDC) {
                            int handleIndex = ((LDC) handles[i - 1].getInstruction()).getIndex();

                            for (int j = i + 1; j < handles.length; j++) {
                                if (handles[j].getInstruction() instanceof LoadInstruction &&
                                        ((LoadInstruction) handles[j].getInstruction()).getIndex() == index) {
                                    instList.insert(handles[j], new LDC(handleIndex));
                                    setTarget(j + 1, j, cpgen, instList);
                                    try {
                                        instList.delete(handles[j]);
                                    } catch (TargetLostException e) {
                                        System.out.println("target lost");
                                    }
                                }
                            }
                        } else if ((handles[i - 1].getInstruction()) instanceof LDC2_W) {
                            int handleIndex = ((LDC2_W) handles[i - 1].getInstruction()).getIndex();
                            for (int j = i + 1; j < handles.length; j++) {
                                if (handles[j].getInstruction() instanceof LoadInstruction &&
                                        ((LoadInstruction) handles[j].getInstruction()).getIndex() == index) {
                                    instList.insert(handles[j], new LDC2_W(handleIndex));
                                    setTarget(j + 1, j, cpgen, instList);
                                    try {
                                        instList.delete(handles[j]);
                                    } catch (TargetLostException e) {
                                        System.out.println("target lost");
                                    }
                                }
                            }
                        }
                    }
                    setTarget(i - 1, i + 1, cpgen, instList);
                    try {
                        instList.delete(handles[i]);
                        instList.delete(handles[i - 1]);
                    } catch (TargetLostException e) {
                        System.out.println("target lost");
                    }
                    break;
                }
            }
            handles = instList.getInstructionHandles();
            simpleFolding(cpgen, instList);
            handles = instList.getInstructionHandles();
        } while (!isOptimised);
    }

    private int recursiveFoldVariable(int memoryIndex, int constantIndex, Object value, int handleStartIndex, int handleEndIndex, ConstantPoolGen cpgen, InstructionList instList, boolean isLoop) {
        InstructionHandle[] handles = instList.getInstructionHandles();
        // return if there is value reassigned during this layer
        // hashmap records the range of the loops
        HashMap<Integer, Integer> loopFromTo = new HashMap<>();
        HashMap<Integer, Integer> loopRange = new HashMap<>();
        for (int i = handleStartIndex; i <= handleEndIndex && i < handles.length; i++) {
            if (handles[i].getInstruction() instanceof IfInstruction && findTargetIndex(handles, ((IfInstruction) handles[i].getInstruction()).getTarget()) < i) {
                loopFromTo.put(findTargetIndex(handles, ((IfInstruction) handles[i].getInstruction()).getTarget()), i);
            } else if (handles[i].getInstruction() instanceof GotoInstruction && findTargetIndex(handles, ((GotoInstruction) handles[i].getInstruction()).getTarget()) < i) {
                loopRange.put(findTargetIndex(handles, ((GotoInstruction) handles[i].getInstruction()).getTarget()), i);
            }
        }
        boolean updateable = true;
        for (int i = handleStartIndex; i <= handleEndIndex && i < handles.length; i++) {
            if (handles[i].getInstruction() instanceof StoreInstruction && ((StoreInstruction) handles[i].getInstruction()).getIndex() == memoryIndex
                    || handles[i].getInstruction() instanceof IINC && ((IINC) handles[i].getInstruction()).getIndex() == memoryIndex)
                updateable = false;
        }
        int i = handleStartIndex;
        boolean inLoopRange = false;
        while (i <= handleEndIndex && i < handles.length) {
            if (loopRange.containsKey(i))
                inLoopRange = true;
            if (loopRange.containsValue(i))
                inLoopRange = false;
            if (!inLoopRange && (!isLoop || updateable) && value != null && handles[i].getInstruction() instanceof LoadInstruction
                    && ((LoadInstruction) handles[i].getInstruction()).getIndex() == memoryIndex) {
                isOptimised = false;
                if (value instanceof Integer || value instanceof Float) {
                    instList.insert(handles[i], new LDC(constantIndex));
                } else if (value instanceof Long || value instanceof Double) {
                    instList.insert(handles[i], new LDC2_W(constantIndex));
                }
                setTarget(i + 1, i, cpgen, instList);
                try {
                    instList.delete(handles[i]);
                } catch (TargetLostException e) {
                }
            }
            if (handles[i].getInstruction() instanceof StoreInstruction && ((StoreInstruction) handles[i].getInstruction()).getIndex() == memoryIndex) {
                if (handles[i - 1].getInstruction() instanceof PushInstruction
                        && !(handles[i - 1].getInstruction() instanceof LoadInstruction)) {
                    updateable = true;
                    if (handles[i - 1].getInstruction() instanceof ConstantPushInstruction) {
                        value = ((ConstantPushInstruction) handles[i - 1].getInstruction()).getValue();
                        Constant newConstant = new ConstantInteger(0);
                        if (value instanceof Integer) {
                            newConstant = new ConstantInteger((int) value);
                        } else if (value instanceof Long) {
                            newConstant = new ConstantLong((long) value);
                        } else if (value instanceof Double) {
                            newConstant = new ConstantDouble((double) value);
                        } else if (value instanceof Float) {
                            newConstant = new ConstantFloat((float) value);
                        }
                        constantIndex = cpgen.addConstant(newConstant, cpgen);
                    } else if (handles[i - 1].getInstruction() instanceof CPInstruction) {
                        if ((handles[i - 1].getInstruction()) instanceof LDC) {
                            constantIndex = ((LDC) handles[i - 1].getInstruction()).getIndex();
                            value = ((LDC) handles[i - 1].getInstruction()).getValue(cpgen);
                        } else if ((handles[i - 1].getInstruction()) instanceof LDC2_W) {
                            constantIndex = ((LDC2_W) handles[i - 1].getInstruction()).getIndex();
                            value = ((LDC2_W) handles[i - 1].getInstruction()).getValue(cpgen);
                        }
                    }
                } else {
                    value = null;
                    constantIndex = -1;
                }
            }
            if (handles[i].getInstruction() instanceof IINC && ((IINC) handles[i].getInstruction()).getIndex() == memoryIndex) {
                value = null;
                constantIndex = -1;
            }
            if (handles[i].getInstruction() instanceof GotoInstruction
                    || handles[i].getInstruction() instanceof ReturnInstruction
                    || (handles[i].getInstruction() instanceof IfInstruction && findTargetIndex(handles, ((IfInstruction) handles[i].getInstruction()).getTarget()) < i)) {
                return constantIndex;
            }
            // Critical
            if (handles[i].getInstruction() instanceof IfInstruction
                    && findTargetIndex(handles, ((IfInstruction) handles[i].getInstruction()).getTarget()) > i) {
                InstructionHandle targetInstruction = ((IfInstruction) handles[i].getInstruction()).getTarget();
                int ifToPosition = findTargetIndex(handles, ((IfInstruction) handles[i].getInstruction()).getTarget());
                if (handles[ifToPosition - 1].getInstruction() instanceof GotoInstruction) {
                    // there is goto before if's target
                    InstructionHandle gotoInstruction = handles[ifToPosition - 1];
                    InstructionHandle goToTarget = ((GotoInstruction) gotoInstruction.getInstruction()).getTarget();
                    if (findTargetIndex(handles, goToTarget) < ifToPosition - 1) {
                        // goto upwards : while or for loop
                        int goToPosition = findTargetIndex(handles, goToTarget);
                        constantIndex = recursiveFoldVariable(memoryIndex, constantIndex, value, i + 1, ifToPosition - 2, cpgen, instList, true);
                        if (constantIndex != -1) {
                            i = ifToPosition - 1;
                            inLoopRange = false;
                        } else {
                            i = ifToPosition - 1;
                            constantIndex = -1;
                            value = null;
                            inLoopRange = false;
                        }

                    } else {
                        // goto downwards : if - else statement
                        int goToPosition = findTargetIndex(handles, goToTarget);
                        int index1 = recursiveFoldVariable(memoryIndex, constantIndex, value, i + 1, ifToPosition - 2, cpgen, instList, isLoop);
                        int index2 = recursiveFoldVariable(memoryIndex, constantIndex, value, ifToPosition, goToPosition - 1, cpgen, instList, isLoop);
                        if ((index1 != -1 && index2 != -1 && index1 == index2)) {
                            i = goToPosition - 1;
                        } else {
                            i = goToPosition - 1;
                            value = null;
                            constantIndex = -1;
                        }
                    }
                } else {
                    // No goto before if's target : if statement without else
                    constantIndex = recursiveFoldVariable(memoryIndex, constantIndex, value, i + 1, ifToPosition - 1, cpgen, instList, isLoop);
                    if (constantIndex != -1) {
                        i = ifToPosition - 1;
                    } else {
                        i = ifToPosition - 1;
                        constantIndex = -1;
                        value = null;
                    }
                }
            }
            // do-while loop
            if (loopFromTo.containsKey(i)) {
                constantIndex = recursiveFoldVariable(memoryIndex, constantIndex, value, i, loopFromTo.get(i) - 1, cpgen, instList, true);
                if (constantIndex != -1) {
                    i = loopFromTo.get(i) - 1;
                    inLoopRange = false;
                } else {
                    i = loopFromTo.get(i) - 1;
                    constantIndex = -1;
                    value = null;
                    inLoopRange = false;
                }
            }

            i++;
        }
        return constantIndex;
    }

    private void dynamicVariables(ConstantPoolGen cpgen, InstructionList instList) {
        InstructionHandle[] handles = instList.getInstructionHandles();
        ConstantPool cp = cpgen.getConstantPool();
        HashSet<Integer> variableSet = new HashSet<>();
        for (int i = 1; i < handles.length; i++) {
            if (handles[i].getInstruction() instanceof StoreInstruction) {
                if (!variableSet.contains(((StoreInstruction) handles[i].getInstruction()).getIndex())) {
                    variableSet.add(((StoreInstruction) handles[i].getInstruction()).getIndex());
                }
            }
        }
        do {
            isOptimised = true;
            for (int i : variableSet) {
                handles = instList.getInstructionHandles();
                recursiveFoldVariable(i, -1, null, 0, handles.length - 1, cpgen, instList, false);
            }
            constantVariables(cpgen, instList);
        } while (!isOptimised);
    }

    private Method optimizeMethod(ClassGen cgen, Method m) {
        ConstantPoolGen cpgen = cgen.getConstantPool();
        MethodGen mg = new MethodGen(m, cgen.getClassName(), cpgen);
        mg.removeNOPs();

        InstructionList instList = mg.getInstructionList();

        preprocessor(cpgen, instList);

        simpleFolding(cpgen, instList);

        constantVariables(cpgen, instList);

        dynamicVariables(cpgen, instList);

        mg.stripAttributes(true);
        mg.setMaxStack();
        return mg.getMethod();
    }

    private void optimize() {
        ClassGen cgen = new ClassGen(original);
        ConstantPoolGen cpgen = cgen.getConstantPool();
        Method[] methods = cgen.getMethods();
        int length = methods.length;
        Method[] optimizedMethods = new Method[length];
        for (int i = 0; i < length; i++) {
            System.out.println("--------------------optimizing method: " + i);
            optimizedMethods[i] = optimizeMethod(cgen, methods[i]);
        }
        this.gen.setMethods(optimizedMethods);
        this.gen.setConstantPool(cpgen);
        this.gen.setMajor(50);
        this.optimized = gen.getJavaClass();
    }

    public void write(String optimisedFilePath) {
        this.optimize();
        try {
            FileOutputStream out = new FileOutputStream(new File(optimisedFilePath));
            this.optimized.dump(out);
        } catch (FileNotFoundException e) {
            // Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }
    }
}